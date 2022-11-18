package es.upm.bienestaremocional.app.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class AppSettings(private val context: Context): AppSettingsInterface
{
    companion object {
        private val Context.appSettingsDataStore : DataStore<Preferences> by preferencesDataStore(name = "settings")
        private val SHOW_ONBOARDING = booleanPreferencesKey("show_onboarding")
    }

    override suspend fun saveShowOnboarding(value: Boolean)
    {
        context.appSettingsDataStore.edit{ preferences ->
            preferences[SHOW_ONBOARDING] = value
        }
    }

    override suspend fun getShowOnboarding(): Flow<Boolean> =
        context.appSettingsDataStore.data.map { preferences ->
            preferences[SHOW_ONBOARDING] ?: true
        }
}

