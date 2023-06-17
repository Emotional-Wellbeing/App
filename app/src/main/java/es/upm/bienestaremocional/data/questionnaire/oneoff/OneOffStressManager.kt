package es.upm.bienestaremocional.data.questionnaire.oneoff

import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffStress
import es.upm.bienestaremocional.data.questionnaire.ScoreLevel

class OneOffStressManager: OneOffManager<OneOffStress>()
{
    override val answers: Array<Int?> = arrayOfNulls(OneOffQuestionnaire.Stress.numberOfQuestions)
    override val questionsWithInvertedScore : Set<Int> = OneOffQuestionnaire.Stress.questionsWithInvertedScore
    override val answerRange: IntRange = OneOffQuestionnaire.Stress.answerRange
    override val numberOfAnswers : Int = OneOffQuestionnaire.Stress.numberOfAnswers
    override val numberOfQuestions: Int = OneOffQuestionnaire.Stress.numberOfQuestions
    override val questionScoreOffset : Int = OneOffQuestionnaire.Stress.questionScoreOffset
    override val levels: List<ScoreLevel> = OneOffQuestionnaire.Stress.levels

    override fun loadEntity(element: OneOffStress)
    {
        answers[0] = element.answer1
        answers[1] = element.answer2
        answers[2] = element.answer3
        answers[3] = element.answer4
        answers[4] = element.answer5
        answers[5] = element.answer6
        answers[6] = element.answer7
        answers[7] = element.answer8
        answers[8] = element.answer9
        answers[9] = element.answer10
    }

    override fun setEntity(element: OneOffStress) {
        element.apply {
            score = this@OneOffStressManager.score
            scoreLevel = this@OneOffStressManager.scoreLevel?.level?.id
            completed = this@OneOffStressManager.questionnaireFulfilled
            answer1 = this@OneOffStressManager.answers.getOrNull(0)
            answer2 = this@OneOffStressManager.answers.getOrNull(1)
            answer3 = this@OneOffStressManager.answers.getOrNull(2)
            answer4 = this@OneOffStressManager.answers.getOrNull(3)
            answer5 = this@OneOffStressManager.answers.getOrNull(4)
            answer6 = this@OneOffStressManager.answers.getOrNull(5)
            answer7 = this@OneOffStressManager.answers.getOrNull(6)
            answer8 = this@OneOffStressManager.answers.getOrNull(7)
            answer9 = this@OneOffStressManager.answers.getOrNull(8)
            answer10 = this@OneOffStressManager.answers.getOrNull(9)
        }
    }
}