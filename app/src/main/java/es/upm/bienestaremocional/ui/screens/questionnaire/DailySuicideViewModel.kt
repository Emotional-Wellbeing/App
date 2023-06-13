package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import es.upm.bienestaremocional.data.questionnaire.Level
import es.upm.bienestaremocional.data.questionnaire.daily.DailySuicideManager
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySuicideRepository
import es.upm.bienestaremocional.ui.component.questionnaire.animationDurationMillis
import es.upm.bienestaremocional.ui.screens.destinations.DailySuicideScreenDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
@HiltViewModel
class DailySuicideViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: DailySuicideRepository,
    private val manager : DailySuicideManager
) : ViewModel()
{
    //state
    private val _state : MutableStateFlow<SuicideScreenState> = MutableStateFlow(SuicideScreenState.InProgress)
    val state : StateFlow<SuicideScreenState> get() = _state.asStateFlow()

    private val _questionNumber : MutableStateFlow<Int> = MutableStateFlow(0)
    val questionNumber : StateFlow<Int> get() = _questionNumber.asStateFlow()

    private var measureEntity: DailySuicide? = null

    val entityId = DailySuicideScreenDestination.argsFrom(savedStateHandle).entityId

    var level = Level.Low

    init {
        runBlocking {
            measureEntity = repository.get(entityId)
        }
        if(measureEntity?.completed == true)
        {
            _state.value = SuicideScreenState.Finished
        }
        else
        {
            loadAnswers()
        }
    }

    fun onSkippingAttempt()
    {
        _state.value = SuicideScreenState.SkipAttempt
    }

    fun onInProgress()
    {
        _state.value = SuicideScreenState.InProgress
    }

    fun onSkipped()
    {
        runBlocking {
            updateQuestionnaire()
        }
        _state.value = SuicideScreenState.Skipped
    }

    fun onSummary()
    {
        runBlocking {
            updateQuestionnaire()
        }
        _state.value = SuicideScreenState.Finished
    }

    suspend fun onAnswer(question: Int, answer: Int)
    {
        manager.apply {
            setAnswer(question,answer)
        }

        delay(animationDurationMillis.toLong())

        //If user answers no, we don't ask more questions, or if we don't have more questions to ask
        if(answer == 1 || _questionNumber.value == manager.numberOfQuestions - 1)
        {
            // If user answer no, level depends on what index is
            level = if(answer == 1)
            {
                if (question == 0)
                    Level.Low
                else
                    Level.Moderate
            }
            // If all answers were yes, level is high
            else
            {
                Level.High
            }
            manager.setCompleted()
            _state.value = SuicideScreenState.Summary
        }
        else
            _questionNumber.value++
    }

    fun answerSelected(question: Int) = manager.getAnswer(question)

    private fun loadAnswers()
    {
        measureEntity?.let { manager.loadEntity(it) }

        if (measureEntity?.answer1 != null)
        {
            if (measureEntity?.answer2 != null)
                _questionNumber.value = 2
            else
                _questionNumber.value = 1
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