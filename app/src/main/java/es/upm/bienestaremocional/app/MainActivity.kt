package es.upm.bienestaremocional.app

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.annotation.SuppressLint
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.worker.WorkAdministrator
import javax.inject.Inject
import androidx.core.app.ActivityCompat.requestPermissions

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    @Inject
    lateinit var appSettings: AppSettings
    @Inject
    lateinit var scheduler: WorkAdministrator

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //request permissions
        requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CALL_LOG),
            101)

        //get imei as provisional user name
        val imei = "userName"
        //encrypt name

        setContent {
            if (imei != null) {
                BienestarEmocionalApp(
                    appSettings = appSettings,
                    scheduler = scheduler,
                    userName = imei
                )
            }
        }
    }
//    @SuppressLint("HardwareIds")
//    private fun getDeviceIMEI(): String? {
//        var deviceUniqueIdentifier: String?
    //      val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    //  deviceUniqueIdentifier = tm.imei
    //    if (null == deviceUniqueIdentifier || deviceUniqueIdentifier.isEmpty()) {
    //        deviceUniqueIdentifier =
    //          Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
    //  }
    //  return deviceUniqueIdentifier
    //}
}
