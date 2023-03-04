package es.upm.bienestaremocional.app.data.questionnaire

/**
 * Class with the intervals of the score in the questionnaires
 * @param min: Lower bound of the interval
 * @param min: Upper bound of the interval
 * @param levelLabel: Label of the interval
 */
data class ScoreLevel(
    val min : Int,
    val max: Int,
    val levelLabel: LevelLabel
)
