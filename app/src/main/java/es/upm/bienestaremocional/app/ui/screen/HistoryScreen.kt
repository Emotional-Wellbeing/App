package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.m3.style.m3ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.database.entity.PSS
import es.upm.bienestaremocional.app.data.questionnaire.generateBunchOfPSSEntries
import es.upm.bienestaremocional.app.domain.processSameDayEntries
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.ui.viewmodel.HistoryViewModel
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import java.time.LocalDate
import java.time.ZoneId

/**
 * Plots graphics about user's history
 */
@Destination
@Composable
fun HistoryScreen(navigator: DestinationsNavigator,
                  historyViewModel: HistoryViewModel = hiltViewModel()
)
{
    // State
    val pssData = historyViewModel.pssData.observeAsState(emptyList())

    // API call
    LaunchedEffect(key1 = Unit) {
        historyViewModel.fetchPSSData()
    }

    if (pssData.value.isNotEmpty())
        DrawHistoryScreen(navigator = navigator, pssData = pssData.value)
}
@Composable
private fun DrawHistoryScreen(navigator: DestinationsNavigator,
                              pssData : List<PSS>)
{

    val chartStyle = m3ChartStyle()

    val data = processSameDayEntries(pssData)

    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.HistoryScreen,
        label = BottomBarDestination.HistoryScreen.label)
    {
        Column(modifier = Modifier.padding(16.dp)) {

            val pssProducer = ChartEntryModelProducer(
                data.mapIndexed {index, pair ->
                    Entry(
                        pair.first.atZone(ZoneId.systemDefault()).toLocalDate(),
                        index.toFloat(),
                        pair.second.toFloat()
                    )
                }
            )

            val bottomAxisValueFormatter =
                AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, chartValues ->
                    val entry = (chartValues.chartEntryModel.entries.first().getOrNull(value.toInt())) as? Entry
                    entry?.localDate?.let { "${it.dayOfMonth}/${it.monthValue}" } ?: ""
                }

            ProvideChartStyle(chartStyle)
            {
                Chart(
                    chart = lineChart(),
                    chartModelProducer = pssProducer,
                    startAxis = startAxis(
                        titleComponent = textComponent(
                            /*color = Color.Black,
                                    background = shapeComponent(Shapes.pillShape, color1),
                                    padding = axisTitlePadding,
                                    margins = startAxisTitleMargins,
                                    typeface = Typeface.MONOSPACE,*/
                        ),
                        title = stringResource(R.string.pss_label)),
                    bottomAxis = bottomAxis(valueFormatter = bottomAxisValueFormatter),
                )
            }

            ProvideChartStyle(chartStyle)
            {
                Chart(
                    chart = columnChart(),
                    chartModelProducer = pssProducer,
                    startAxis = startAxis(
                        title = stringResource(R.string.pss_label)),
                    bottomAxis = bottomAxis(),
                )
            }
        }
    }
}

private class Entry(
    val localDate: LocalDate,
    override val x: Float,
    override val y: Float,
) : ChartEntry {
    override fun withY(y: Float) = Entry(localDate, x, y)
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun HistoryScreenPreview()
{
    BienestarEmocionalTheme {
        DrawHistoryScreen(EmptyDestinationsNavigator, generateBunchOfPSSEntries())
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun HistoryScreenPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        DrawHistoryScreen(EmptyDestinationsNavigator, generateBunchOfPSSEntries())
    }
}