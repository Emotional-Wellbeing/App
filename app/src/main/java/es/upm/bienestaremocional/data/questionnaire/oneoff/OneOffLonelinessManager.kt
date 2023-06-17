package es.upm.bienestaremocional.data.questionnaire.oneoff

import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffLoneliness
import es.upm.bienestaremocional.data.questionnaire.ScoreLevel

class OneOffLonelinessManager: OneOffManager<OneOffLoneliness>()
{
    override val answers: Array<Int?> = arrayOfNulls(OneOffQuestionnaire.Loneliness.numberOfQuestions)
    override val questionsWithInvertedScore : Set<Int> = OneOffQuestionnaire.Loneliness.questionsWithInvertedScore
    override val answerRange: IntRange = OneOffQuestionnaire.Loneliness.answerRange
    override val numberOfAnswers : Int = OneOffQuestionnaire.Loneliness.numberOfAnswers
    override val numberOfQuestions: Int = OneOffQuestionnaire.Loneliness.numberOfQuestions
    override val questionScoreOffset : Int = OneOffQuestionnaire.Loneliness.questionScoreOffset
    override val levels: List<ScoreLevel> = OneOffQuestionnaire.Loneliness.levels

    override fun loadEntity(element: OneOffLoneliness)
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
        answers[10] = element.answer11
        answers[11] = element.answer12
        answers[12] = element.answer13
        answers[13] = element.answer14
        answers[14] = element.answer15
        answers[15] = element.answer16
        answers[16] = element.answer17
        answers[17] = element.answer18
        answers[18] = element.answer19
        answers[19] = element.answer20
    }

    override fun setEntity(element: OneOffLoneliness) {
        element.apply {
            score = this@OneOffLonelinessManager.score
            scoreLevel = this@OneOffLonelinessManager.scoreLevel?.level?.id
            completed = this@OneOffLonelinessManager.questionnaireFulfilled
            answer1 = this@OneOffLonelinessManager.answers.getOrNull(0)
            answer2 = this@OneOffLonelinessManager.answers.getOrNull(1)
            answer3 = this@OneOffLonelinessManager.answers.getOrNull(2)
            answer4 = this@OneOffLonelinessManager.answers.getOrNull(3)
            answer5 = this@OneOffLonelinessManager.answers.getOrNull(4)
            answer6 = this@OneOffLonelinessManager.answers.getOrNull(5)
            answer7 = this@OneOffLonelinessManager.answers.getOrNull(6)
            answer8 = this@OneOffLonelinessManager.answers.getOrNull(7)
            answer9 = this@OneOffLonelinessManager.answers.getOrNull(8)
            answer10 = this@OneOffLonelinessManager.answers.getOrNull(9)
            answer11 = this@OneOffLonelinessManager.answers.getOrNull(10)
            answer12 = this@OneOffLonelinessManager.answers.getOrNull(11)
            answer13 = this@OneOffLonelinessManager.answers.getOrNull(12)
            answer14 = this@OneOffLonelinessManager.answers.getOrNull(13)
            answer15 = this@OneOffLonelinessManager.answers.getOrNull(14)
            answer16 = this@OneOffLonelinessManager.answers.getOrNull(15)
            answer17 = this@OneOffLonelinessManager.answers.getOrNull(16)
            answer18 = this@OneOffLonelinessManager.answers.getOrNull(17)
            answer19 = this@OneOffLonelinessManager.answers.getOrNull(18)
            answer20 = this@OneOffLonelinessManager.answers.getOrNull(19)
        }
    }
}