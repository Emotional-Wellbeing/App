package es.upm.bienestaremocional.data.questionnaire.daily

import es.upm.bienestaremocional.data.database.entity.daily.DailyStress
import es.upm.bienestaremocional.data.questionnaire.ScoreLevel
import es.upm.bienestaremocional.data.questionnaire.ScoredManager

class DailyStressManager: ScoredManager<DailyStress>()
{
    override val answers: Array<Int?> = arrayOfNulls(DailyScoredQuestionnaire.Stress.numberOfQuestions)
    override val numberOfQuestions: Int = DailyScoredQuestionnaire.Stress.numberOfQuestions
    override val answerRange: IntRange = DailyScoredQuestionnaire.Stress.answerRange
    override val levels: List<ScoreLevel> = DailyScoredQuestionnaire.Stress.levels

    override fun loadEntity(element: DailyStress)
    {
        answers[0] = element.answer1
        answers[1] = element.answer2
        answers[2] = element.answer3
        answers[3] = element.answer4
    }

    override fun setEntity(element: DailyStress) {
        element.apply {
            score = this@DailyStressManager.score
            scoreLevel = this@DailyStressManager.scoreLevel?.level?.id
            completed = this@DailyStressManager.questionnaireFulfilled
            answer1 = this@DailyStressManager.answers.getOrNull(0)
            answer2 = this@DailyStressManager.answers.getOrNull(1)
            answer3 = this@DailyStressManager.answers.getOrNull(2)
            answer4 = this@DailyStressManager.answers.getOrNull(3)
        }
    }
}