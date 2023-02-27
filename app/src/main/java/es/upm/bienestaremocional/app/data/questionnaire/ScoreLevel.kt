package es.upm.bienestaremocional.app.data.questionnaire

import androidx.annotation.StringRes

data class ScoreLevel(
    val min : Int,
    val max: Int,
    val internalLabel: String,
    @StringRes val label: Int,
)
