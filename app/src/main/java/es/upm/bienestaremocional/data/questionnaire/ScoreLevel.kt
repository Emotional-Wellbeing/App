package es.upm.bienestaremocional.data.questionnaire

/**
 * Class with the intervals of the score in the questionnaires
 * @param min: Lower bound of the interval
 * @param min: Upper bound of the interval
 * @param level: Label of the interval
 */
data class ScoreLevel(
    val min: Int,
    val max: Int,
    val level: Level
)
