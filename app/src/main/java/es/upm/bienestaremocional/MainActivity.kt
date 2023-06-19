package es.upm.bienestaremocional

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import es.upm.bienestaremocional.data.firstTimeExecution
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.settings.AppSettings
import es.upm.bienestaremocional.data.settings.ThemeMode
import es.upm.bienestaremocional.data.worker.WorkAdministrator
import es.upm.bienestaremocional.domain.repository.LastUploadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appSettings: AppSettings

    @Inject
    lateinit var appInfo: AppInfo

    @Inject
    lateinit var scheduler: WorkAdministrator

    @Inject
    lateinit var lastUploadRepository: LastUploadRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        lateinit var darkTheme: ThemeMode
        var dynamicColors by Delegates.notNull<Boolean>()

        val job = CoroutineScope(Dispatchers.Default).launch {
            if (appInfo.getFirstTime().first()) {
                firstTimeExecution(
                    notificationManager = notificationManager,
                    scheduler = scheduler,
                    lastUploadRepository = lastUploadRepository
                )
            }

            darkTheme = appSettings.getTheme().first()
            dynamicColors = appSettings.getDynamicColors().first()
        }

        CoroutineScope(Dispatchers.Main).launch {
            job.join()
            setContent {
                BienestarEmocionalApp(
                    darkTheme = darkTheme,
                    dynamicColors = dynamicColors,
                    appInfo = appInfo
                )
            }
        }
    }
}
