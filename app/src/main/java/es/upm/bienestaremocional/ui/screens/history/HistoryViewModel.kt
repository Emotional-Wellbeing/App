package es.upm.bienestaremocional.ui.screens.history

import android.util.Range
import androidx.core.util.toRange
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.QuestionnaireEntity
import es.upm.bienestaremocional.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.domain.processing.processRecords
import es.upm.bienestaremocional.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.UCLARepository
import es.upm.bienestaremocional.destinations.HistoryScreenDestination
import es.upm.bienestaremocional.utils.TimeGranularity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val pssRepository: PSSRepository,
    private val phqRepository: PHQRepository,
    private val uclaRepository: UCLARepository,
) : ViewModel()
{
    private val defaultQuestionnaire =
        HistoryScreenDestination.argsFrom(savedStateHandle).preSelectedQuestionnaire
            ?: Questionnaire.PSS

    //state
    private val _state = MutableStateFlow(
        HistoryState(questionnaire = defaultQuestionnaire,
            timeGranularity = TimeGranularity.Day,
            timeRange = (ZonedDateTime.now().minusDays(7) .. ZonedDateTime.now()).toRange(),
            isDataNotEmpty = false)
    )
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

    fun onTimeRangeChange(timeRange: Range<ZonedDateTime>)
    {
        updateChart(questionnaire = _state.value.questionnaire,
            timeGranularity = _state.value.timeGranularity,
            timeRange = timeRange)
    }

    private suspend fun updateData(questionnaire: Questionnaire,
                                   timeRange: Range<ZonedDateTime>)
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
            TimeGranularity.Day -> processRecords(cachedData, timeGranularity).map { it.score }
            TimeGranularity.Week -> processRecords(cachedData, timeGranularity).map { it.score }
            TimeGranularity.Month -> processRecords(cachedData, timeGranularity).map { it.score }
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
                            timeRange: Range<ZonedDateTime>)
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