package es.upm.bienestaremocional.data.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.settings.ThemeMode.DARK_MODE
import es.upm.bienestaremocional.data.settings.ThemeMode.DEFAULT_MODE
import es.upm.bienestaremocional.data.settings.ThemeMode.LIGHT_MODE

/**
 * Contains the options of theming: [LIGHT_MODE], [DARK_MODE] and [DEFAULT_MODE]
 * @param labelRes: StringResource of the label
 * @param key: String used for store themes
 */
enum class ThemeMode(@StringRes val labelRes: Int, val key: String) {
    LIGHT_MODE(R.string.light_mode_label, "light"),
    DARK_MODE(R.string.dark_mode_label, "dark"),
    DEFAULT_MODE(R.string.default_mode_label, "default");

    /**
     * Retrieves boolean indicating if option is dark or not
     */
    @Composable
    fun themeIsDark(): Boolean =
        when (this) {
            LIGHT_MODE -> false
            DARK_MODE -> true
            DEFAULT_MODE -> isSystemInDarkTheme()
        }

    companion object {
        /**
         * Get all [ThemeMode] possible values
         * @return [List] of [ThemeMode] with the values
         */
        fun get(): List<ThemeMode> = values().asList()

        /**
         * Get all labels of the [ThemeMode] possible values
         * @return [List] of [String] with the labels
         */
        @Composable
        fun getLabels(): List<String> = get().map { stringResource(id = it.labelRes) }
    }
}