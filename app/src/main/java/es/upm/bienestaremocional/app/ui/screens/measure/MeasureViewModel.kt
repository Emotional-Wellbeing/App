package es.upm.bienestaremocional.app.ui.screens.measure

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireEntity
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.domain.processing.NullableChartRecord
import es.upm.bienestaremocional.app.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.app.domain.processing.processRecords
import es.upm.bienestaremocional.app.domain.processing.processRecordsMaintainingEmpty
import es.upm.bienestaremocional.app.domain.processing.reduceEntries
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.UCLARepository
import es.upm.bienestaremocional.app.ui.screens.destinations.MeasureScreenDestination
import es.upm.bienestaremocional.app.utils.TimeGranularity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MeasureViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    pssRepository: PSSRepository,
    phqRepository: PHQRepository,
    uclaRepository: UCLARepository,
) : ViewModel()
{

    val questionnaire = MeasureScreenDestination.argsFrom(savedStateHandle).questionnaire

    val repository : QuestionnaireRepository<out QuestionnaireEntity> = when(questionnaire)
    {
        Questionnaire.PSS -> pssRepository
        Questionnaire.PHQ -> phqRepository
        Questionnaire.UCLA -> uclaRepository
    }

    private val _yesterdayScore : MutableStateFlow<Int?> = MutableStateFlow(null)
    val yesterdayScore : StateFlow<Int?> = _yesterdayScore.asStateFlow()
    private val _lastSevenDaysScore : MutableStateFlow<Int?> = MutableStateFlow(null)
    val lastSevenDaysScore : StateFlow<Int?> = _lastSevenDaysScore.asStateFlow()
    private val _currentWeekScores : MutableStateFlow<List<NullableChartRecord>> =
        MutableStateFlow(listOf())
    val currentWeekScores : StateFlow<List<NullableChartRecord>> =
        _currentWeekScores.asStateFlow()

    init
    {
        viewModelScope.launch {
            val yesterdayScores = repository.getAllFromYesterday()
            val lastSevenDayScores = repository.getAllFromLastSevenDays()
            val currentWeekScoresRaw = repository.getAllFromCurrentWeek()

            if (yesterdayScores.any { it.score != null })
            {
               _yesterdayScore.value = processRecords(yesterdayScores, TimeGranularity.Day)[0]
                   .score
                   .toInt()
            }
            if (lastSevenDayScores.any { it.score != null })
            {
                _lastSevenDaysScore.value = reduceEntries(
                    processRecords(lastSevenDayScores, TimeGranularity.Day)
                )?.toInt()
            }

            _currentWeekScores.value = processRecordsMaintainingEmpty(
                currentWeekScoresRaw,
                getCurrentWeek(),
                TimeGranularity.Day
            )

        }
    }
}