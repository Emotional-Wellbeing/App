package es.upm.bienestaremocional.ui.component.chart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.domain.processing.NullableChartRecord
import es.upm.bienestaremocional.ui.component.ChartEntryWithTime
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ActualWeekChart(
    questionnaire: DailyScoredQuestionnaire,
    data: List<NullableChartRecord>
) {
    val producer = remember { ChartEntryModelProducer() }

    producer.setEntries(
        data.mapIndexed { index, value ->
            ChartEntryWithTime(value.day, index.toFloat(), value.score ?: 0f)
        }
    )

    val valueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { index, chartValues ->
        // Access to the first list of entries (in our case only one chart is plotted)
        // Get actual element and extract day of the week from time
        (chartValues.chartEntryModel.entries.first()
            .getOrNull(index.toInt()) as? ChartEntryWithTime)
            ?.time
            ?.run { this.dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault()) }
            .orEmpty()
    }

    val chartStyle = m3ChartStyle()

    ProvideChartStyle(chartStyle)
    {
        Chart(
            chart = columnChart(
                axisValuesOverrider = AxisValuesOverrider.fixed(
                    null,
                    null,
                    questionnaire.minScore.toFloat(),
                    questionnaire.maxScore.toFloat()
                )
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
