package es.upm.bienestaremocional.ui.screens.measure

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.ScoredEntity
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.domain.processing.NullableChartRecord
import es.upm.bienestaremocional.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.domain.processing.processRecords
import es.upm.bienestaremocional.domain.processing.processRecordsMaintainingEmpty
import es.upm.bienestaremocional.domain.processing.reduceEntries
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyStressRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository
import es.upm.bienestaremocional.destinations.MeasureScreenDestination
import es.upm.bienestaremocional.utils.TimeGranularity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MeasureViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dailyStressRepository: DailyStressRepository,
    dailyDepressionRepository: DailyDepressionRepository,
    dailyLonelinessRepository: DailyLonelinessRepository
) : ViewModel() {

    val questionnaire = MeasureScreenDestination.argsFrom(savedStateHandle).questionnaire

    val repository: QuestionnaireRepository<out ScoredEntity> = when (questionnaire) {
        DailyScoredQuestionnaire.Stress -> dailyStressRepository
        DailyScoredQuestionnaire.Depression -> dailyDepressionRepository
        DailyScoredQuestionnaire.Loneliness -> dailyLonelinessRepository
    }

    private val _yesterdayScore: MutableStateFlow<Int?> = MutableStateFlow(null)
    val yesterdayScore: StateFlow<Int?> = _yesterdayScore.asStateFlow()
    private val _lastSevenDaysScore: MutableStateFlow<Int?> = MutableStateFlow(null)
    val lastSevenDaysScore: StateFlow<Int?> = _lastSevenDaysScore.asStateFlow()
    private val _currentWeekScores: MutableStateFlow<List<NullableChartRecord>> =
        MutableStateFlow(listOf())
    val currentWeekScores: StateFlow<List<NullableChartRecord>> =
        _currentWeekScores.asStateFlow()

    init {
        viewModelScope.launch {
            val yesterdayScores = repository.getAllFromYesterday()
            val lastSevenDayScores = repository.getAllFromLastSevenDays()
            val currentWeekScoresRaw = repository.getAllFromCurrentWeek()

            if (yesterdayScores.any { it.score != null }) {
                _yesterdayScore.value = processRecords(yesterdayScores, TimeGranularity.Day)[0]
                    .score
                    .toInt()
            }
            if (lastSevenDayScores.any { it.score != null }) {
                _lastSevenDaysScore.value = reduceEntries(
                    processRecords(lastSevenDayScores, TimeGranularity.Day)
                )?.toInt()
            }

            _currentWeekScores.value = processRecordsMaintainingEmpty(
                records = currentWeekScoresRaw,
                dateRange = getCurrentWeek(),
                timeGranularity = TimeGranularity.Day
            )

        }
    }
}