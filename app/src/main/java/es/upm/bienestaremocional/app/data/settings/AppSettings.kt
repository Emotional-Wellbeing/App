package es.upm.bienestaremocional.app.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * Implementation of [AppSettingsInterface] using DataStore
 */
class AppSettings(private val context: Context): AppSettingsInterface
{
    companion object
    {
        /**
         * DataStore object used for ACID operations
         */
        private val Context.appSettingsDataStore : DataStore<Preferences>
            by preferencesDataStore(name = "settings")

        //preferences keys of the settings
        private val SHOW_ONBOARDING = booleanPreferencesKey("show_onboarding")
        private val THEME = stringPreferencesKey("theme")
        private val DYNAMIC_COLORS = booleanPreferencesKey("dynamic_colors")

        //defaults values
        private const val SHOW_ONBOARDING_DEFAULT_VALUE = true
        private val THEME_DEFAULT_VALUE = ThemeMode.DEFAULT_MODE
        private const val DYNAMIC_COLORS_DEFAULT_VALUE = false
    }

    override suspend fun saveShowOnboarding(value: Boolean)
    {
        context.appSettingsDataStore.edit{ preferences ->
            preferences[SHOW_ONBOARDING] = value
        }
    }

    override suspend fun getShowOnboarding(): Flow<Boolean> =
        context.appSettingsDataStore.data.map { preferences ->
            preferences[SHOW_ONBOARDING] ?: SHOW_ONBOARDING_DEFAULT_VALUE
        }

    override fun getShowOnboardingValue(): Boolean =
        runBlocking {
            getShowOnboarding().first()
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

