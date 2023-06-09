package es.upm.bienestaremocional.data.questionnaire

interface Questionnaire {
    val numberOfQuestions : Int
    val answerRange: IntRange
}