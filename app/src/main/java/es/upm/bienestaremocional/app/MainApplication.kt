package es.upm.bienestaremocional.app

import android.app.Application
import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManager

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
    }
}