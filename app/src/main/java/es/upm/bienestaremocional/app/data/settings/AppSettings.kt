package es.upm.bienestaremocional.app.data.settings

import es.upm.bienestaremocional.app.data.alarm.AlarmsFrequency
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import kotlinx.coroutines.flow.Flow

/**
 * Contains the operations related with the settings of the app
 * @see AppSettingsImpl
 */
interface AppSettings
{

    /**
     * Save alarm frequency preference
     * @param value: Preference to save
     */
    suspend fun saveAlarmFrequency(value: AlarmsFrequency)

    /**
     * Get alarm frequency preference
     * @return [Flow] of [AlarmsFrequency] with the values
     */
    suspend fun getAlarmFrequency(): Flow<AlarmsFrequency>

    /**
     * Thread-blocking implementation of [getAlarmFrequency]
     */
    fun getAlarmFrequencyValue(): AlarmsFrequency

    /**
     * Save questionnaires selected
     * @param value: Preference to save
     */
    suspend fun saveQuestionnairesSelected(value: Set<Questionnaire>)

    /**
     * Get alarm frequency preference
     * @return [Flow] of [Set] of [Questionnaire] with the values
     */
    suspend fun getQuestionnairesSelected(): Flow<Set<Questionnaire>>

    /**
     * Thread-blocking implementation of [getQuestionnairesSelected]
     */
    fun getQuestionnairesSelectedValue(): Set<Questionnaire>

    /**
     * Save first time info
     * @param value: Preference to save
     */
    suspend fun saveFirstTime(value: Boolean)

    /**
     * Get if the app is executed for first time
     * @return [Flow] of [Boolean] with the values
     */
    suspend fun getFirstTime(): Flow<Boolean>

    /**
     * Thread-blocking implementation of [getFirstTime]
     */
    fun getFirstTimeValue(): Boolean

    /**
     * Save theme preference
     * @param value: Preference to save
     */
    suspend fun saveTheme(value: ThemeMode)

    /**
     * Get theme preference
     * @return [Flow] of [ThemeMode] with the values
     */
    suspend fun getTheme(): Flow<ThemeMode>

    /**
     * Thread-blocking implementation of [getTheme]
     */
    fun getThemeValue(): ThemeMode

    /**
     * Save dynamic color palette preference
     * @param value: Preference to save
     */
    suspend fun saveDynamicColors(value: Boolean)

    /**
     * Get dynamic colors theme preference
     * @return [Flow] of [Boolean] with the values
     */
    suspend fun getDynamicColors(): Flow<Boolean>

    /**
     * Thread-blocking implementation of [getDynamicColors]
     */
    fun getDynamicColorsValue(): Boolean
}