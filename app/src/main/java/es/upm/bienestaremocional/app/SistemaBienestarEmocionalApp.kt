package es.upm.bienestaremocional.app

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.INTERNET
import android.Manifest.permission.READ_CALL_LOG
import android.app.NotificationManager
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.ramcosta.composedestinations.DestinationsNavHost
import es.upm.bienestaremocional.app.data.info.AppInfo
import es.upm.bienestaremocional.app.data.notification.NotificationChannels
import es.upm.bienestaremocional.app.data.notification.createNotificationChannel
import es.upm.bienestaremocional.app.data.phonecalls.PhoneInfo
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import es.upm.bienestaremocional.app.data.trafficstats.Traffic
import es.upm.bienestaremocional.app.data.worker.WorkAdministrator
import es.upm.bienestaremocional.app.ui.screens.NavGraphs
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
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
    executorServicePhone.scheduleAtFixedRate({ PhoneInfo(context) }, 0, 30, TimeUnit.SECONDS)

    val executorServiceTraffic = Executors.newSingleThreadScheduledExecutor()
    executorServiceTraffic.scheduleAtFixedRate({ TrafficInfo(context) }, 0, 20, TimeUnit.SECONDS)

    //----------------------------------------------------------------------------------------

    BienestarEmocionalTheme(darkTheme = darkTheme.themeIsDark(), dynamicColors = dynamicColors)
    {
        DestinationsNavHost(navGraph = NavGraphs.root)
    }
}

fun PhoneInfo(context: Context) {
    //phone calls logs

    val permissionCheckResult = ContextCompat.checkSelfPermission(context, READ_CALL_LOG)
    if (permissionCheckResult==0)
    {
        val phone = PhoneInfo()
        phone.getCallLogs(context)
        println("Inserted phone info")//estos van bien
    }
}

fun TrafficInfo(context: Context) {
    //Internet calls logs

    val permissionInternet = ContextCompat.checkSelfPermission(context, INTERNET)
    val permissionCoarse = ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
    val permissionFine = ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
    if ((permissionInternet == 0) && (permissionCoarse == 0) && (permissionFine == 0))
    {
        val traffic = Traffic()
        traffic.init()
        println("Inserted internet info")//estos van bien
    }
}