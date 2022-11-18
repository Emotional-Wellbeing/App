package es.upm.bienestaremocional.app.data.settings

import kotlinx.coroutines.flow.Flow

interface AppSettingsInterface
{
    suspend fun saveShowOnboarding(value: Boolean)
    suspend fun getShowOnboarding(): Flow<Boolean>
}