package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.ViewModel
import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.ScoredManager
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

// If we want inherit NotScoredQuestionnaireViewModel and add our score, we need to override
// the type of the manager and we achieve this setting manager as open val.
// If we do this change, we cannot use loadAnswers on init block on NotScoredQuestionnaireViewModel
// see https://stackoverflow.com/questions/57989947/null-value-in-init-block-of-parent-class

abstract class ScoredQuestionnaireViewModel(
    protected val repository: QuestionnaireRepository<MeasureEntity>,
    protected val manager: ScoredManager<MeasureEntity>,
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

    val score
        get() = manager.score

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