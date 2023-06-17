package es.upm.bienestaremocional.data.questionnaire

import es.upm.bienestaremocional.data.database.entity.MeasureEntity

abstract class NotScoredManager<in T> where T : MeasureEntity
{
    abstract val answers: Array<Int?>
    abstract val numberOfQuestions : Int
    abstract val answerRange: IntRange

    /**
     * Set answer on the manager
     * @param questionIndex index of the question to be set
     * @param answer value of the answer
     */
    fun setAnswer(questionIndex: Int, answer: Int) 
    {
        if (questionIndex in 0 until numberOfQuestions)
            answers[questionIndex] = answer
    }

    /**
     * Get answer
     * @param questionIndex index of the question to be loaded
     * @return value of the answer
     */
    fun getAnswer(questionIndex: Int): Int? = answers.getOrNull(questionIndex)

    /**
     * Remove answer on the manager
     * @param questionIndex index of the question to be removed
     */
    fun removeAnswer(questionIndex: Int) 
    {
        if (questionIndex in 0 until numberOfQuestions)
            answers[questionIndex] = null
    }
    
    /**
     * Get the answers that hasn't been set
     */
    val answersRemaining: List<Int>
        get() = answers
            .mapIndexed{index, value -> if(value == null) index else null}
            .filterNotNull()

    /**
     * True if all answers has been set, else if any of all hasn't been set
     */
    val questionnaireFulfilled: Boolean
        get() = answers.all { it != null }

    /**
     * Load the answers of the entity
     * @param element to be loaded
     */
    abstract fun loadEntity(element: T)

    /**
     * Set the answers of the entity
     * @param element to be loaded
     */
    abstract fun setEntity(element: T)
}