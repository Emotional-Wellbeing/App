package es.upm.bienestaremocional.app.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import es.upm.bienestaremocional.app.data.alarm.AlarmsFrequency
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * Implementation of [AppSettings] using DataStore
 */
class AppSettingsImpl(private val context: Context): AppSettings
{
    companion object
    {
        /**
         * DataStore object used for ACID operations
         */
        private val Context.appSettingsDataStore : DataStore<Preferences>
            by preferencesDataStore(name = "settings")

        //preferences keys of the settings
        private val ALARM_FREQUENCY = intPreferencesKey("alarm_frequency")
        private val QUESTIONNAIRES = stringSetPreferencesKey("questionnaires")
        private val FIRST_TIME = booleanPreferencesKey("first_time")
        private val THEME = stringPreferencesKey("theme")
        private val DYNAMIC_COLORS = booleanPreferencesKey("dynamic_colors")

        //defaults values
        private val ALARM_FREQUENCY_DEFAULT_VALUE = AlarmsFrequency.NIGHT_ALARM
        private val QUESTIONNAIRES_DEFAULT_VALUE = emptySet<String>()
        private const val FIRST_TIME_DEFAULT_VALUE = true
        private val THEME_DEFAULT_VALUE = ThemeMode.DEFAULT_MODE
        private const val DYNAMIC_COLORS_DEFAULT_VALUE = false
    }

    override suspend fun saveAlarmFrequency(value: AlarmsFrequency)
    {
        context.appSettingsDataStore.edit{ preferences ->
            preferences[ALARM_FREQUENCY] = value.ordinal
        }
    }

    override suspend fun getAlarmFrequency(): Flow<AlarmsFrequency> =
        context.appSettingsDataStore.data.map { preferences ->
            when(preferences[ALARM_FREQUENCY])
            {
                AlarmsFrequency.NIGHT_ALARM.ordinal -> AlarmsFrequency.NIGHT_ALARM
                AlarmsFrequency.NIGHT_LUNCH_ALARM.ordinal -> AlarmsFrequency.NIGHT_LUNCH_ALARM
                else -> ALARM_FREQUENCY_DEFAULT_VALUE
            }
        }

    override fun getAlarmFrequencyValue(): AlarmsFrequency =
        runBlocking {
            getAlarmFrequency().first()
        }

    override suspend fun saveQuestionnairesSelected(value: Set<Questionnaire>)
    {
        val setTransformed = value.map { it.id }.toSet()
        context.appSettingsDataStore.edit{ preferences ->
            preferences[QUESTIONNAIRES] = setTransformed
        }
    }

    override suspend fun getQuestionnairesSelected(): Flow<Set<Questionnaire>> {
        val originalFlow = context.appSettingsDataStore.data.map { preferences ->
            preferences[QUESTIONNAIRES] ?: QUESTIONNAIRES_DEFAULT_VALUE
        }
        return originalFlow.map { set -> set.mapNotNull { Questionnaire.decode(it) }.toSet() }
    }


    override fun getQuestionnairesSelectedValue(): Set<Questionnaire> =
        runBlocking {
            getQuestionnairesSelected().first()
        }

    override suspend fun saveFirstTime(value: Boolean)
    {
        context.appSettingsDataStore.edit{ preferences ->
            preferences[FIRST_TIME] = value
        }
    }

    override suspend fun getFirstTime(): Flow<Boolean> =
        context.appSettingsDataStore.data.map { preferences ->
            preferences[FIRST_TIME] ?: FIRST_TIME_DEFAULT_VALUE
        }

    override fun getFirstTimeValue(): Boolean =
        runBlocking {
            getFirstTime().first()
        }

    override suspend fun saveTheme(value: ThemeMode)
    {
        context.appSettingsDataStore.edit{ preferences ->
            preferences[THEME] = value.key
        }
    }

    override suspend fun getTheme(): Flow<ThemeMode> =
        context.appSettingsDataStore.data.map { preferences ->
            when(preferences[THEME])
            {
                ThemeMode.LIGHT_MODE.key -> ThemeMode.LIGHT_MODE
                ThemeMode.DARK_MODE.key -> ThemeMode.DARK_MODE
                ThemeMode.DEFAULT_MODE.key -> ThemeMode.DEFAULT_MODE
                else -> THEME_DEFAULT_VALUE
            }
        }

    override fun getThemeValue(): ThemeMode =
        runBlocking {
            getTheme().first()
        }

    override suspend fun saveDynamicColors(value: Boolean)
    {
        context.appSettingsDataStore.edit{ preferences ->
            preferences[DYNAMIC_COLORS] = value
        }
    }

    override suspend fun getDynamicColors(): Flow<Boolean> =
        context.appSettingsDataStore.data.map { preferences ->
            preferences[DYNAMIC_COLORS] ?: DYNAMIC_COLORS_DEFAULT_VALUE
        }

    override fun getDynamicColorsValue(): Boolean =
        runBlocking {
            getDynamicColors().first()
        }
}