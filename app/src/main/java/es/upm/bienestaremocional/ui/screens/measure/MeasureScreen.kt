package es.upm.bienestaremocional.ui.screens.measure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.domain.processing.NullableChartRecord
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.component.DoubleMeasureStatus
import es.upm.bienestaremocional.ui.component.chart.ActualWeekChart
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.ui.screens.destinations.HistoryScreenDestination
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import java.time.DayOfWeek
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

@Destination
@Composable
fun MeasureScreen(
    navigator: DestinationsNavigator,
    questionnaire: DailyScoredQuestionnaire,
    viewModel: MeasureViewModel = hiltViewModel()
) {
    val yesterdayScore by viewModel.yesterdayScore.collectAsStateWithLifecycle()
    val lastSevenDaysScore by viewModel.lastSevenDaysScore.collectAsStateWithLifecycle()
    val currentWeekScores by viewModel.currentWeekScores.collectAsStateWithLifecycle()


    MeasureScreen(
        navigator = navigator,
        questionnaire = questionnaire,
        yesterdayScore = yesterdayScore,
        lastSevenDaysScore = lastSevenDaysScore,
        currentWeekScores = currentWeekScores,
        widthSize = computeWindowWidthSize()
    )
}

@Composable
private fun MeasureScreen(
    navigator: DestinationsNavigator,
    questionnaire: DailyScoredQuestionnaire,
    yesterdayScore: Int?,
    lastSevenDaysScore: Int?,
    currentWeekScores: List<NullableChartRecord>,
    widthSize: WindowWidthSizeClass
) {
    AppBasicScreen(
        navigator = navigator,
        entrySelected = null,
        label = questionnaire.measure.measureRes
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        )
        {
            Box(modifier = Modifier.weight(0.4f))
            {
                BoxWithConstraints()
                {
                    DoubleMeasureStatus(
                        data = Pair(yesterdayScore, lastSevenDaysScore),
                        headers = Pair(
                            stringResource(R.string.yesterday),
                            stringResource(R.string.last_seven_days)
                        ),
                        questionnaire = questionnaire,
                        height = maxHeight,
                        widthSize = widthSize,
                        showHeadline = false,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        indicatorContainerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                }

            }
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(16.dp)
            )
            {
                if (currentWeekScores.isNotEmpty())
                    ActualWeekChart(questionnaire, currentWeekScores)
                else
                    Text(stringResource(id = R.string.no_data_to_display))
            }
            TextButton(onClick = { navigator.navigate(HistoryScreenDestination(questionnaire)) }) {
                Text(stringResource(R.string.more_details))
            }
        }
    }
}

@Preview(group = "Light Theme")
@Composable
private fun MeasureScreenCompactPreview() {
    val days = 7
    val step = DailyScoredQuestionnaire.Stress.run { maxScore - minScore } / days.toFloat()

    val monday = ZonedDateTime
        .now()
        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        .truncatedTo(ChronoUnit.DAYS)

    val currentWeekScores: List<NullableChartRecord> = List(days)
    {
        NullableChartRecord(
            day = monday.plusDays(it.toLong()),
            score = DailyScoredQuestionnaire.Stress.minScore + (step * (it + 1)),
        )
    }

    BienestarEmocionalTheme {
        MeasureScreen(
            navigator = EmptyDestinationsNavigator,
            questionnaire = DailyScoredQuestionnaire.Stress,
            yesterdayScore = 20,
            lastSevenDaysScore = 40,
            currentWeekScores = currentWeekScores,
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(group = "Dark Theme")
@Composable
private fun MeasureScreenCompactPreviewDarkTheme() {
    val days = 7
    val step = DailyScoredQuestionnaire.Stress.run { maxScore - minScore } / days.toFloat()

    val monday = ZonedDateTime
        .now()
        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        .truncatedTo(ChronoUnit.DAYS)

    val currentWeekScores: List<NullableChartRecord> = List(days)
    {
        NullableChartRecord(
            day = monday.plusDays(it.toLong()),
            score = DailyScoredQuestionnaire.Stress.minScore + (step * (it + 1)),
        )
    }

    BienestarEmocionalTheme(darkTheme = true) {
        MeasureScreen(
            navigator = EmptyDestinationsNavigator,
            questionnaire = DailyScoredQuestionnaire.Stress,
            yesterdayScore = 20,
            lastSevenDaysScore = 40,
            currentWeekScores = currentWeekScores,
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}