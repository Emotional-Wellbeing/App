package es.upm.bienestaremocional.data.questionnaire.daily

import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import es.upm.bienestaremocional.data.questionnaire.NotScoredManager

class DailySuicideManager: NotScoredManager<DailySuicide>()
{
    override val answers: Array<Int?> = arrayOfNulls(DailyNotScoredQuestionnaire.Suicide.numberOfQuestions)
    override val numberOfQuestions: Int = DailyNotScoredQuestionnaire.Suicide.numberOfQuestions
    override val answerRange: IntRange = DailyNotScoredQuestionnaire.Suicide.answerRange

    override fun loadEntity(element: DailySuicide)
    {
        answers[0] = element.answer1
        answers[1] = element.answer2
        answers[2] = element.answer3
    }

    override fun setEntity(element: DailySuicide) {
        element.apply {
            completed = this@DailySuicideManager.questionnaireFulfilled
            answer1 = this@DailySuicideManager.answers.getOrNull(0)
            answer2 = this@DailySuicideManager.answers.getOrNull(1)
            answer3 = this@DailySuicideManager.answers.getOrNull(2)
        }
    }
}