package es.upm.bienestaremocional.app.data.settings

import kotlinx.coroutines.flow.Flow

/**
 * Contains the operations related with the settings of the app
 *
 */
interface AppSettingsInterface
{
    /**
     * Save show onboarding screen preference
     * @param value: Preference to save
     */
    suspend fun saveShowOnboarding(value: Boolean)

    /**
     * Get show onboarding screen preference
     * @return [Flow] of [Boolean] with the values
     */
    suspend fun getShowOnboarding(): Flow<Boolean>

    /**
     * Thread-blocking implementation of [getShowOnboarding]
     */
    fun getShowOnboardingValue(): Boolean

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