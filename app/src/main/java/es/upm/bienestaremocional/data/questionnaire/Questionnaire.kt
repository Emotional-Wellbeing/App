package es.upm.bienestaremocional.data.questionnaire

/**
 * Contain the essential data of all questionnaires
 */
interface Questionnaire {
    val numberOfQuestions : Int
    val answerRange: IntRange
}