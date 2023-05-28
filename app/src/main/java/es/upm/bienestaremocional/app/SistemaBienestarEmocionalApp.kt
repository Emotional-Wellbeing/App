package es.upm.bienestaremocional.app

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.INTERNET
import android.Manifest.permission.READ_CALL_LOG
import android.app.NotificationManager
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.ramcosta.composedestinations.DestinationsNavHost
import es.upm.bienestaremocional.app.data.AppConstants
import es.upm.bienestaremocional.app.data.info.AppInfo
import es.upm.bienestaremocional.app.data.notification.NotificationChannels
import es.upm.bienestaremocional.app.data.notification.createNotificationChannel
import es.upm.bienestaremocional.app.data.phonecalls.PhoneInfo
import es.upm.bienestaremocional.app.data.remote.RemoteAPI
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import es.upm.bienestaremocional.app.data.trafficstats.Traffic
import es.upm.bienestaremocional.app.data.worker.WorkAdministrator
import es.upm.bienestaremocional.app.domain.repository.remote.RemoteRepository
import es.upm.bienestaremocional.app.domain.repository.remote.RemoteRepositoryImpl
import es.upm.bienestaremocional.app.ui.screens.NavGraphs
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

@Composable
fun BienestarEmocionalApp(appSettings: AppSettings,
                          appInfo: AppInfo,
                          scheduler: WorkAdministrator)
{
    //init variables
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val remoteAPI: RemoteAPI = Retrofit.Builder()
        .baseUrl(AppConstants.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RemoteAPI::class.java)

    val remoteRepository: RemoteRepository = RemoteRepositoryImpl(
        remoteAPI = remoteAPI,
        distance = null,
        elevationGained = null,
        exerciseSession = null,
        floorsClimbed = null,
        heartRate = null,
        sleep = null,
        steps = null,
        totalCaloriesBurned = null,
        weight = null
    )

    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    lateinit var darkTheme : ThemeMode
    var dynamicColors by Delegates.notNull<Boolean>()

    runBlocking {
        if (appInfo.getFirstTime().first())
        {
            //build channel notifications
            for (appChannel in NotificationChannels.values())
                createNotificationChannel(
                    notificationManager = notificationManager,
                    channel = appChannel
                )
            //schedule notifications
            scheduler.schedule(appSettings.getNotificationFrequency().first().items)
            scheduler.scheduleUploadWorker()
        }

        //read ui settings
        darkTheme = appSettings.getTheme().first()
        dynamicColors  = appSettings.getDynamicColors().first()
    }

    val executorServicePhone = Executors.newSingleThreadScheduledExecutor()
    executorServicePhone.scheduleAtFixedRate({ phoneInfo(context, coroutineScope, remoteRepository) }, 0, 30, TimeUnit.SECONDS)

    val executorServiceTraffic = Executors.newSingleThreadScheduledExecutor()
    executorServiceTraffic.scheduleAtFixedRate({ trafficInfo(context, coroutineScope, remoteRepository) }, 0, 20, TimeUnit.SECONDS)

    //----------------------------------------------------------------------------------------

    BienestarEmocionalTheme(darkTheme = darkTheme.themeIsDark(), dynamicColors = dynamicColors)
    {
        DestinationsNavHost(navGraph = NavGraphs.root)
    }
}

fun phoneInfo(context: Context, coroutineScope: CoroutineScope, remoteRepository: RemoteRepository) {
    //phone calls logs

    val permissionCheckResult = ContextCompat.checkSelfPermission(context, READ_CALL_LOG)
    if (permissionCheckResult==0)
    {
        val phone = PhoneInfo()
        val listCalls = phone.getCallLogs(context)
        coroutineScope.launch {
            val message = "{ \"UserId\": 1000, \"Type\": \"PhoneInfo\", \"Data\": $listCalls}"
            val success = remoteRepository.postBackgroundData(message)
            if (success)
                println("Inserted phone info")
        }
    }
}

fun trafficInfo(context: Context, coroutineScope: CoroutineScope, remoteRepository: RemoteRepository) {
    //Internet calls logs
    val permissionInternet = ContextCompat.checkSelfPermission(context, INTERNET)
    val permissionCoarse = ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
    val permissionFine = ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
    if ((permissionInternet == 0) && (permissionCoarse == 0) && (permissionFine == 0))
    {
        val traffic = Traffic()
        val trafficMessage = traffic.init()
        coroutineScope.launch {
            val message = "{ \"UserId\": 1000, \"Type\": \"InternetInfo\", \"Data\": $trafficMessage}"
            val success = remoteRepository.postBackgroundData(message)
            if (success)
                println("Inserted internet info")
        }
    }
}