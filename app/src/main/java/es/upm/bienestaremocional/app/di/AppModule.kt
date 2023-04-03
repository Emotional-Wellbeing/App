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
import es.upm.bienestaremocional.app.data.alarm.AlarmManager
import es.upm.bienestaremocional.app.data.alarm.AlarmScheduler
import es.upm.bienestaremocional.app.data.alarm.AndroidAlarmManager
import es.upm.bienestaremocional.app.data.alarm.AndroidAlarmScheduler
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.language.LanguageManager
import es.upm.bienestaremocional.app.data.language.LanguageManagerImpl
import es.upm.bienestaremocional.app.data.receiver.AlarmReceiver
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.settings.AppSettingsImpl
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRoundReducedRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRoundReducedRepositoryImpl
import es.upm.bienestaremocional.app.ui.notification.Notification
import es.upm.bienestaremocional.app.ui.notification.NotificationImpl
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectAvailability
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManager
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import javax.inject.Named
import javax.inject.Singleton

/**
 * Contains services injected by Hilt common to the entire application
 */
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
    fun provideAppSettings(@ApplicationContext context: Context): AppSettings =
        AppSettingsImpl(context)

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


    @Provides
    @Singleton
    fun provideAlarmManager(scheduler: AlarmScheduler,
                            appSettings: AppSettings,
                            @Named("logTag") logTag: String): AlarmManager =
        AndroidAlarmManager(scheduler,appSettings,logTag)

    @Provides
    @Singleton
    fun provideLanguageManager(application: Application) : LanguageManager =
        LanguageManagerImpl(Lingver.init(application))

    @Provides
    @Singleton
    fun provideHealthConnectAvailability(healthConnectManager: HealthConnectManagerInterface)
    : MutableState<HealthConnectAvailability> = healthConnectManager.availability

    @Provides
    @Singleton
    fun provideQuestionnaireRoundReducedRepository(dao: AppDAO,
                                                   appSettings: AppSettings,
                                                   @Named("logTag") logTag: String
    ): QuestionnaireRoundReducedRepository =
        QuestionnaireRoundReducedRepositoryImpl(dao,appSettings,logTag)
}