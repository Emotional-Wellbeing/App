package es.upm.bienestaremocional.data.questionnaire.daily

import es.upm.bienestaremocional.data.database.entity.daily.DailyLoneliness
import es.upm.bienestaremocional.data.questionnaire.ScoreLevel
import es.upm.bienestaremocional.data.questionnaire.ScoredManager

class DailyLonelinessManager: ScoredManager<DailyLoneliness>()
{
    override val answers: Array<Int?> = Array(DailyScoredQuestionnaire.Loneliness.numberOfQuestions) { 0 }
    override val numberOfQuestions: Int = DailyScoredQuestionnaire.Loneliness.numberOfQuestions
    override val answerRange: IntRange = DailyScoredQuestionnaire.Loneliness.answerRange
    override val levels: List<ScoreLevel> = DailyScoredQuestionnaire.Loneliness.levels

    override fun loadEntity(element: DailyLoneliness)
    {
        answers[0] = element.answer1
        answers[1] = element.answer2
        answers[2] = element.answer3
        answers[3] = element.answer4
    }

    override fun setEntity(element: DailyLoneliness) {
        element.apply {
            score = this@DailyLonelinessManager.score
            scoreLevel = this@DailyLonelinessManager.scoreLevel?.level?.id
            completed = this@DailyLonelinessManager.questionnaireFulfilled
            answer1 = this@DailyLonelinessManager.answers.getOrNull(0)
            answer2 = this@DailyLonelinessManager.answers.getOrNull(1)
            answer3 = this@DailyLonelinessManager.answers.getOrNull(2)
            answer4 = this@DailyLonelinessManager.answers.getOrNull(3)
        }
    }
}