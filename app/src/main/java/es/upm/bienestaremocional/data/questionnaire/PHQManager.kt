package es.upm.bienestaremocional.data.questionnaire

import es.upm.bienestaremocional.data.database.entity.PHQ

class PHQManager: QuestionnaireManager<PHQ>()
{
    override val answers: Array<Int?> = arrayOfNulls(Questionnaire.PHQ.numberOfQuestions)
    override val questionsWithInvertedScore : Set<Int> = Questionnaire.PHQ.questionsWithInvertedScore
    override val numberOfAnswers : Int = Questionnaire.PHQ.numberOfAnswers
    override val numberOfQuestions: Int = Questionnaire.PHQ.numberOfQuestions
    override val questionScoreOffset : Int = Questionnaire.PHQ.questionScoreOffset
    override val levels: List<ScoreLevel> = Questionnaire.PHQ.levels

    override fun getAnswers(element: PHQ)
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

    override fun setEntity(element: PHQ) {
        element.apply {
            score = this@PHQManager.score
            scoreLevel = this@PHQManager.scoreLevel?.level?.id
            completed = this@PHQManager.questionnaireFulfilled
            answer1 = this@PHQManager.answers.getOrNull(0)
            answer2 = this@PHQManager.answers.getOrNull(1)
            answer3 = this@PHQManager.answers.getOrNull(2)
            answer4 = this@PHQManager.answers.getOrNull(3)
            answer5 = this@PHQManager.answers.getOrNull(4)
            answer6 = this@PHQManager.answers.getOrNull(5)
            answer7 = this@PHQManager.answers.getOrNull(6)
            answer8 = this@PHQManager.answers.getOrNull(7)
            answer9 = this@PHQManager.answers.getOrNull(8)
        }
    }
}