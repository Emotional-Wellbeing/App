package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.ViewModel
import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.NotScoredManager
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

abstract class NotScoredQuestionnaireViewModel(
    protected val repository: QuestionnaireRepository<MeasureEntity>,
    protected val manager: NotScoredManager<MeasureEntity>,
    protected val entityId: Long,
) : ViewModel()
{
    //state
    private val _state : MutableStateFlow<QuestionnaireState> = MutableStateFlow(QuestionnaireState.InProgress)
    val state : StateFlow<QuestionnaireState> get() = _state.asStateFlow()

    private var measureEntity: MeasureEntity? = null

    init {
        runBlocking {
            measureEntity = repository.get(entityId)
        }
        if(measureEntity?.completed == true)
        {
            _state.value = QuestionnaireState.Finished
        }
        else
        {
            loadAnswers()
        }
    }

    val answersRemaining
        get() = manager.answersRemaining

    fun onSkippingAttempt()
    {
        _state.value = QuestionnaireState.SkipAttempt
    }

    fun onInProgress()
    {
        _state.value = QuestionnaireState.InProgress
    }

    fun onSkipped()
    {
        runBlocking {
            updateQuestionnaire()
        }
        _state.value = QuestionnaireState.Skipped
    }

    fun onFinishAttempt()
    {
        val listAnswersRemaining = manager.answersRemaining
        if (listAnswersRemaining.isEmpty())
            _state.value = QuestionnaireState.Summary
        else
            _state.value = QuestionnaireState.FinishAttempt
    }

    fun onSummary()
    {
        runBlocking {
            updateQuestionnaire()
        }
        _state.value = QuestionnaireState.Finished
    }

    fun onAnswer(question: Int, answer: Int)
    {
        manager.apply {
            if (getAnswer(question) != answer)
                setAnswer(question,answer)
            else
                removeAnswer(question)
        }
    }

    fun answerSelected(question: Int) = manager.getAnswer(question)

    private fun loadAnswers()
    {
        measureEntity?.let {
            manager.loadEntity(it)
        }
    }

    private suspend fun updateQuestionnaire()
    {
        measureEntity?.let {
            manager.setEntity(it)
            repository.update(it)
        }
    }
}