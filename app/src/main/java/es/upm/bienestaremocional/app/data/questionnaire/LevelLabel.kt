package es.upm.bienestaremocional.app.data.questionnaire

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import es.upm.bienestaremocional.R

/**
 * Contains available levels of the results of the questionnaires
 * @param id: String with the id used to save in database
 * @param label: StringResource with the label used to show in screens
 * @param color: Color used in plots
 */
enum class LevelLabel(
    val id: String,
    @StringRes val label: Int,
    val color: Color
)
{
    //TODO select proper color por each one with material design builder
    Low("low", R.string.low, Color.Red),
    Moderate("moderate",R.string.moderate, Color.Green),
    High("high",R.string.high, Color.Blue),
    Minimal("minimal",R.string.minimal, Color.Red),
    Mild("mild",R.string.mild, Color.Yellow),
    ModeratelySevere("moderately_severe",R.string.moderately_severe, Color.Cyan),
    Severe("severe",R.string.severe, Color.Blue);

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
    }
}
