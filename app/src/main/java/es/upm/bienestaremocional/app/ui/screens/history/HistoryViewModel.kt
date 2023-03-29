package es.upm.bienestaremocional.app.ui.screens.history

import android.util.Range
import androidx.core.util.toRange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.domain.processing.aggregateEntriesPerDay
import es.upm.bienestaremocional.app.domain.processing.aggregateEntriesPerMonth
import es.upm.bienestaremocional.app.domain.processing.aggregateEntriesPerWeek
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.UCLARepository
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    val windowSize: WindowSize,
    private val pssRepository: PSSRepository,
    private val phqRepository: PHQRepository,
    private val uclaRepository: UCLARepository,
) : ViewModel()
{
    //state
    private val _state = MutableStateFlow(
        HistoryState(questionnaire = Questionnaire.PSS,
            timeGranularity = TimeGranularity.Day,
            timeRange = (LocalDate.now().minusDays(7) .. LocalDate.now()).toRange(),
            scores = emptyList()))
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value =  HistoryState(questionnaire = _state.value.questionnaire,
                timeGranularity = _state.value.timeGranularity,
                timeRange = _state.value.timeRange,
                scores = obtainData(_state.value.questionnaire,
                    _state.value.timeGranularity,
                    _state.value.timeRange)
            )
        }

    }

    fun onQuestionnaireChange(questionnaire: Questionnaire)
    {
        viewModelScope.launch {
            _state.value =  HistoryState(questionnaire = questionnaire,
                timeGranularity = _state.value.timeGranularity,
                timeRange = _state.value.timeRange,
                scores = obtainData(questionnaire,
                    _state.value.timeGranularity,
                    _state.value.timeRange)
            )
        }
    }

    fun onTimeGranularityChange(timeGranularity: TimeGranularity)
    {
        viewModelScope.launch {
            _state.value =  HistoryState(questionnaire = _state.value.questionnaire,
                timeGranularity = timeGranularity,
                timeRange = _state.value.timeRange,
                scores = obtainData(_state.value.questionnaire,
                    timeGranularity,
                    _state.value.timeRange)
            )
        }
    }

    fun onTimeRangeChange(timeRange: Range<LocalDate>)
    {
        viewModelScope.launch {
            _state.value =  HistoryState(questionnaire = _state.value.questionnaire,
                timeGranularity = _state.value.timeGranularity,
                timeRange = timeRange,
                scores = obtainData(_state.value.questionnaire,
                    _state.value.timeGranularity,
                    timeRange)
            )
        }
    }
    private suspend fun obtainData(questionnaire: Questionnaire,
                                   timeGranularity: TimeGranularity,
                                   range: Range<LocalDate>): List<Int>
    {
        val data = when(questionnaire) {
            Questionnaire.PSS -> pssRepository.getAllFromRange(range)
            Questionnaire.PHQ -> phqRepository.getAllFromRange(range)
            Questionnaire.UCLA -> uclaRepository.getAllFromRange(range)
        }
        return when(timeGranularity)
        {
            TimeGranularity.Day -> aggregateEntriesPerDay(data).map { pair -> pair.second}
            TimeGranularity.Week -> aggregateEntriesPerWeek(data).map { pair -> pair.second}
            TimeGranularity.Month -> aggregateEntriesPerMonth(data).map { pair -> pair.second}
        }
    }
}