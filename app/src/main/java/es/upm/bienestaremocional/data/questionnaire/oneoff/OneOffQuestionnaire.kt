package es.upm.bienestaremocional.data.questionnaire.oneoff

import es.upm.bienestaremocional.data.questionnaire.Level
import es.upm.bienestaremocional.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.data.questionnaire.QuestionnaireScored
import es.upm.bienestaremocional.data.questionnaire.ScoreLevel

/**
 * Information of the available Questionnaire on the app
 * @param measureRes: StringResource with the label of the measure
 * @param questionRes: ArrayResource with the questions available in the questionnaire
 * @param answerRes: ArrayResource with the answers available in the questionnaire
 * @param numberOfQuestions: Number of the questions in the questionnaire
 * @param numberOfAnswers: Number of the answers in the questionnaire
 * @param questionScoreOffset: Offset of the score of each question of the answer
 * @param questionsWithInvertedScore: Set with the indexes of the inverted score questions
 * @param levels: List of the ScoreLevel of the questionnaire
 */
enum class OneOffQuestionnaire(override val numberOfQuestions : Int,
                               override val answerRange: IntRange,
                               override val levels: List<ScoreLevel>,
                               override val minScore: Int,
                               override val maxScore: Int,
                               val numberOfAnswers : Int,
                               val questionScoreOffset : Int = 0,
                               val questionsWithInvertedScore : Set<Int> = setOf(),

) : Questionnaire, QuestionnaireScored
{
    Stress(
        numberOfQuestions = 10,
        answerRange = 0..4,
        numberOfAnswers = 5,
        questionsWithInvertedScore = setOf(3,4,6,7),
        levels = listOf(
            ScoreLevel(0,13, Level.Low),
            ScoreLevel(14,26, Level.Moderate),
            ScoreLevel(27,40, Level.High),
        ),
        minScore = 0,
        maxScore = 40,
    ),
    Depression(
        numberOfQuestions = 9,
        answerRange = 0..3,
        numberOfAnswers = 4,
        levels = listOf(
            ScoreLevel(0,4, Level.Minimal),
            ScoreLevel(5,9, Level.Mild),
            ScoreLevel(10,14, Level.Moderate),
            ScoreLevel(15,19, Level.ModeratelySevere),
            ScoreLevel(20,27, Level.Severe),
        ),
        minScore = 0,
        maxScore = 27,
    ),
    Loneliness(
        numberOfQuestions = 20,
        answerRange = 0..3,
        numberOfAnswers = 4,
        questionScoreOffset = 1,
        questionsWithInvertedScore = setOf(0, 4, 5, 8, 9, 14, 15, 18, 19),
        levels = listOf(
            ScoreLevel(20,40, Level.Low),
            ScoreLevel(41,60, Level.Moderate),
            ScoreLevel(61,80, Level.High),
        ),
        minScore = 20,
        maxScore = 80,
    );
}