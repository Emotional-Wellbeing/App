package es.upm.bienestaremocional.app.data.settings

import es.upm.bienestaremocional.app.data.notification.NotificationsFrequency
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import kotlinx.coroutines.flow.Flow

/**
 * Contains the operations related with the settings of the app
 * @see AppSettingsImpl
 */
interface AppSettings
{

    /**
     * Save notification frequency preference
     * @param value: Preference to save
     */
    suspend fun saveNotificationFrequency(value: NotificationsFrequency)

    /**
     * Get notification frequency preference
     * @return [Flow] of [NotificationsFrequency] with the values
     */
    suspend fun getNotificationFrequency(): Flow<NotificationsFrequency>

    /**
     * Save questionnaires selected
     * @param value: Preference to save
     */
    suspend fun saveQuestionnairesSelected(value: Set<Questionnaire>)

    /**
     * Get notification frequency preference
     * @return [Flow] of [Set] of [Questionnaire] with the values
     */
    suspend fun getQuestionnairesSelected(): Flow<Set<Questionnaire>>

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
     * Save dynamic color palette preference
     * @param value: Preference to save
     */
    suspend fun saveDynamicColors(value: Boolean)

    /**
     * Get dynamic colors theme preference
     * @return [Flow] of [Boolean] with the values
     */
    suspend fun getDynamicColors(): Flow<Boolean>
}