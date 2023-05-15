package es.upm.bienestaremocional.app.ui.screens.history

import android.graphics.Typeface
import android.util.Range
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.toRange
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
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.questionnaire.*
import es.upm.bienestaremocional.app.data.questionnaire.Level.Companion.getColor
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.utils.TimeGranularity
import es.upm.bienestaremocional.app.utils.formatDate
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.rememberMarker
import es.upm.bienestaremocional.core.ui.responsive.computeWindowHeightSize
import es.upm.bienestaremocional.core.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import java.time.LocalDate
import kotlin.random.Random

/**
 * Plots graphics about user's history
 */
@Destination
@Composable
fun HistoryScreen(navigator: DestinationsNavigator,
                  preSelectedQuestionnaire: Questionnaire?,
                  viewModel: HistoryViewModel = hiltViewModel()
)
{
    val widthSize = computeWindowWidthSize()
    val heightSize = computeWindowHeightSize()

    // State
    val state by viewModel.state.collectAsStateWithLifecycle()

    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.HistoryScreen,
        label = BottomBarDestination.HistoryScreen.label)
    {
        Column(modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
        ) {

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                DisplayQuestionnaireMenu(
                    modifier = Modifier.weight(1f,true),
                    selected = state.questionnaire,
                    onChange = {questionnaire -> viewModel.onQuestionnaireChange(questionnaire)}
                )
                DisplayGranularityOfDataMenu(
                    modifier = Modifier.weight(1f,true),
                    selected = state.timeGranularity,
                    onChange = {timeGranularity -> viewModel.onTimeGranularityChange(timeGranularity)}
                )
                // If we have enough width space, show all options inside same row
                if (widthSize > WindowWidthSizeClass.Compact)
                    DisplayCalendarPicker(state.timeRange) { range -> viewModel.onTimeRangeChange(range) }
            }
            // If we didn't had width space, show calendar picker in other row
            if (widthSize <= WindowWidthSizeClass.Compact)
                DisplayCalendarPicker(state.timeRange) { range -> viewModel.onTimeRangeChange(range) }

            if(state.isDataNotEmpty)
                DrawLineChart(heightSize = heightSize,
                    producer = viewModel.producer,
                    questionnaire = state.questionnaire,
                    timeGranularity = state.timeGranularity)
            else
                Text(text = stringResource(id = R.string.no_data_to_display))
        }
    }
}

/**
 * Display questionnaire menu
 * @param selected [Questionnaire] that should be displayed as selected one
 * @param onChange Callback to execute when user changes option
 * @param modifier Optional modifier to override visual aspects
 * @see DisplayMenu
 */
@Composable
private fun DisplayQuestionnaireMenu(selected: Questionnaire,
                                     onChange: (Questionnaire) -> Unit,
                                     modifier: Modifier = Modifier
)
{
    DisplayMenu(
        label = stringResource(id = R.string.measure),
        options = Questionnaire.get().map { Pair(stringResource(it.measureRes)) { onChange(it) } },
        selected = stringResource(id = selected.measureRes),
        modifier = modifier)
}

/**
 * Display granularity menu
 * @param selected [TimeGranularity] that should be displayed as selected one
 * @param onChange Callback to execute when user changes option
 * @param modifier Optional modifier to override visual aspects
 * @see DisplayMenu
 */
@Composable
private fun DisplayGranularityOfDataMenu(selected: TimeGranularity,
                                         onChange: (TimeGranularity) -> Unit,
                                         modifier: Modifier = Modifier)
{
    DisplayMenu(
        label = stringResource(id = R.string.granularity),
        options = TimeGranularity.get().map { Pair(stringResource(it.label)) { onChange(it) } },
        selected = stringResource(id = selected.label),
        modifier = modifier)
}

