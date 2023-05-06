package es.upm.bienestaremocional.app.data.questionnaire

import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.core.ui.theme.dark_Limegreen
import es.upm.bienestaremocional.core.ui.theme.dark_Ochre
import es.upm.bienestaremocional.core.ui.theme.dark_Persianred
import es.upm.bienestaremocional.core.ui.theme.dark_Xanthous
import es.upm.bienestaremocional.core.ui.theme.dark_Yellowgreen
import es.upm.bienestaremocional.core.ui.theme.light_Limegreen
import es.upm.bienestaremocional.core.ui.theme.light_Ochre
import es.upm.bienestaremocional.core.ui.theme.light_Persianred
import es.upm.bienestaremocional.core.ui.theme.light_Xanthous
import es.upm.bienestaremocional.core.ui.theme.light_Yellowgreen

/**
 * Contains available levels of the results of the questionnaires
 * @param id: String with the id used to save in database
 * @param label: StringResource with the label used to show in screens
 */
enum class LevelLabel(
    val id: String,
    @StringRes val label: Int
)
{
    Low("low", R.string.low),
    Moderate("moderate",R.string.moderate),
    High("high",R.string.high),
    Minimal("minimal",R.string.minimal),
    Mild("mild",R.string.mild),
    ModeratelySevere("moderately_severe",R.string.moderately_severe),
    Severe("severe",R.string.severe);

    companion object
    {
        /**
         * Obtain the LevelLabel from their id
         * @param id: The id to query
         * @return The LevelLabel if the id matches with any LevelLabel or null if doesn't
         */
        fun decodeFromId(id: String) : LevelLabel?
        {
            return when(id)
            {
                Low.id -> Low
                Moderate.id -> Moderate
                High.id -> High
                Minimal.id -> Minimal
                Mild.id -> Mild
                ModeratelySevere.id -> ModeratelySevere
                Severe.id -> Severe
                else -> null
            }
        }

        @Composable
        fun LevelLabel.getColor() : Color
        {
            return when(this)
            {
                Low -> {
                    if (isSystemInDarkTheme())
                        dark_Limegreen
                    else
                        light_Limegreen
                }
                Moderate -> {
                    if (isSystemInDarkTheme())
                        dark_Xanthous
                    else
                        light_Xanthous
                }
                High -> {
                    if (isSystemInDarkTheme())
                        dark_Persianred
                    else
                        light_Persianred
                }
                Minimal -> {
                    if (isSystemInDarkTheme())
                        dark_Limegreen
                    else
                        light_Limegreen
                }
                Mild -> {
                    if (isSystemInDarkTheme())
                        dark_Yellowgreen
                    else
                        light_Yellowgreen
                }
                ModeratelySevere -> {
                    if (isSystemInDarkTheme())
                        dark_Ochre
                    else
                        light_Ochre
                }
                Severe -> {
                    if (isSystemInDarkTheme())
                        dark_Persianred
                    else
                        light_Persianred
                }
            }
        }
    }
}
