package es.upm.bienestaremocional.data.questionnaire.daily

import es.upm.bienestaremocional.data.questionnaire.Questionnaire

/**
 * Information of the available Questionnaire on the app
 * @param measureRes: StringResource with the label of the measure
 * @param questionRes: ArrayResource with the questions available in the questionnaire
 * @param numberOfQuestions: Number of the questions in the questionnaire
 */
enum class DailyNotScoredQuestionnaire(override val numberOfQuestions : Int,
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