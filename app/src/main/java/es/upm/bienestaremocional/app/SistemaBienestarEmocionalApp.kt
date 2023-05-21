package es.upm.bienestaremocional.app

import android.app.NotificationManager
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ramcosta.composedestinations.DestinationsNavHost
import es.upm.bienestaremocional.app.data.info.AppInfo
import es.upm.bienestaremocional.app.data.notification.NotificationChannels
import es.upm.bienestaremocional.app.data.notification.createNotificationChannel
import es.upm.bienestaremocional.app.data.phonecalls.PhoneInfo
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import es.upm.bienestaremocional.app.data.trafficstats.Traffic
import es.upm.bienestaremocional.app.data.usage.Usage
import es.upm.bienestaremocional.app.data.worker.WorkAdministrator
import es.upm.bienestaremocional.app.ui.screens.NavGraphs
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

@Composable
fun BienestarEmocionalApp(appSettings: AppSettings,
                          appInfo: AppInfo,
                          scheduler: WorkAdministrator,
                          activity: MainActivity)
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

    //internet info
    val traffic = Traffic()
    traffic.init()
    println("Inserted internet info")
    //val phone = PhoneInfo()
    //val executorServicePhone = Executors.newSingleThreadScheduledExecutor()
    //executorServicePhone.scheduleAtFixedRate({ phone.getCallLogs(context) }, 0, 10, TimeUnit.SECONDS)

    //executorService.scheduleAtFixedRate(        { backgroundInfo(activity,context) },
    //    5, 10, TimeUnit.SECONDS
    //)

    //----------------------------------------------------------------------------------------

    BienestarEmocionalTheme(darkTheme = darkTheme.themeIsDark(), dynamicColors = dynamicColors)
    {
        DestinationsNavHost(navGraph = NavGraphs.root)
    }
}

fun backgroundInfo(activity: MainActivity, context: Context) {
    //phone calls logs
    println("Recolecting background data")
    val phone = PhoneInfo()
    phone.getCallLogs(context)
    println("Inserted phone info")//estos van bien

    //usage info
    //val usage = Usage()
    //usage.getAppUsage(activity)
    println("Inserted usage info")

    //internet info
   //val traffic = Traffic()
   // traffic.init()
    println("Inserted internet info")
}
