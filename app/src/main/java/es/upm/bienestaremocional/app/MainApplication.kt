package es.upm.bienestaremocional.app

import android.app.Application
import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import com.yariksoffice.lingver.Lingver
import es.upm.bienestaremocional.app.data.alarm.AlarmReceiver
import es.upm.bienestaremocional.app.data.alarm.AlarmScheduler
import es.upm.bienestaremocional.app.data.alarm.AndroidAlarmScheduler
import es.upm.bienestaremocional.app.data.database.AppDatabase
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.repository.*
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.data.settings.LanguageManager
import es.upm.bienestaremocional.app.ui.notification.Notification
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
        val notification: Notification
            get() = Notification(applicationContext)
        val alarmScheduler: AlarmScheduler
            get() = AndroidAlarmScheduler(applicationContext, AlarmReceiver::class.java)
        lateinit var languageManager : LanguageManager
            private set

        private val database: AppDatabase
            get() = AppDatabase.getInstance(applicationContext)
        private val dao: AppDAO
            get() = database.appDao()

        val questionnaireRoundRepository : QuestionnaireRoundRepository
            get() = QuestionnaireRoundRepositoryImpl(dao)
        val pssRepository : PSSRepository
            get() = PSSRepositoryImpl(dao)
        val phqRepository : PHQRepository
            get() = PHQRepositoryImpl(dao)
        val uclaRepository : UCLARepository
            get() = UCLARepositoryImpl(dao)
        val questionnaireRoundWithQuestionnairesRepository : QuestionnaireRoundWithQuestionnairesRepository
            get() = QuestionnaireRoundWithQuestionnairesRepositoryImpl(dao)

        const val logTag = "BienestarEmocionalApp"

        //init at MainActivity
        var windowSize : WindowSize? = null
    }
}