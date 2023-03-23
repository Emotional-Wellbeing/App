package es.upm.bienestaremocional.app.ui.screens.history

import android.graphics.Typeface
import android.util.Range
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.patrykandpatrick.vico.compose.axis.axisGuidelineComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.compose.legend.verticalLegendItem
import com.patrykandpatrick.vico.compose.m3.style.m3ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.questionnaire.*
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.utils.formatDate
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import java.lang.Float.max
import java.time.LocalDate

/**
 * Plots graphics about user's history
 */
@Destination
@Composable
fun HistoryScreen(navigator: DestinationsNavigator,
                  viewModel: HistoryViewModel = hiltViewModel()
)
{
    // State
    val state by viewModel.state.collectAsStateWithLifecycle()

    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.HistoryScreen,
        label = BottomBarDestination.HistoryScreen.label)
    {
        Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
        ) {

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                DisplayQuestionnaireMenu(
                    modifier = Modifier.weight(1.5f,true),
                    onChange = {questionnaire -> viewModel.onQuestionnaireChange(questionnaire)}
                )
                DisplayGranularityOfDataMenu(
                    modifier = Modifier.weight(1f,true),
                    onChange = {timeGranularity -> viewModel.onTimeGranularityChange(timeGranularity)}
                )
            }

            DisplayCalendarPicker(state.timeRange) { range -> viewModel.onTimeRangeChange(range) }

            if(state.scores.isNotEmpty())
            {
                DrawLineChart(state.scores, state.questionnaire, state.timeGranularity)
            }
        }
    }
}

@Composable
private fun DisplayQuestionnaireMenu(modifier: Modifier = Modifier,
                                     onChange: (Questionnaire) -> Unit)
{
    DisplayMenu(
        label = stringResource(id = R.string.questionnaire),
        options = Questionnaire.get().map { Pair(stringResource(it.labelRes)) { onChange(it) } },
        modifier = modifier)
}

@Composable
private fun DisplayGranularityOfDataMenu(modifier: Modifier = Modifier,
                                         onChange: (TimeGranularity) -> Unit)
{
    DisplayMenu(
        label = stringResource(id = R.string.granularity),
        options = TimeGranularity.get().map { Pair(stringResource(it.label)) { onChange(it) } },
        modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisplayMenu(label: String,
                        options: List<Pair<String,() -> Unit>>,
                        modifier: Modifier
)
{
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(options[0].first) }

    // Point of entry of the menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        //Visible at every moment
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selected,
            onValueChange = {}, //empty due to being readOnly
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.first) },
                    onClick = {
                        selected = selectionOption.first
                        expanded = false
                        selectionOption.second()
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisplayCalendarPicker(selectedRange : Range<LocalDate>,
                                  onSelectedRange : (Range<LocalDate>) -> Unit
)
{
    var showDialog by remember { mutableStateOf(false) }


    TextField(
        value = "${formatDate(selectedRange.lower)} - ${formatDate(selectedRange.upper)}",
        modifier = Modifier.clickable { showDialog = true },
        onValueChange = {},
        //readOnly = true,
        enabled = false,
        label = { Text(stringResource(R.string.time_interval)) },
        trailingIcon = { Icon(Icons.Default.DateRange,"") },
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledIndicatorColor =  MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor =  MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor =  MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor =  MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor =  MaterialTheme.colorScheme.onSurface,
            disabledSupportingTextColor =  MaterialTheme.colorScheme.onSurfaceVariant,
        )
    )

    if (showDialog)
    {
        CalendarDialog(
            state = rememberUseCaseState(visible = true,
                embedded = true,
                onCloseRequest = { showDialog = false },
                onFinishedRequest = { showDialog = false },
                onDismissRequest = { showDialog = false },
            ),
            config = CalendarConfig(
                yearSelection = true,
                monthSelection = true,
                style = CalendarStyle.MONTH,
            ),
            selection = CalendarSelection.Period(
                selectedRange = selectedRange
            ) { startDate, endDate ->
                onSelectedRange(Range(startDate, endDate))
            },
        )
    }
}

