package es.upm.bienestaremocional.app.ui.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alorma.compose.settings.storage.base.SettingValueState
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.storage.base.rememberIntSettingState
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SettingsViewModel(val appSettings: AppSettingsInterface): ViewModel()
{
    companion object
    {
        /**
         * Factory class to instance [SettingsViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory{
            initializer {
                SettingsViewModel(appSettings = MainApplication.appSettings)
            }
        }
    }

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
     * Save theme value from [AppSettingsInterface]
     * @Todo solve recall bug to implement restart
     */
    suspend fun changeDarkMode(option: SettingValueState<Int>)
    {
        // Default to null
        val themeMode: ThemeMode? = ThemeMode.values().getOrNull(option.value)
        themeMode?.let {
            appSettings.saveTheme(themeMode)
        }
    }
}