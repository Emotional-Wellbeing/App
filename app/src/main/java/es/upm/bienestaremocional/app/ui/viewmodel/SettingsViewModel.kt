package es.upm.bienestaremocional.app.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.BuildConfig
import es.upm.bienestaremocional.app.data.alarm.AlarmScheduler
import es.upm.bienestaremocional.app.data.alarm.AlarmsFrequency
import es.upm.bienestaremocional.app.data.language.LanguageManager
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettings: AppSettings,
    private val languageManager: LanguageManager,
    private val alarmScheduler: AlarmScheduler
) : ViewModel()
{
    /**
     * Load dynamic color value from [AppSettings]
     */
    fun loadDynamicColors(): Boolean
    {
        var option : Boolean

        runBlocking(Dispatchers.IO)
        {
            option = appSettings.getDynamicColors().first()
        }
        return option
    }

    /**
     * Saves dynamic color value
     */
    fun changeDynamicColors(option: Boolean)
    {
        viewModelScope.launch {
            appSettings.saveDynamicColors(option)
        }
    }

    /**
     * Load theme value from [AppSettings]
     */
    fun loadDarkMode() : Int
    {
        var option : Int
        runBlocking(Dispatchers.IO)
        {
            option = appSettings.getTheme().first().ordinal
        }
        return option
    }

    /**
     * Save theme value from [LanguageManager]
     */
     fun changeLanguage(context: Context, option: Int)
    {
        languageManager.changeLocale(context,option)
    }

  /**
     * Load theme value from [LanguageManager]
     */
    fun loadLanguage() : Int = languageManager.getLocale()

    /**
     * Save theme value from [AppSettings]
     */
    fun changeDarkMode(option: Int)
    {
        // Default to null
        val themeMode: ThemeMode? = ThemeMode.values().getOrNull(option)
        themeMode?.let {
            viewModelScope.launch {
                appSettings.saveTheme(themeMode)
            }
        }
    }
    
    /**
     * Load alarm frequency from [AppSettings]
     */
    fun loadAlarmFrequency(): Int
    {
        var option : Int
        runBlocking(Dispatchers.IO)
        {
            option = appSettings.getAlarmFrequency().first().ordinal
        }
        return option
    }

    /**
     * Saves alarm frequency value
     */
    fun changeAlarmFrequency(option: Int)
    {
        val alarmsFrequency: AlarmsFrequency? = AlarmsFrequency.values().getOrNull(option)
        alarmsFrequency?.let {
            alarmScheduler.cancel(appSettings.getAlarmFrequencyValue().alarmItems)
            alarmScheduler.schedule(it.alarmItems)
            viewModelScope.launch {
                appSettings.saveAlarmFrequency(it)
            }
        }
    }
    
    
    /**
     * Load questionnaires selected value from [AppSettings]
     */
    fun loadQuestionnairesSelected(): Set<Int>
    {
        var option : Set<Int>
        val possibleOptions = Questionnaire.getOptional()
        runBlocking(Dispatchers.IO)
        {
            option = appSettings.getQuestionnairesSelected().first().map { possibleOptions.indexOf(it) }.toSet()
        }
        return option
    }

    /**
     * Saves questionnaires selected value
     */
    fun changeQuestionnairesSelected(option: Set<Int>)
    {
        // Default to null
        val questionnaire: Set<Questionnaire> = option.mapNotNull {
            Questionnaire.getOptional().getOrNull(it)
        }.toSet()

        viewModelScope.launch {
            appSettings.saveQuestionnairesSelected(questionnaire)
        }
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