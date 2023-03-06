package es.upm.bienestaremocional.app.data.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireEntity

abstract class QuestionnaireManager<in T> where T : QuestionnaireEntity
{

    abstract val answers: Array<Int?>
    abstract val questionsWithInvertedScore : Set<Int>
    abstract val numberOfAnswers : Int
    abstract val numberOfQuestions : Int
    abstract val questionScoreOffset : Int
    abstract val levels: List<ScoreLevel>

    fun setAnswer(questionIndex: Int, answerIndex: Int) {
        if (questionIndex < numberOfQuestions)
            answers[questionIndex] = answerIndex
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

    val score : Int?
        get()
        {
            return if (questionnaireFulfilled)
            {
                var auxiliarScore = 0
                answers.forEachIndexed { index, answer ->

                    var tempScore = answer!!
                    if (questionsWithInvertedScore.contains(index))
                        tempScore = numberOfAnswers - 1 - tempScore
                    tempScore += questionScoreOffset

                    auxiliarScore += tempScore
                }
                auxiliarScore
            }
            else
                null
        }

    val scoreLevel : ScoreLevel?
        get()
        {
            return if (questionnaireFulfilled)
            {
                var scoreLevel: ScoreLevel? = null
                for(level in levels)
                {
                    if (score in level.min .. level.max)
                    {
                        scoreLevel = level
                        break
                    }
                }
                scoreLevel
            }
            else
                null
        }

    abstract fun getAnswers(element: T)
    abstract fun setEntity(element: T)
}