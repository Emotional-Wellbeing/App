package es.upm.bienestaremocional.data.questionnaire.daily

import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide

class DailySuicideManager {
    val answers: Array<Int?> = arrayOfNulls(DailyNotScoredQuestionnaire.Suicide.numberOfQuestions)
    val numberOfQuestions: Int = DailyNotScoredQuestionnaire.Suicide.numberOfQuestions
    val answerRange: IntRange = DailyNotScoredQuestionnaire.Suicide.answerRange

    private val answerToContinue = 0
    val answerToSkip = 1

    val completed: Boolean
        get() = answers.any { it == answerToSkip } || answers.all { it == answerToContinue }

    /**
     * Set answer on the manager
     * @param questionIndex index of the question to be set
     * @param answer value of the answer
     */
    fun setAnswer(questionIndex: Int, answer: Int) {
        if (questionIndex in 0 until numberOfQuestions)
            answers[questionIndex] = answer
    }

    /**
     * Get answer
     * @param questionIndex index of the question to be loaded
     * @return value of the answer
     */
    fun getAnswer(questionIndex: Int): Int? = answers.getOrNull(questionIndex)


    fun loadEntity(element: DailySuicide) {
        answers[0] = element.answer1
        answers[1] = element.answer2
        answers[2] = element.answer3
    }

    fun setEntity(element: DailySuicide) {
        element.apply {
            completed = this@DailySuicideManager.completed
            answer1 = this@DailySuicideManager.answers.getOrNull(0)
            answer2 = this@DailySuicideManager.answers.getOrNull(1)
            answer3 = this@DailySuicideManager.answers.getOrNull(2)
        }
    }
}