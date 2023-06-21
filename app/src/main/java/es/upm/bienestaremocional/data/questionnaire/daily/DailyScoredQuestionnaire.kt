package es.upm.bienestaremocional.data.questionnaire.daily

import es.upm.bienestaremocional.data.Measure
import es.upm.bienestaremocional.data.questionnaire.Level
import es.upm.bienestaremocional.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.data.questionnaire.QuestionnaireScored
import es.upm.bienestaremocional.data.questionnaire.ScoreLevel

/**
 * Information of the available daily questionnaires of the app with score
 * @param measure Measure which is implemented by the questionnaire
 * @param numberOfQuestions Number of the questions in the questionnaire
 * @param answerRange Inclusive range of the valid responses on a answer
 * @param levels List of the score levels of the questionnaire
 * @param minScore Minimum possible score for the questionnaire
 * @param maxScore Maximum possible score for the questionnaire
 */

enum class DailyScoredQuestionnaire(
    val measure: Measure,
    override val numberOfQuestions: Int,
    override val answerRange: IntRange,
    override val levels: List<ScoreLevel>,
    override val minScore: Int,
    override val maxScore: Int,
) : Questionnaire, QuestionnaireScored {
    Stress(
        measure = Measure.Stress,
        numberOfQuestions = 4,
        answerRange = 0..10,
        levels = listOf(
            ScoreLevel(0, 13, Level.Low),
            ScoreLevel(14, 27, Level.Moderate),
            ScoreLevel(28, 40, Level.High)
        ),
        minScore = 0,
        maxScore = 40
    ),
    Depression(
        measure = Measure.Depression,
        numberOfQuestions = 3,
        answerRange = 0..10,
        levels = listOf(
            ScoreLevel(0, 10, Level.Low),
            ScoreLevel(11, 20, Level.Moderate),
            ScoreLevel(21, 30, Level.High)
        ),
        minScore = 0,
        maxScore = 30
    ),
    Loneliness(
        measure = Measure.Loneliness,
        numberOfQuestions = 4,
        answerRange = 0..10,
        levels = listOf(
            ScoreLevel(0, 13, Level.Low),
            ScoreLevel(14, 27, Level.Moderate),
            ScoreLevel(28, 40, Level.High)
        ),
        minScore = 0,
        maxScore = 40
    );

    companion object {
        /**
         * Obtain a [DailyScoredQuestionnaire] from a certain [Measure]
         * @param measure to decode
         * @return [DailyScoredQuestionnaire] related if is any
         */
        fun fromMeasure(measure: Measure): DailyScoredQuestionnaire? {
            return when (measure) {
                Measure.Stress -> Stress
                Measure.Depression -> Depression
                Measure.Loneliness -> Loneliness
                Measure.Suicide -> null
                Measure.Symptoms -> null
            }
        }
    }
}
