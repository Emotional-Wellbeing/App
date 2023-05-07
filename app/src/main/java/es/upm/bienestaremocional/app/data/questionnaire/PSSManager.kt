package es.upm.bienestaremocional.app.data.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.PSS

class PSSManager: QuestionnaireManager<PSS>() 
{
    override val answers: Array<Int?> = arrayOfNulls(Questionnaire.PSS.numberOfQuestions)
    override val questionsWithInvertedScore : Set<Int> = Questionnaire.PSS.questionsWithInvertedScore
    override val numberOfAnswers : Int = Questionnaire.PSS.numberOfAnswers
    override val numberOfQuestions: Int = Questionnaire.PSS.numberOfQuestions
    override val questionScoreOffset : Int = Questionnaire.PSS.questionScoreOffset
    override val levels: List<ScoreLevel> = Questionnaire.PSS.levels

    override fun getAnswers(element: PSS)
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

    override fun setEntity(element: PSS) {
        element.apply {
            score = this@PSSManager.score
            scoreLevel = this@PSSManager.scoreLevel?.level?.id
            completed = this@PSSManager.questionnaireFulfilled
            answer1 = this@PSSManager.answers.getOrNull(0)
            answer2 = this@PSSManager.answers.getOrNull(1)
            answer3 = this@PSSManager.answers.getOrNull(2)
            answer4 = this@PSSManager.answers.getOrNull(3)
            answer5 = this@PSSManager.answers.getOrNull(4)
            answer6 = this@PSSManager.answers.getOrNull(5)
            answer7 = this@PSSManager.answers.getOrNull(6)
            answer8 = this@PSSManager.answers.getOrNull(7)
            answer9 = this@PSSManager.answers.getOrNull(8)
            answer10 = this@PSSManager.answers.getOrNull(9)
        }
    }
}