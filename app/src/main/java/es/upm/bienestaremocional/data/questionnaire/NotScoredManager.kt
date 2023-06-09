package es.upm.bienestaremocional.data.questionnaire

import es.upm.bienestaremocional.data.database.entity.MeasureEntity

abstract class NotScoredManager<in T> where T : MeasureEntity
{
    abstract val answers: Array<Int?>
    abstract val numberOfQuestions : Int
    abstract val answerRange: IntRange

    fun setAnswer(questionIndex: Int, answer: Int) {
        if (questionIndex < numberOfQuestions)
            answers[questionIndex] = answer
    }

    fun getAnswer(questionIndex: Int): Int? {
        return if (questionIndex < numberOfQuestions)
            answers[questionIndex]
        else
            null
    }

    fun removeAnswer(questionIndex: Int) {
        if (questionIndex < numberOfQuestions)
            answers[questionIndex] = null
    }

    val answersRemaining: List<Int>
        get() = answers.mapIndexed{index, value -> if(value == null) index else null}.filterNotNull()

    protected val questionnaireFulfilled: Boolean
        get() = answers.all { it != null }

    abstract fun getAnswers(element: T)
    abstract fun setEntity(element: T)
}