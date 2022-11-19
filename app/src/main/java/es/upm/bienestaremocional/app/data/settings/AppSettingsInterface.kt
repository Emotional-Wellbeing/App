package es.upm.bienestaremocional.app.data.settings

import kotlinx.coroutines.flow.Flow

interface AppSettingsInterface
{
    //onboarding screen
    suspend fun saveShowOnboarding(value: Boolean)
    suspend fun getShowOnboarding(): Flow<Boolean>

    //theme mode
    suspend fun saveTheme(value: ThemeMode)
    suspend fun getTheme(): Flow<ThemeMode>

    //dynamic colors
    suspend fun saveDynamicColors(value: Boolean)
    suspend fun getDynamicColors(): Flow<Boolean>
}