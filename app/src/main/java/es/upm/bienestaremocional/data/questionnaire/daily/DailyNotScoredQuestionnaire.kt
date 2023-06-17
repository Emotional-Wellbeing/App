package es.upm.bienestaremocional.data.questionnaire.daily

import es.upm.bienestaremocional.data.questionnaire.Questionnaire

/**
 * Information of the available daily questionnaires of the app without score
 * @param numberOfQuestions: Number of the questions in the questionnaire
 * @param answerRange: Inclusive range of the valid responses on a answer
 */
enum class DailyNotScoredQuestionnaire(
    override val numberOfQuestions : Int,
    override val answerRange: IntRange,
) : Questionnaire
{
    Suicide(
        numberOfQuestions = 3,
        answerRange = 0..1,
    ),
    Symptoms(
        numberOfQuestions = 6,
        answerRange = 0..2,
    );
}