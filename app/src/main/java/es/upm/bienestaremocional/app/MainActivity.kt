package es.upm.bienestaremocional.app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import es.upm.bienestaremocional.app.data.info.AppInfo
import android.Manifest
import android.annotation.SuppressLint
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.worker.WorkAdministrator
import javax.inject.Inject
import androidx.core.app.ActivityCompat.requestPermissions

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    @Inject
    lateinit var appSettings: AppSettings
    @Inject
    lateinit var appInfo: AppInfo
    @Inject
    lateinit var scheduler: WorkAdministrator

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContent {
            BienestarEmocionalApp(
                appSettings = appSettings,
                appInfo = appInfo,
                scheduler = scheduler,
                activity = this,
            )
        }
    }
}
