package es.upm.bienestaremocional.ui.screens.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.domain.processing.NullableChartRecord
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject


@HiltViewModel
class CommunityViewModel @Inject constructor(remoteRepository: RemoteRepository) : ViewModel()
{
    // Stress
    private val _stressYesterdayScore : MutableStateFlow<Int?> = MutableStateFlow(null)
    val stressYesterdayScore : StateFlow<Int?> = _stressYesterdayScore.asStateFlow()
    private val _stressLastSevenDaysScore : MutableStateFlow<Int?> = MutableStateFlow(null)
    val stressLastSevenDaysScore : StateFlow<Int?> = _stressLastSevenDaysScore.asStateFlow()
    private val _stressCurrentWeekScores : MutableStateFlow<List<NullableChartRecord>> =
        MutableStateFlow(listOf())
    val stressCurrentWeekScores : StateFlow<List<NullableChartRecord>> =
        _stressCurrentWeekScores.asStateFlow()

    // Depression
    private val _depressionYesterdayScore : MutableStateFlow<Int?> = MutableStateFlow(null)
    val depressionYesterdayScore : StateFlow<Int?> = _depressionYesterdayScore.asStateFlow()
    private val _depressionLastSevenDaysScore : MutableStateFlow<Int?> = MutableStateFlow(null)
    val depressionLastSevenDaysScore : StateFlow<Int?> = _depressionLastSevenDaysScore.asStateFlow()
    private val _depressionCurrentWeekScores : MutableStateFlow<List<NullableChartRecord>> =
        MutableStateFlow(listOf())
    val depressionCurrentWeekScores : StateFlow<List<NullableChartRecord>> =
        _depressionCurrentWeekScores.asStateFlow()

    // Loneliness
    private val _lonelinessYesterdayScore : MutableStateFlow<Int?> = MutableStateFlow(null)
    val lonelinessYesterdayScore : StateFlow<Int?> = _lonelinessYesterdayScore.asStateFlow()
    private val _lonelinessLastSevenDaysScore : MutableStateFlow<Int?> = MutableStateFlow(null)
    val lonelinessLastSevenDaysScore : StateFlow<Int?> = _lonelinessLastSevenDaysScore.asStateFlow()
    private val _lonelinessCurrentWeekScores : MutableStateFlow<List<NullableChartRecord>> =
        MutableStateFlow(listOf())
    val lonelinessCurrentWeekScores : StateFlow<List<NullableChartRecord>> =
        _lonelinessCurrentWeekScores.asStateFlow()

    init
    {
        viewModelScope.launch {
            val result = remoteRepository.getCommunity()
            val yesterdayScores = result?.data?.yesterday
            val lastSevenDayScores = result?.data?.lastSevenDays
            val currentWeekScoresRaw = result?.data?.currentWeek

            yesterdayScores?.let {
                _stressYesterdayScore.value = it.stress?.toInt()
                _depressionYesterdayScore.value = it.depression?.toInt()
                _lonelinessYesterdayScore.value = it.loneliness?.toInt()
            }

            lastSevenDayScores?.let {
                _stressLastSevenDaysScore.value = it.stress?.toInt()
                _depressionLastSevenDaysScore.value = it.depression?.toInt()
                _lonelinessLastSevenDaysScore.value = it.loneliness?.toInt()
            }

            currentWeekScoresRaw?.let {
                val firstDay = ZonedDateTime
                    .now()
                    .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                    .truncatedTo(ChronoUnit.DAYS)

                val stressBuffer = mutableListOf<NullableChartRecord>()
                val depressionBuffer = mutableListOf<NullableChartRecord>()
                val lonelinessBuffer = mutableListOf<NullableChartRecord>()

                for (index in 0..6)
                {
                    val day = firstDay.plusDays(index.toLong())

                    stressBuffer.add(
                        NullableChartRecord(
                            day = day,
                            score = it.getOrNull(index)?.stress?.toFloat()
                        )
                    )

                    depressionBuffer.add(
                        NullableChartRecord(
                            day = day,
                            score = it.getOrNull(index)?.depression?.toFloat()
                        )
                    )

                    lonelinessBuffer.add(
                        NullableChartRecord(
                            day = day,
                            score = it.getOrNull(index)?.loneliness?.toFloat()
                        )
                    )
                }

                _stressCurrentWeekScores.value = stressBuffer
                _depressionCurrentWeekScores.value = depressionBuffer
                _lonelinessCurrentWeekScores.value = lonelinessBuffer
            }
        }
    }

}