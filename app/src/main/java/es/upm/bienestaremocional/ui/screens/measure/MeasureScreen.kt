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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.patrykandpatrick.vico.compose.axis.axisGuidelineComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.m3.style.m3ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.domain.processing.NullableChartRecord
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.component.ChartEntryWithTime
import es.upm.bienestaremocional.ui.component.DoubleMeasureStatus
import es.upm.bienestaremocional.ui.component.chart.rememberMarker
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.ui.screens.destinations.HistoryScreenDestination
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import java.time.DayOfWeek
import java.time.ZonedDateTime
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.util.Locale

@Destination
@Composable
fun MeasureScreen(navigator : DestinationsNavigator,
                  questionnaire: DailyScoredQuestionnaire,
                  viewModel : MeasureViewModel = hiltViewModel()
)
{
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
)
{
    AppBasicScreen(navigator = navigator,
        entrySelected = null,
        label = questionnaire.measure.measureRes)
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
                        headers = Pair(stringResource(R.string.yesterday), stringResource(R.string.last_seven_days)),
                        questionnaire = questionnaire,
                        height = maxHeight,
                        widthSize = widthSize,
                        showHeadline = false,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        indicatorContainerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                }

            }
            Box(modifier = Modifier
                .weight(0.6f)
                .padding(16.dp))
            {
                if (currentWeekScores.isNotEmpty())
                    ActualWeekChart(questionnaire,currentWeekScores)
                else
                    Text(stringResource(id = R.string.no_data_to_display))
            }
            TextButton(onClick = { navigator.navigate(HistoryScreenDestination(questionnaire)) }) {
                Text(stringResource(R.string.more_details))
            }
        }
    }
}

@Composable
private fun ActualWeekChart(questionnaire: DailyScoredQuestionnaire,
                            data : List<NullableChartRecord>)
{
    val producer = remember { ChartEntryModelProducer() }

    producer.setEntries(
        data.mapIndexed { index, value ->
            ChartEntryWithTime(value.day, index.toFloat(),value.score ?: 0f)
        }
    )

    val valueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { index, chartValues ->
        // Access to the first list of entries (in our case only one chart is plotted)
        // Get actual element and extract day of the week from time
        (chartValues.chartEntryModel.entries.first().getOrNull(index.toInt()) as? ChartEntryWithTime)
            ?.time
            ?.run { this.dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault())  }
            .orEmpty()
    }

    val chartStyle = m3ChartStyle()

    ProvideChartStyle(chartStyle)
    {
        Chart(
            chart = columnChart(
                axisValuesOverrider = AxisValuesOverrider.fixed(null,
                    null,
                    questionnaire.minScore.toFloat(),
                    questionnaire.maxScore.toFloat())
            ),
            model = producer.getModel(),
            startAxis = startAxis(
                guideline = axisGuidelineComponent(),
                titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                title = stringResource(questionnaire.measure.measureRes),
            ),

            bottomAxis = bottomAxis(
                guideline = null,
                valueFormatter = valueFormatter,
                titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                title = stringResource(R.string.actual_week),
            ),
            marker = rememberMarker(),
            modifier = Modifier.fillMaxSize()
        )
    }

}


@Preview(group = "Light Theme")
@Composable
private fun MeasureScreenCompactPreview()
{
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
            score = DailyScoredQuestionnaire.Stress.minScore + (step * (it+1)),
        )
    }

    BienestarEmocionalTheme {
        MeasureScreen(navigator = EmptyDestinationsNavigator,
            questionnaire = DailyScoredQuestionnaire.Stress,
            yesterdayScore = 20,
            lastSevenDaysScore = 40,
            currentWeekScores = currentWeekScores,
            widthSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(group = "Dark Theme")
@Composable
private fun MeasureScreenCompactPreviewDarkTheme()
{
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
            score = DailyScoredQuestionnaire.Stress.minScore + (step * (it+1)),
        )
    }

    BienestarEmocionalTheme(darkTheme = true) {
        MeasureScreen(navigator = EmptyDestinationsNavigator,
            questionnaire = DailyScoredQuestionnaire.Stress,
            yesterdayScore = 20,
            lastSevenDaysScore = 40,
            currentWeekScores = currentWeekScores,
            widthSize = WindowWidthSizeClass.Compact)
    }
}