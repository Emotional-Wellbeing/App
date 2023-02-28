package es.upm.bienestaremocional.app.data.questionnaire

import androidx.annotation.StringRes
import es.upm.bienestaremocional.R

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
        fun decodeFromId(id: String?) : LevelLabel?
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
