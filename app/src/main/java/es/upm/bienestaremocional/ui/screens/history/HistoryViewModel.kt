package es.upm.bienestaremocional.ui.screens.history

import android.util.Range
import androidx.core.util.toRange
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.ScoredEntity
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyStressRepository
import es.upm.bienestaremocional.destinations.HistoryScreenDestination
import es.upm.bienestaremocional.domain.processing.HybridChartRecord
import es.upm.bienestaremocional.domain.processing.lowerStartDayUpperEndDay
import es.upm.bienestaremocional.domain.processing.processRecordsSimulatingEmpty
import es.upm.bienestaremocional.ui.component.chart.ChartEntryWithTimeAndSimulated
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
    private val dailyStressRepository: DailyStressRepository,
    private val dailyDepressionRepository: DailyDepressionRepository,
    private val dailyLonelinessRepository: DailyLonelinessRepository,
) : ViewModel() {
    private val defaultQuestionnaire =
        HistoryScreenDestination.argsFrom(savedStateHandle).preSelectedQuestionnaire
            ?: DailyScoredQuestionnaire.Stress

    //state
    private val defaultTimeRange = (ZonedDateTime.now().minusDays(7)..ZonedDateTime.now())
        .toRange()
        .lowerStartDayUpperEndDay()

    private val _state = MutableStateFlow(
        HistoryState(
            questionnaire = defaultQuestionnaire,
            timeGranularity = TimeGranularity.Day,
            timeRange = defaultTimeRange,
            isDataNotEmpty = false
        )
    )
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    // Producer used to display the data
    val producer = ChartEntryModelProducer()

    private val cachedData: MutableList<ScoredEntity> = mutableListOf()

    init {
        viewModelScope.launch {
            //cached data is empty at the start
            _state.value.apply {
                updateData(
                    questionnaire = questionnaire,
                    timeRange = timeRange
                )
                updateChart(
                    questionnaire = questionnaire,
                    timeGranularity = timeGranularity,
                    timeRange = timeRange
                )
            }
        }
    }

    fun onQuestionnaireChange(questionnaire: DailyScoredQuestionnaire) {
        updateChart(
            questionnaire = questionnaire,
            timeGranularity = _state.value.timeGranularity,
            timeRange = _state.value.timeRange
        )
    }

    fun onTimeGranularityChange(timeGranularity: TimeGranularity) {
        updateChart(
            questionnaire = _state.value.questionnaire,
            timeGranularity = timeGranularity,
            timeRange = _state.value.timeRange
        )
    }

    fun onTimeRangeChange(timeRange: Range<ZonedDateTime>) {
        updateChart(
            questionnaire = _state.value.questionnaire,
            timeGranularity = _state.value.timeGranularity,
            timeRange = timeRange
        )
    }

    private suspend fun updateData(
        questionnaire: DailyScoredQuestionnaire,
        timeRange: Range<ZonedDateTime>
    ) {
        cachedData.clear()
        cachedData.addAll(
            when (questionnaire) {
                DailyScoredQuestionnaire.Stress -> dailyStressRepository.getAllFromRange(
                    range = timeRange,
                    onlyCompleted = false
                )

                DailyScoredQuestionnaire.Depression -> dailyDepressionRepository.getAllFromRange(
                    range = timeRange,
                    onlyCompleted = false
                )

                DailyScoredQuestionnaire.Loneliness -> dailyLonelinessRepository.getAllFromRange(
                    range = timeRange,
                    onlyCompleted = false
                )
            }
        )
    }

    private fun computeAggregateData(
        timeGranularity: TimeGranularity,
        timeRange: Range<ZonedDateTime>
    ): List<HybridChartRecord> {
        return processRecordsSimulatingEmpty(
            records = cachedData,
            dateRange = timeRange,
            timeGranularity = timeGranularity
        )
    }

    private fun updateProducer(aggregateData: List<HybridChartRecord>) {
        if (aggregateData.isEmpty())
            producer.setEntries(emptyList<ChartEntryWithTimeAndSimulated>())
        else
            producer.setEntries(
                aggregateData.mapIndexed { index, value ->
                    ChartEntryWithTimeAndSimulated(
                        value.day,
                        value.simulated,
                        index.toFloat(),
                        value.score
                    )
                }
            )
    }

    private fun updateChart(
        questionnaire: DailyScoredQuestionnaire,
        timeGranularity: TimeGranularity,
        timeRange: Range<ZonedDateTime>
    ) {
        val dataMustBeUpdated =
            questionnaire != _state.value.questionnaire || timeRange != _state.value.timeRange

        if (dataMustBeUpdated) {
            viewModelScope.launch {
                updateData(questionnaire = questionnaire, timeRange = timeRange)

                val aggregateData = computeAggregateData(
                    timeGranularity = timeGranularity,
                    timeRange = timeRange
                )
                updateProducer(aggregateData)

                _state.value = HistoryState(
                    questionnaire = questionnaire,
                    timeGranularity = timeGranularity,
                    timeRange = timeRange,
                    isDataNotEmpty = aggregateData.isNotEmpty()
                )
            }
        }
        else {
            val aggregateData = computeAggregateData(
                timeGranularity = timeGranularity,
                timeRange = timeRange
            )
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