package es.upm.bienestaremocional.app.data.questionnaire

import androidx.annotation.StringRes
import es.upm.bienestaremocional.R

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
    }
}
