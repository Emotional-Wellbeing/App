package es.upm.bienestaremocional.app

import android.app.Application
import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import com.yariksoffice.lingver.Lingver
import es.upm.bienestaremocional.app.data.alarm.AlarmReceiver
import es.upm.bienestaremocional.app.data.alarm.AlarmScheduler
import es.upm.bienestaremocional.app.data.alarm.AndroidAlarmScheduler
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.data.settings.LanguageManager
import es.upm.bienestaremocional.app.ui.notification.NotificationSender
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManager
import es.upm.bienestaremocional.core.ui.responsive.WindowSize


//https://guides.codepath.com/android/Understanding-the-Android-Application-Class

/**
 * This class contains default objects that need context to be instanced.
 * They are usually used in viewmodels
 */
class MainApplication: Application()
{
    init
    {
        instance = this
    }

    override fun onCreate()
    {
        super.onCreate()
        languageManager = LanguageManager(Lingver.init(this))
    }

    companion object
    {
        lateinit var instance : MainApplication
            private set

        private val applicationContext : Context get() = instance.applicationContext

        val healthConnectClient : HealthConnectClient
            get() = HealthConnectClient.getOrCreate(applicationContext)
        val healthConnectManager : HealthConnectManager
            get() = HealthConnectManager(healthConnectClient = healthConnectClient,
                context = applicationContext)
        val appSettings : AppSettingsInterface
            get() = AppSettings(applicationContext)
        val notificationSender: NotificationSender
            get() = NotificationSender(applicationContext)
        val alarmScheduler: AlarmScheduler
            get() = AndroidAlarmScheduler(applicationContext, AlarmReceiver::class.java)
        lateinit var languageManager : LanguageManager
            private set

        //init at MainActivity
        var windowSize : WindowSize? = null
    }
}