/**
 * Display a [ExposedDropdownMenuBox] to select an option from list
 * @param label Label of the [TextField] present as entry of the dropdown menu
 * @param options List of options available, that consist an string to show to user and function
 * to execute as callback
 * @param selected String that is selected by the user or default
 * @param modifier Optional modifier to override visual aspects
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisplayMenu(label: String,
                        options: List<Pair<String,() -> Unit>>,
                        selected : String,
                        modifier: Modifier = Modifier
)
{
    var expanded by remember { mutableStateOf(false) }

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
                        expanded = false
                        selectionOption.second()
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

/**
 * Display Material Design 3 calendar picker to user using Sheets library (official
 * Material Design 3 doesn't have it implemented yet)
 * @param selectedRange [Range] of [LocalDate] that user has selected before or by default
 * @param onSelectedRange: Callback to react changes on selected range
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisplayCalendarPicker(selectedRange : Range<LocalDate>,
                                  onSelectedRange : (Range<LocalDate>) -> Unit
)
{
    var showDialog by remember { mutableStateOf(false) }

    // Due to this field is not editable (only show information about the value of picker) is
    // disabled. Nevertheless, being these textfield disabled, these colors are changed by default,
    // so we override them to present an homogeneous style across all components.
    TextField(
        value = "${formatDate(selectedRange.lower)} - ${formatDate(selectedRange.upper)}",
        modifier = Modifier.clickable { showDialog = true },
        onValueChange = {},
        enabled = false,
        label = { Text(stringResource(R.string.time_interval)) },
        trailingIcon = { Icon(Icons.Default.DateRange,"") },
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
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

/**
 * Draws a line chart
 * @param heightSize Depending of the height of the screen, present more or less components
 * @param producer Source of the data
 * @param questionnaire [Questionnaire] whose data is being displayed
 * @param timeGranularity [TimeGranularity] that is used in the chart
 */
