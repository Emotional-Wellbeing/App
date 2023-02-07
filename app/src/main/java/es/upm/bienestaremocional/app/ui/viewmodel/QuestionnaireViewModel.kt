package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire

class QuestionnaireViewModelFactory(private val questionnaire: Questionnaire) :
    ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(QuestionnaireViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return QuestionnaireViewModel(questionnaire) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class QuestionnaireViewModel(val questionnaire: Questionnaire) : ViewModel()
{
    val previousTitleTextRes = R.string.questionnaire

    var state : QuestionnaireState by mutableStateOf(QuestionnaireState.InProgress)

    private val answers: Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)

    fun countAnswersRemaining(): Int {
        return answers.count { it == null }
    }

    fun answersRemaining(): List<Int> {
        return List(answers.filter { it == null }.size) { index -> index }
    }

    fun allAnswersFulfilled(): Boolean {
        return answers.all { it != null }
    }

    fun getScore(): Int? {
        var score: Int? = null
        if (allAnswersFulfilled()) {
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