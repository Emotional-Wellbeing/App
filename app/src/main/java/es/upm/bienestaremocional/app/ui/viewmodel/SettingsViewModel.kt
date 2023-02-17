package es.upm.bienestaremocional.app.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.alorma.compose.settings.storage.base.SettingValueState
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.storage.base.rememberIntSetSettingState
import com.alorma.compose.settings.storage.base.rememberIntSettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.BuildConfig
import es.upm.bienestaremocional.app.data.alarm.AlarmScheduler
import es.upm.bienestaremocional.app.data.alarm.AlarmsFrequency
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.data.settings.LanguageManager
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettings: AppSettingsInterface,
    private val languageManager: LanguageManager,
    private val alarmScheduler: AlarmScheduler
) : ViewModel()
{
    /**
     * Load dynamic color value from [AppSettingsInterface]
     */
    @Composable
    fun loadDynamicColors(): SettingValueState<Boolean>
    {
        var option : Boolean
        runBlocking(Dispatchers.IO)
        {
            option = appSettings.getDynamicColors().first()
        }
        return rememberBooleanSettingState(option)
    }

    /**
     * Saves dynamic color value
     */
    suspend fun changeDynamicColors(option: SettingValueState<Boolean>)
    {
        appSettings.saveDynamicColors(option.value)
    }

    /**
     * Load theme value from [AppSettingsInterface]
     */
    @Composable
    fun loadDarkMode() : SettingValueState<Int>
    {
        var option : Int
        runBlocking(Dispatchers.IO)
        {
            option = appSettings.getTheme().first().ordinal
        }
        return rememberIntSettingState(option)
    }

    /**
     * Save theme value from [LanguageManager]
     */
     fun changeLanguage(context: Context, option: SettingValueState<Int>)
    {
        languageManager.changeLocale(context,option.value)
    }

    /**
     * Load theme value from [LanguageManager]
     */
    @Composable
    fun loadLanguage() : SettingValueState<Int>
    {
        return rememberIntSettingState(languageManager.getLocale())
    }

    /**
     * Save theme value from [AppSettingsInterface]
     */
    suspend fun changeDarkMode(option: SettingValueState<Int>)
    {
        // Default to null
        val themeMode: ThemeMode? = ThemeMode.values().getOrNull(option.value)
        themeMode?.let {
            appSettings.saveTheme(themeMode)
        }
    }

    /**
     * Load alarm frequency from [AppSettingsInterface]
     */
    @Composable
    fun loadAlarmFrequency(): SettingValueState<Int>
    {
        var option : Int
        runBlocking(Dispatchers.IO)
        {
            option = appSettings.getAlarmFrequency().first().ordinal
        }
        return rememberIntSettingState(option)
    }

    /**
     * Saves alarm frequency value
     */
    suspend fun changeAlarmFrequency(option: SettingValueState<Int>)
    {
        val alarmsFrequency: AlarmsFrequency? = AlarmsFrequency.values().getOrNull(option.value)
        alarmsFrequency?.let {
            alarmScheduler.cancel(appSettings.getAlarmFrequencyValue().alarmItems)
            alarmScheduler.schedule(it.alarmItems)
            appSettings.saveAlarmFrequency(it)
        }
    }

    /**
     * Load questionnaires selected value from [AppSettingsInterface]
     */
    @Composable
    fun loadQuestionnairesSelected(): SettingValueState<Set<Int>>
    {
        var option : Set<Int>
        val possibleOptions = Questionnaire.getOptional()
        runBlocking(Dispatchers.IO)
        {
            option = appSettings.getQuestionnairesSelected().first().map { possibleOptions.indexOf(it) }.toSet()
        }
        return rememberIntSetSettingState(option)
    }

    /**
     * Saves questionnaires selected value
     */
    suspend fun changeQuestionnairesSelected(option: SettingValueState<Set<Int>>)
    {
        // Default to null
        val questionnaire: Set<Questionnaire> = option.value.mapNotNull {
            Questionnaire.getOptional().getOrNull(it)
        }.toSet()

        appSettings.saveQuestionnairesSelected(questionnaire)
    }

    fun openSettingsNotifications(context: Context)
    {
        val intent = Intent().apply {
            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }
        context.startActivity(intent)
    }

    fun openSettingsApplication(context: Context)
    {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    fun openSettingsExactNotifications(context: Context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            val intent = Intent().apply {
                action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            }
            context.startActivity(intent)
        }
    }

    fun getLanguagesAvailable() = languageManager.getSupportedLocalesLabel()
}