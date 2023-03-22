package es.upm.bienestaremocional.app.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireEntity
import es.upm.bienestaremocional.app.data.questionnaire.*
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.UCLARepository
import es.upm.bienestaremocional.app.ui.screens.destinations.QuestionnaireScreenDestination
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(
    pssRepository: PSSRepository,
    phqRepository: PHQRepository,
    uclaRepository: UCLARepository,
    savedStateHandle: SavedStateHandle,
    @Named("logTag") val logTag : String
) : ViewModel()
{
    //state
    private val _state : MutableStateFlow<QuestionnaireState> = MutableStateFlow(QuestionnaireState.InProgress)
    val state : StateFlow<QuestionnaireState> get() = _state.asStateFlow()

    private val questionnaire = QuestionnaireScreenDestination.argsFrom(savedStateHandle).questionnaire
    private val entityId = QuestionnaireScreenDestination.argsFrom(savedStateHandle).entityId

    private var questionnaireEntity : QuestionnaireEntity?
    private var repository : QuestionnaireRepository<QuestionnaireEntity>
    var manager: QuestionnaireManager<QuestionnaireEntity>

    init {
        runBlocking {
            @Suppress("UNCHECKED_CAST")
            when(questionnaire)
            {
                Questionnaire.PSS -> {
                    questionnaireEntity = pssRepository.get(entityId)
                    repository = pssRepository as QuestionnaireRepository<QuestionnaireEntity>
                    manager = PSSManager() as QuestionnaireManager<QuestionnaireEntity>
                }
                Questionnaire.PHQ -> {
                    questionnaireEntity = phqRepository.get(entityId)
                    repository = phqRepository as QuestionnaireRepository<QuestionnaireEntity>
                    manager = PHQManager() as QuestionnaireManager<QuestionnaireEntity>
                }
                Questionnaire.UCLA -> {
                    questionnaireEntity = uclaRepository.get(entityId)
                    repository = uclaRepository as QuestionnaireRepository<QuestionnaireEntity>
                    manager = UCLAManager() as QuestionnaireManager<QuestionnaireEntity>
                }
            }
        }
        loadAnswers()
    }

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

    private fun loadAnswers()
    {
        questionnaireEntity?.let {
            manager.getAnswers(it)
        }
    }

    private suspend fun updateQuestionnaire()
    {
        questionnaireEntity?.let {
            manager.setEntity(it)
            repository.update(it)
        }
    }
}