@Composable
private fun DrawLineChart(data : List<Int>,
                          questionnaire: Questionnaire,
                          timeGranularity: TimeGranularity
)
{
    val chartStyle = m3ChartStyle()

    val decorations = questionnaire.levels.map { rememberThresholdLine(it) }

    val legends = questionnaire.levels.map { verticalLegendItem(
        icon = shapeComponent(Shapes.pillShape, it.levelLabel.color),
        label = textComponent(
            color = chartStyle.axis.axisLabelColor,
            textSize = 12.sp,
            typeface = Typeface.MONOSPACE,
        ),
        labelText = stringResource(it.levelLabel.label))
    }

    ProvideChartStyle(chartStyle)
    {
        val defaultLines = currentChartStyle.lineChart.lines
        Chart(
            chart = lineChart(
                lines = remember(defaultLines) {
                    defaultLines.map { defaultLine ->
                        LineChart.LineSpec(
                            defaultLine.lineColor,
                            defaultLine.lineThicknessDp
                        )
                    }
                },
                axisValuesOverrider = AxisValuesOverrider.fixed(null,null,questionnaire.minScore.toFloat(),questionnaire.maxScore.toFloat()) ,
                decorations = decorations
            ),
            model = entryModelOf(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat()) }),
            startAxis = startAxis(
                guideline = axisGuidelineComponent(),
                maxLabelCount = (questionnaire.maxScore - questionnaire.minScore + 1),
                titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                title = stringResource(questionnaire.labelRes)),
            bottomAxis = bottomAxis(
                guideline = axisGuidelineComponent(),
                titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                title = stringResource(timeGranularity.label)),
            legend = verticalLegend(
                items = legends,
                iconSize = 8.dp,
                iconPadding = 10.dp,
                spacing = 4.dp,
                padding = dimensionsOf(8.dp),
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun rememberThresholdLine(scoreLevel: ScoreLevel): ThresholdLine {
    /*val label = textComponent(
        color = Color.Black,
        background = shapeComponent(Shapes.rectShape, color),
        padding = dimensionsOf(8.dp,2.dp),
        margins = dimensionsOf(4.dp),
        typeface = Typeface.MONOSPACE)*/
    val label = textComponent(textSize = (0).sp)
    val line = shapeComponent(color = scoreLevel.levelLabel.color.copy(.05f))
    val thresholdLabel = stringResource(scoreLevel.levelLabel.label)
    return remember(scoreLevel) {
        ThresholdLine(
            thresholdRange = max(0f,scoreLevel.min.toFloat()-1)..scoreLevel.max.toFloat(),
            thresholdLabel = thresholdLabel,
            labelComponent = label,
            lineComponent = line,
        )
    }
}


@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable

fun DisplayQuestionnaireMenuPreview()
{
    BienestarEmocionalTheme {
        DisplayQuestionnaireMenu(
            modifier = Modifier,
            onChange = {}
        )
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable

fun DisplayQuestionnaireMenuPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        DisplayQuestionnaireMenu(
            modifier = Modifier,
            onChange = {})
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable

fun DisplayGranularityOfDataMenuMenuPreview()
{
    BienestarEmocionalTheme {
        DisplayGranularityOfDataMenu(
            modifier = Modifier,
            onChange = {})
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable

fun DisplayGranularityOfDataMenuPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        DisplayGranularityOfDataMenu(
            modifier = Modifier,
            onChange = {})
    }
}

/*
@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun PSSChartPreview()
{
    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                processSameDayEntries(generateBunchOfPSSEntries()).map { pair -> pair.second},
                Questionnaire.PSS
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun PSSChartPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(processSameDayEntries(generateBunchOfPSSEntries()).map { pair -> pair.second},
                Questionnaire.PSS)
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun PHQChartPreview()
{
    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                processSameDayEntries(generateBunchOfPHQEntries()).map { pair -> pair.second},
                Questionnaire.PHQ
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun PHQChartPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(processSameDayEntries(generateBunchOfPHQEntries()).map { pair -> pair.second},
                Questionnaire.PHQ)
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun UCLAChartPreview()
{
    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                processSameDayEntries(generateBunchOfUCLAEntries()).map { pair -> pair.second},
                Questionnaire.UCLA
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun UCLAChartPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(processSameDayEntries(generateBunchOfUCLAEntries()).map { pair -> pair.second},
                Questionnaire.UCLA)
        }
    }
}*/