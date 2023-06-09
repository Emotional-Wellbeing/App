package es.upm.bienestaremocional.data.questionnaire.oneoff

import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffDepression
import es.upm.bienestaremocional.data.questionnaire.ScoreLevel

class OneOffDepressionManager: OneOffManager<OneOffDepression>()
{
    override val answers: Array<Int?> = arrayOfNulls(OneOffQuestionnaire.Depression.numberOfQuestions)
    override val questionsWithInvertedScore : Set<Int> = OneOffQuestionnaire.Depression.questionsWithInvertedScore
    override val answerRange: IntRange = OneOffQuestionnaire.Depression.answerRange
    override val numberOfAnswers : Int = OneOffQuestionnaire.Depression.numberOfAnswers
    override val numberOfQuestions: Int = OneOffQuestionnaire.Depression.numberOfQuestions
    override val questionScoreOffset : Int = OneOffQuestionnaire.Depression.questionScoreOffset
    override val levels: List<ScoreLevel> = OneOffQuestionnaire.Depression.levels

    override fun getAnswers(element: OneOffDepression)
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
    }

    override fun setEntity(element: OneOffDepression) {
        element.apply {
            score = this@OneOffDepressionManager.score
            scoreLevel = this@OneOffDepressionManager.scoreLevel?.level?.id
            completed = this@OneOffDepressionManager.questionnaireFulfilled
            answer1 = this@OneOffDepressionManager.answers.getOrNull(0)
            answer2 = this@OneOffDepressionManager.answers.getOrNull(1)
            answer3 = this@OneOffDepressionManager.answers.getOrNull(2)
            answer4 = this@OneOffDepressionManager.answers.getOrNull(3)
            answer5 = this@OneOffDepressionManager.answers.getOrNull(4)
            answer6 = this@OneOffDepressionManager.answers.getOrNull(5)
            answer7 = this@OneOffDepressionManager.answers.getOrNull(6)
            answer8 = this@OneOffDepressionManager.answers.getOrNull(7)
            answer9 = this@OneOffDepressionManager.answers.getOrNull(8)
        }
    }
}