@Composable
private fun DrawLineChart(heightSize: WindowHeightSizeClass,
                          producer: ChartEntryModelProducer,
                          questionnaire: Questionnaire,
                          timeGranularity: TimeGranularity
)
{
    val chartStyle = m3ChartStyle()

    val decorations = questionnaire.levels.mapIndexed { index, scoreLevel ->
        val previousMax = if(index == 0)
            questionnaire.minScore
        else
            questionnaire.levels[index-1].max
        thresholdArea(scoreLevel = scoreLevel, previousMax = previousMax.toFloat())
    }

    // Legends are disabled in devices that are small in height space in order to avoid crashes
    // related to not having space enough to display chart after legend has been drawn.
    // Vico draws before legends and using WindowSize is needed to know if screen can plot
    // simultaneously chart and legend; avoiding hardcoded conditionals based on test and error
    
    val legends = if(heightSize > WindowHeightSizeClass.Compact)
        {
            questionnaire.levels.map { verticalLegendItem(
                icon = shapeComponent(Shapes.pillShape, it.level.getColor()),
                label = textComponent(
                    color = chartStyle.axis.axisLabelColor,
                    textSize = 12.sp,
                    typeface = Typeface.MONOSPACE,
                ),
                labelText = stringResource(it.level.label))
            }
        }
        else
            null

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
            model = producer.getModel(),
            startAxis = startAxis(
                guideline = axisGuidelineComponent(),
                maxLabelCount = (questionnaire.maxScore - questionnaire.minScore + 1),
                titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                title = stringResource(questionnaire.measureRes)),
            bottomAxis = bottomAxis(
                guideline = axisGuidelineComponent(),
                titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                title = stringResource(timeGranularity.label)),
            marker = rememberMarker(),
            legend = legends?.let {
                verticalLegend(
                    items = it,
                    iconSize = 8.dp,
                    iconPadding = 10.dp,
                    spacing = 4.dp,
                    padding = dimensionsOf(8.dp),
                )
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * Draws a threshold area
 * @param scoreLevel Used to obtain color and label
 * @param previousMax lower bound of the area
 */
@Composable
private fun thresholdArea(scoreLevel: ScoreLevel,
                          previousMax: Float): ThresholdLine
{
    val label = textComponent(textSize = (0).sp)
    val line = shapeComponent(color = scoreLevel.level.getColor().copy(.5f))
    val thresholdLabel = stringResource(scoreLevel.level.label)
    return remember(scoreLevel) {
        ThresholdLine(
            thresholdRange = previousMax..scoreLevel.max.toFloat(),
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
            selected = Questionnaire.PSS,
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
            selected = Questionnaire.PSS,
            onChange = {})
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable

fun DisplayGranularityOfDataMenuPreview()
{
    BienestarEmocionalTheme {
        DisplayGranularityOfDataMenu(
            modifier = Modifier,
            selected = TimeGranularity.Day,
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
            selected = TimeGranularity.Day,
            onChange = {})
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable

fun DisplayCalendarPickerPreview()
{
    BienestarEmocionalTheme {
        DisplayCalendarPicker(
            selectedRange = (LocalDate.now().minusDays(7) .. LocalDate.now()).toRange(),
            onSelectedRange = {})
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable

fun DisplayCalendarPickerPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        DisplayCalendarPicker(
            selectedRange = (LocalDate.now().minusDays(7) .. LocalDate.now()).toRange(),
            onSelectedRange = {})
    }
}


@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun PSSChartCompactPreview()
{
    val data = List(100) { Random.nextInt(Questionnaire.PSS.minScore, Questionnaire.PSS.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Compact,
                producer = producer,
                questionnaire = Questionnaire.PSS,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun PSSChartCompactPreviewDarkTheme()
{
    val data = List(100) { Random.nextInt(Questionnaire.PSS.minScore, Questionnaire.PSS.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Compact,
                producer = producer,
                questionnaire = Questionnaire.PSS,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}


@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun PSSChartMediumPreview()
{
    val data = List(100) { Random.nextInt(Questionnaire.PSS.minScore, Questionnaire.PSS.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Medium,
                producer = producer,
                questionnaire = Questionnaire.PSS,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun PSSChartMediumPreviewDarkTheme()
{
    val data = List(100) { Random.nextInt(Questionnaire.PSS.minScore, Questionnaire.PSS.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Medium,
                producer = producer,
                questionnaire = Questionnaire.PSS,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun PHQChartCompactPreview()
{
    val data = List(100) { Random.nextInt(Questionnaire.PHQ.minScore, Questionnaire.PHQ.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Compact,
                producer = producer,
                questionnaire = Questionnaire.PHQ,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun PHQChartPreviewCompactDarkTheme()
{
    val data = List(100) { Random.nextInt(Questionnaire.PHQ.minScore, Questionnaire.PHQ.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Compact,
                producer = producer,
                questionnaire = Questionnaire.PHQ,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun PHQChartMediumPreview()
{
    val data = List(100) { Random.nextInt(Questionnaire.PHQ.minScore, Questionnaire.PHQ.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Medium,
                producer = producer,
                questionnaire = Questionnaire.PHQ,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun PHQChartMediumPreviewDarkTheme()
{
    val data = List(100) { Random.nextInt(Questionnaire.PHQ.minScore, Questionnaire.PHQ.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Medium,
                producer = producer,
                questionnaire = Questionnaire.PHQ,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun UCLAChartCompactPreview()
{
    val data = List(100) { Random.nextInt(Questionnaire.UCLA.minScore, Questionnaire.UCLA.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Compact,
                producer = producer,
                questionnaire = Questionnaire.UCLA,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun UCLAChartCompactPreviewDarkTheme()
{
    val data = List(100) { Random.nextInt(Questionnaire.UCLA.minScore, Questionnaire.UCLA.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Compact,
                producer = producer,
                questionnaire = Questionnaire.UCLA,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun UCLAChartMediumPreview()
{
    val data = List(100) { Random.nextInt(Questionnaire.UCLA.minScore, Questionnaire.UCLA.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Medium,
                producer = producer,
                questionnaire = Questionnaire.UCLA,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun UCLAChartMediumPreviewDarkTheme()
{
    val data = List(100) { Random.nextInt(Questionnaire.UCLA.minScore, Questionnaire.UCLA.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Medium,
                producer = producer,
                questionnaire = Questionnaire.UCLA,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}