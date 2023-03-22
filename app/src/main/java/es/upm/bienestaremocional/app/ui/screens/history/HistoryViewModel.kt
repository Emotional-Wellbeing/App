package es.upm.bienestaremocional.app.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireEntity
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.domain.aggregateEntriesPerDay
import es.upm.bienestaremocional.app.domain.aggregateEntriesPerMonth
import es.upm.bienestaremocional.app.domain.aggregateEntriesPerWeek
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.UCLARepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val pssRepository: PSSRepository,
    private val phqRepository: PHQRepository,
    private val uclaRepository: UCLARepository,
) : ViewModel()
{
    //state
    private val _state = MutableStateFlow(HistoryState(questionnaire = Questionnaire.PSS,
        timeGranularity = TimeGranularity.Day,
        scores = emptyList()))
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    var data : List<QuestionnaireEntity> = emptyList()

    init {
        viewModelScope.launch {
            fetchData(_state.value.questionnaire)
            _state.value =  HistoryState(questionnaire = _state.value.questionnaire,
                timeGranularity = _state.value.timeGranularity,
                scores = processData(_state.value.timeGranularity))
        }

    }

    fun onQuestionnaireChange(questionnaire: Questionnaire)
    {
        viewModelScope.launch {
            fetchData(questionnaire)
            _state.value = HistoryState(questionnaire = questionnaire,
                timeGranularity = _state.value.timeGranularity,
                scores = processData(_state.value.timeGranularity))
        }
    }

    fun onTimeGranularityChange(timeGranularity: TimeGranularity)
    {
        viewModelScope.launch {
            _state.value = HistoryState(questionnaire = _state.value.questionnaire,
                timeGranularity = timeGranularity,
                scores = processData(timeGranularity))
        }
    }

    private suspend fun fetchData(questionnaire: Questionnaire)
    {
        data = when(questionnaire) {
            Questionnaire.PSS -> pssRepository.getAll()
            Questionnaire.PHQ -> phqRepository.getAll()
            Questionnaire.UCLA -> uclaRepository.getAll()
        }
    }

    private fun processData(timeGranularity: TimeGranularity): List<Int>
    {
        return when(timeGranularity)
        {
            TimeGranularity.Day -> aggregateEntriesPerDay(this.data).map { pair -> pair.second}
            TimeGranularity.Week -> aggregateEntriesPerWeek(this.data).map { pair -> pair.second}
            TimeGranularity.Month -> aggregateEntriesPerMonth(this.data).map { pair -> pair.second}
        }
    }
}