package es.upm.bienestaremocional.app.ui.screens.history

import android.util.Range
import androidx.core.util.toRange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireEntity
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.domain.processing.aggregateEntriesPerDay
import es.upm.bienestaremocional.app.domain.processing.aggregateEntriesPerMonth
import es.upm.bienestaremocional.app.domain.processing.aggregateEntriesPerWeek
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.UCLARepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
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
            isDataNotEmpty = false))
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    // Producer used to display the data
    val producer = ChartEntryModelProducer()

    private val cachedData : MutableList<QuestionnaireEntity> = mutableListOf()

    init {
        viewModelScope.launch {
            //cached data is empty at the start
            _state.value.apply {
                updateData(questionnaire = questionnaire, timeRange = timeRange)
                updateChart(
                    questionnaire = questionnaire,
                    timeGranularity = timeGranularity,
                    timeRange = timeRange
                )
            }
        }
    }

    fun onQuestionnaireChange(questionnaire: Questionnaire)
    {
        updateChart(questionnaire = questionnaire,
            timeGranularity = _state.value.timeGranularity,
            timeRange = _state.value.timeRange)
    }

    fun onTimeGranularityChange(timeGranularity: TimeGranularity)
    {
        updateChart(questionnaire = _state.value.questionnaire,
            timeGranularity = timeGranularity,
            timeRange = _state.value.timeRange)
    }

    fun onTimeRangeChange(timeRange: Range<LocalDate>)
    {
        updateChart(questionnaire = _state.value.questionnaire,
            timeGranularity = _state.value.timeGranularity,
            timeRange = timeRange)
    }

    private suspend fun updateData(questionnaire: Questionnaire,
                                   timeRange: Range<LocalDate>)
    {
        cachedData.clear()
        cachedData.addAll(
            when (questionnaire) {
                Questionnaire.PSS -> pssRepository.getAllFromRange(timeRange)
                Questionnaire.PHQ -> phqRepository.getAllFromRange(timeRange)
                Questionnaire.UCLA -> uclaRepository.getAllFromRange(timeRange)
            }
        )
    }

    private fun computeAggregateData(timeGranularity: TimeGranularity): List<Float>
    {
        return when(timeGranularity)
        {
            TimeGranularity.Day -> aggregateEntriesPerDay(cachedData).map { pair -> pair.second}
            TimeGranularity.Week -> aggregateEntriesPerWeek(cachedData).map { pair -> pair.second}
            TimeGranularity.Month -> aggregateEntriesPerMonth(cachedData).map { pair -> pair.second}
        }
    }

    private fun updateProducer(aggregateData : List<Float>)
    {
        if (aggregateData.isEmpty())
        {
            producer.setEntries(emptyList<ChartEntry>())
        }
        else
        {
            producer.setEntries(aggregateData.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value)})
        }
    }

    private fun updateChart(questionnaire: Questionnaire,
                            timeGranularity: TimeGranularity,
                            timeRange: Range<LocalDate>)
    {
        val dataMustBeUpdated = questionnaire != _state.value.questionnaire || timeRange != _state.value.timeRange

        if(dataMustBeUpdated)
        {
            viewModelScope.launch {
                updateData(questionnaire = questionnaire, timeRange = timeRange)

                val aggregateData = computeAggregateData(timeGranularity)
                updateProducer(aggregateData)

                _state.value = HistoryState(
                    questionnaire = questionnaire,
                    timeGranularity = timeGranularity,
                    timeRange = timeRange,
                    isDataNotEmpty = aggregateData.isNotEmpty()
                )
            }
        }
        else
        {
            val aggregateData = computeAggregateData(timeGranularity)
            updateProducer(aggregateData)

            _state.value = HistoryState(
                questionnaire = questionnaire,
                timeGranularity = timeGranularity,
                timeRange = timeRange,
                isDataNotEmpty = aggregateData.isNotEmpty()
            )
        }
    }
}