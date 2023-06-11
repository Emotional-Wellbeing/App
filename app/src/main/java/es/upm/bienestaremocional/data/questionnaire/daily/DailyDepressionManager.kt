package es.upm.bienestaremocional.data.questionnaire.daily

import es.upm.bienestaremocional.data.database.entity.daily.DailyDepression
import es.upm.bienestaremocional.data.questionnaire.ScoreLevel
import es.upm.bienestaremocional.data.questionnaire.ScoredManager

class DailyDepressionManager: ScoredManager<DailyDepression>()
{
    override val answers: Array<Int?> = Array(DailyScoredQuestionnaire.Depression.numberOfQuestions) { 0 }
    override val numberOfQuestions: Int = DailyScoredQuestionnaire.Depression.numberOfQuestions
    override val answerRange: IntRange = DailyScoredQuestionnaire.Depression.answerRange
    override val levels: List<ScoreLevel> = DailyScoredQuestionnaire.Depression.levels

    override fun loadEntity(element: DailyDepression)
    {
        answers[0] = element.answer1
        answers[1] = element.answer2
        answers[2] = element.answer3
    }

    override fun setEntity(element: DailyDepression) {
        element.apply {
            score = this@DailyDepressionManager.score
            scoreLevel = this@DailyDepressionManager.scoreLevel?.level?.id
            completed = this@DailyDepressionManager.questionnaireFulfilled
            answer1 = this@DailyDepressionManager.answers.getOrNull(0)
            answer2 = this@DailyDepressionManager.answers.getOrNull(1)
            answer3 = this@DailyDepressionManager.answers.getOrNull(2)
        }
    }
}