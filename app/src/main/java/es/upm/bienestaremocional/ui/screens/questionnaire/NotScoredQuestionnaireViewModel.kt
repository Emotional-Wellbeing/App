package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.ViewModel
import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.NotScoredManager
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

/*
private val questionnaire = QuestionnaireScreenDestination.argsFrom(savedStateHandle).questionnaire
    private val entityId = QuestionnaireScreenDestination.argsFrom(savedStateHandle).entityId
 */

/*
@HiltViewModel
class QuestionnaireViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @Named("logTag") val logTag : String,
    val measureEntity: MeasureEntity,
    val repository: QuestionnaireRepository<MeasureEntity>,
    val manager: NotScoredManager<MeasureEntity>,
) : ViewModel()
 */
abstract class NotScoredQuestionnaireViewModel(
    protected val repository: QuestionnaireRepository<MeasureEntity>,
    protected open val manager: NotScoredManager<MeasureEntity>,
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

    protected fun loadAnswers()
    {
        measureEntity?.let { manager.loadEntity(it) }
    }

    private suspend fun updateQuestionnaire()
    {
        measureEntity?.let {
            manager.setEntity(it)
            repository.update(it)
        }
    }
}