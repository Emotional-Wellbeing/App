package es.upm.bienestaremocional.app.data.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.UCLA

class UCLAManager: QuestionnaireManager<UCLA>() 
{
    override val answers: Array<Int?> = arrayOfNulls(Questionnaire.UCLA.numberOfQuestions)
    override val questionsWithInvertedScore : Set<Int> = Questionnaire.UCLA.questionsWithInvertedScore
    override val numberOfAnswers : Int = Questionnaire.UCLA.numberOfAnswers
    override val numberOfQuestions: Int = Questionnaire.UCLA.numberOfQuestions
    override val questionScoreOffset : Int = Questionnaire.UCLA.questionScoreOffset
    override val levels: List<ScoreLevel> = Questionnaire.UCLA.levels

    override fun getAnswers(element: UCLA)
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

    override fun setEntity(element: UCLA) {
        element.apply {
            score = this@UCLAManager.score
            scoreLevel = this@UCLAManager.scoreLevel?.level?.id
            completed = this@UCLAManager.questionnaireFulfilled
            answer1 = this@UCLAManager.answers.getOrNull(0)
            answer2 = this@UCLAManager.answers.getOrNull(1)
            answer3 = this@UCLAManager.answers.getOrNull(2)
            answer4 = this@UCLAManager.answers.getOrNull(3)
            answer5 = this@UCLAManager.answers.getOrNull(4)
            answer6 = this@UCLAManager.answers.getOrNull(5)
            answer7 = this@UCLAManager.answers.getOrNull(6)
            answer8 = this@UCLAManager.answers.getOrNull(7)
            answer9 = this@UCLAManager.answers.getOrNull(8)
            answer10 = this@UCLAManager.answers.getOrNull(9)
            answer11 = this@UCLAManager.answers.getOrNull(10)
            answer12 = this@UCLAManager.answers.getOrNull(11)
            answer13 = this@UCLAManager.answers.getOrNull(12)
            answer14 = this@UCLAManager.answers.getOrNull(13)
            answer15 = this@UCLAManager.answers.getOrNull(14)
            answer16 = this@UCLAManager.answers.getOrNull(15)
            answer17 = this@UCLAManager.answers.getOrNull(16)
            answer18 = this@UCLAManager.answers.getOrNull(17)
            answer19 = this@UCLAManager.answers.getOrNull(18)
            answer20 = this@UCLAManager.answers.getOrNull(19)
        }
    }
}