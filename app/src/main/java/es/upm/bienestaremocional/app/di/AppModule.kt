package es.upm.bienestaremocional.app.di

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.health.connect.client.HealthConnectClient
import com.yariksoffice.lingver.Lingver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.upm.bienestaremocional.app.data.alarm.AlarmReceiver
import es.upm.bienestaremocional.app.data.alarm.AlarmScheduler
import es.upm.bienestaremocional.app.data.alarm.AndroidAlarmScheduler
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.data.settings.LanguageManager
import es.upm.bienestaremocional.app.ui.notification.Notification
import es.upm.bienestaremocional.app.ui.notification.NotificationImpl
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectAvailability
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManager
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule
{
    @Provides
    @Singleton
    fun provideHealthConnectClient(@ApplicationContext context: Context): HealthConnectClient =
        HealthConnectClient.getOrCreate(context)

    @Provides
    @Singleton
    fun provideHealthConnectManager(healthConnectClient: HealthConnectClient,
                                    @ApplicationContext context: Context): HealthConnectManagerInterface =
        HealthConnectManager(healthConnectClient,context)

    @Provides
    @Singleton
    fun provideAppSettings(@ApplicationContext context: Context): AppSettingsInterface =
        AppSettings(context)

    @Provides
    @Singleton
    fun provideNotification(@ApplicationContext context: Context,
                            @Named("logTag") logTag: String): Notification =
        NotificationImpl(context,logTag)

    @Provides
    @Singleton
    fun provideAlarmScheduler(@ApplicationContext context: Context,
                              @Named("logTag") logTag: String): AlarmScheduler =
        AndroidAlarmScheduler(context,
            AlarmReceiver::class.java,
            logTag)

    @Provides
    @Singleton
    @Named("logTag")
    fun provideLogTag(): String = "BienestarEmocionalApp"

    /*@Provides
    @Singleton
    fun provideLingver(application: Application): Lingver = Lingver.init(application)

    @Provides
    @Singleton
    fun provideLanguageManager(lingver: Lingver) : LanguageManager = LanguageManager(lingver)*/

    @Provides
    @Singleton
    fun provideLanguageManager(application: Application) : LanguageManager =
        LanguageManager(Lingver.init(application))

    @Provides
    @Singleton
    fun provideHealthConnectAvailability(healthConnectManager: HealthConnectManagerInterface)
    : MutableState<HealthConnectAvailability> = healthConnectManager.availability
}