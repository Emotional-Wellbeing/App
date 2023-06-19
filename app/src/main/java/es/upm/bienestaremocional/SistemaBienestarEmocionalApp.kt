package es.upm.bienestaremocional

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.INTERNET
import android.Manifest.permission.READ_CALL_LOG
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.ramcosta.composedestinations.DestinationsNavHost
import es.upm.bienestaremocional.data.AppConstants
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.phonecalls.PhoneInfo
import es.upm.bienestaremocional.data.remote.RemoteAPI
import es.upm.bienestaremocional.data.settings.ThemeMode
import es.upm.bienestaremocional.data.trafficstats.Traffic
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepository
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepositoryImpl
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Composable
fun BienestarEmocionalApp(darkTheme: ThemeMode,
                          dynamicColors : Boolean,
                          appInfo: AppInfo
)
{
    //init variables
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val remoteAPI: RemoteAPI = Retrofit.Builder()
        .baseUrl(AppConstants.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RemoteAPI::class.java)

    val logTag= "logTag"
    val remoteRepository: RemoteRepository = RemoteRepositoryImpl(logTag,remoteAPI)


    val executorServicePhone = Executors.newSingleThreadScheduledExecutor()
    executorServicePhone.scheduleAtFixedRate({ phoneInfo(context, coroutineScope, remoteRepository, appInfo) }, 0, 7200, TimeUnit.SECONDS)

    val executorServiceTraffic = Executors.newSingleThreadScheduledExecutor()
    executorServiceTraffic.scheduleAtFixedRate({ trafficInfo(context, coroutineScope, remoteRepository, appInfo) }, 0, 1800, TimeUnit.SECONDS)

    //----------------------------------------------------------------------------------------

    BienestarEmocionalTheme(darkTheme = darkTheme.themeIsDark(), dynamicColors = dynamicColors)
    {
        DestinationsNavHost(navGraph = NavGraphs.root)
    }
}

fun phoneInfo(context: Context, coroutineScope: CoroutineScope, remoteRepository: RemoteRepository, appInfo: AppInfo) {
    //phone calls logs

    val permissionCheckResult = ContextCompat.checkSelfPermission(context, READ_CALL_LOG)
    if (permissionCheckResult==0)
    {
        val phone = PhoneInfo()
        val listCalls = phone.getCallLogs(context)
        coroutineScope.launch {
            val userId = appInfo.getUserID()
            val message = "{ \"userId\": \"$userId\", \"databg\": { \"PhoneInfo\":$listCalls}}"
            val success = remoteRepository.postBackgroundData(message)
            if (success == true)
                println("Inserted phone info")
        }
    }
}

fun trafficInfo(context: Context, coroutineScope: CoroutineScope, remoteRepository: RemoteRepository, appInfo: AppInfo) {
    //Internet calls logs
    val permissionInternet = ContextCompat.checkSelfPermission(context, INTERNET)
    val permissionCoarse = ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
    val permissionFine = ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
    if ((permissionInternet == 0) && (permissionCoarse == 0) && (permissionFine == 0))
    {
        val traffic = Traffic()
        val trafficMessage = traffic.init()
        coroutineScope.launch {
            val userId = appInfo.getUserID()
            val message = "{ \"userId\": \"$userId\", \"databg\": { \"InternetInfo\": $trafficMessage}}"
            val success = remoteRepository.postBackgroundData(message)
            if (success == true)
               println("Inserted internet info")
        }
    }
}