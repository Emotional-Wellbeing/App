package es.upm.bienestaremocional.app.data.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable


enum class ThemeMode(val label: String, val key: String)
{
    LIGHT_MODE("Desactivado", "light"),
    DARK_MODE("Activado", "dark"),
    DEFAULT_MODE("Usar la configuración del dispositivo","default");

    @Composable
    fun themeIsDark(): Boolean =
        when(this)
        {
            LIGHT_MODE -> false
            DARK_MODE -> true
            DEFAULT_MODE -> isSystemInDarkTheme()
        }

    companion object
    {
        fun get(): List<ThemeMode> = values().asList()
        fun getLabels(): List<String> = get().map { it.label  }
    }
}