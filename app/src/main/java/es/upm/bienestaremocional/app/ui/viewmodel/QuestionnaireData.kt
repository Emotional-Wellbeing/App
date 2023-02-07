package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.ui.state.QuestionnaireState

class QuestionnaireData(val questionnaire: Questionnaire,
                        private val additionalText: String = "")
{
    private val previousTitleTextRes = R.string.questionnaire
    private val questionnaireLabelRes = questionnaire.labelRes

    var state : MutableState<QuestionnaireState> = mutableStateOf(QuestionnaireState.InProgress)

    private val answers: Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)

    @Composable
    fun title(): String = if(additionalText != "")
        "${stringResource(previousTitleTextRes)} $additionalText ${stringResource(questionnaireLabelRes)}"
    else
        "${stringResource(previousTitleTextRes)} ${stringResource(questionnaireLabelRes)}"

    fun answersRemaining(): List<Int> {
        return List(answers.filter { it == null }.size) { index -> index }
    }

    private fun allAnswersFulfilled(): Boolean {
        return answers.all { it != null }
    }

    fun getScore(): Int? {
        var score: Int? = null
        if (allAnswersFulfilled())
        {
            score = 0
            answers.forEachIndexed { index, answer ->
                var tempScore = (answer?.plus(questionnaire.questionScoreOffset)!!)
                if (questionnaire.questionsWithInvertedScore.contains(index))
                    tempScore = questionnaire.numberOfQuestions - 1 - tempScore
                score += tempScore
            }
        }
        return score
    }

    fun setAnswer(questionIndex: Int, answerIndex: Int) {
        if (questionIndex < questionnaire.numberOfQuestions)
            answers[questionIndex] = answerIndex
    }

    fun getAnswer(questionIndex: Int): Int? {
        return if (questionIndex < questionnaire.numberOfQuestions)
            answers[questionIndex]
        else
            null
    }

    fun removeAnswer(questionIndex: Int) {
        if (questionIndex < questionnaire.numberOfQuestions)
            answers[questionIndex] = null
    }
}