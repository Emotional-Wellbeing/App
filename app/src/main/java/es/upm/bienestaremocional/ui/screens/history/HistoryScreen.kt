package es.upm.bienestaremocional.ui.screens.history

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
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.marker.Marker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.*
import es.upm.bienestaremocional.data.questionnaire.Level.Companion.getColor
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.domain.processing.milliSecondToZonedDateTime
import es.upm.bienestaremocional.domain.processing.toEpochMilliSecond
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.component.chart.SimpleMarkerComponent
import es.upm.bienestaremocional.ui.component.chart.rememberMarker
import es.upm.bienestaremocional.ui.component.chart.rememberSimpleMarker
import es.upm.bienestaremocional.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.ui.responsive.computeWindowHeightSize
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.TimeGranularity
import es.upm.bienestaremocional.utils.formatDate
import java.time.ZonedDateTime
import kotlin.random.Random

/**
 * Plots graphics about user's history
 */
@Destination
@Composable
fun HistoryScreen(navigator: DestinationsNavigator,
                  preSelectedQuestionnaire: DailyScoredQuestionnaire?,
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
                    DisplayDatePicker(state.timeRange) { range -> viewModel.onTimeRangeChange(range) }
            }
            // If we didn't had width space, show calendar picker in other row
            if (widthSize <= WindowWidthSizeClass.Compact)
                DisplayDatePicker(state.timeRange) { range -> viewModel.onTimeRangeChange(range) }

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
 * @param selected [DailyScoredQuestionnaire] that should be displayed as selected one
 * @param onChange Callback to execute when user changes option
 * @param modifier Optional modifier to override visual aspects
 * @see DisplayMenu
 */
@Composable
private fun DisplayQuestionnaireMenu(selected: DailyScoredQuestionnaire,
                                     onChange: (DailyScoredQuestionnaire) -> Unit,
                                     modifier: Modifier = Modifier
)
{
    DisplayMenu(
        label = stringResource(id = R.string.measure),
        options = DailyScoredQuestionnaire.values().map { Pair(stringResource(it.measure.measureRes)) { onChange(it) } },
        selected = stringResource(id = selected.measure.measureRes),
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
 * Display Material Design 3 Date Picker
 * @param selectedRange [Range] of [ZonedDateTime] that user has selected before or by default
 * @param onSelectedRange: Callback to react changes on selected range
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisplayDatePicker(selectedRange : Range<ZonedDateTime>,
                              onSelectedRange : (Range<ZonedDateTime>) -> Unit
)
{
    var showDialog by remember { mutableStateOf(false) }
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = selectedRange.lower.toEpochMilliSecond(),
        initialSelectedEndDateMillis = selectedRange.upper.toEpochMilliSecond(),
    )
    val confirmEnabled by remember { derivedStateOf { dateRangePickerState.selectedEndDateMillis != null } }

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
        colors = TextFieldDefaults.colors(
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
        DatePickerDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                showDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        dateRangePickerState.selectedStartDateMillis?.let { start ->
                            dateRangePickerState.selectedEndDateMillis?.let { end ->
                                onSelectedRange(
                                    Range(
                                        milliSecondToZonedDateTime(start),
                                        milliSecondToZonedDateTime(end)
                                    )
                                )
                            }
                        }
                    },
                    enabled = confirmEnabled
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DateRangePicker(
                state = dateRangePickerState,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

/**
 * Draws a line chart
 * @param heightSize Depending of the height of the screen, present more or less components
 * @param producer Source of the data
 * @param questionnaire [DailyScoredQuestionnaire] whose data is being displayed
 * @param timeGranularity [TimeGranularity] that is used in the chart
 */
@Composable
private fun DrawLineChart(heightSize: WindowHeightSizeClass,
                          producer: ChartEntryModelProducer,
                          questionnaire: DailyScoredQuestionnaire,
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

    val model = producer.getModel()

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
                decorations = decorations,
                persistentMarkers = obtainPersistentMarkers(model)
            ),
            model = model,
            startAxis = startAxis(
                guideline = axisGuidelineComponent(),
                maxLabelCount = (questionnaire.maxScore - questionnaire.minScore + 1),
                titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                title = stringResource(questionnaire.measure.measureRes)),
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

/**
 * Obtain the persistent markers from the model using [SimpleMarkerComponent]
 * @param model Model to extract data
 * @return Map with x values as key and marker as value
 */
@Composable
private fun obtainPersistentMarkers(model: ChartEntryModel) : Map<Float, Marker>
{
    val result = mutableMapOf<Float, Marker>()
    model.entries[0].forEach { entry ->
        result[entry.x] = rememberSimpleMarker()
    }
    return result
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
            selected = DailyScoredQuestionnaire.Stress,
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
            selected = DailyScoredQuestionnaire.Stress,
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

fun DisplayDatePickerPreview()
{
    BienestarEmocionalTheme {
        DisplayDatePicker(
            selectedRange = (ZonedDateTime.now().minusDays(7) .. ZonedDateTime.now()).toRange(),
            onSelectedRange = {})
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable

fun DisplayDatePickerPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        DisplayDatePicker(
            selectedRange = (ZonedDateTime.now().minusDays(7) .. ZonedDateTime.now()).toRange(),
            onSelectedRange = {})
    }
}


@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun StressChartCompactPreview()
{
    val data = List(100) { Random.nextInt(DailyScoredQuestionnaire.Stress.minScore, DailyScoredQuestionnaire.Stress.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Compact,
                producer = producer,
                questionnaire = DailyScoredQuestionnaire.Stress,
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
fun StressChartCompactPreviewDarkTheme()
{
    val data = List(100) { Random.nextInt(DailyScoredQuestionnaire.Stress.minScore, DailyScoredQuestionnaire.Stress.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Compact,
                producer = producer,
                questionnaire = DailyScoredQuestionnaire.Stress,
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
fun StressChartMediumPreview()
{
    val data = List(100) { Random.nextInt(DailyScoredQuestionnaire.Stress.minScore, DailyScoredQuestionnaire.Stress.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Medium,
                producer = producer,
                questionnaire = DailyScoredQuestionnaire.Stress,
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
fun StressChartMediumPreviewDarkTheme()
{
    val data = List(100) { Random.nextInt(DailyScoredQuestionnaire.Stress.minScore, DailyScoredQuestionnaire.Stress.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Medium,
                producer = producer,
                questionnaire = DailyScoredQuestionnaire.Stress,
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
fun DepressionChartCompactPreview()
{
    val data = List(100) { Random.nextInt(DailyScoredQuestionnaire.Depression.minScore, DailyScoredQuestionnaire.Depression.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Compact,
                producer = producer,
                questionnaire = DailyScoredQuestionnaire.Depression,
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
fun DepressionChartPreviewCompactDarkTheme()
{
    val data = List(100) { Random.nextInt(DailyScoredQuestionnaire.Depression.minScore, DailyScoredQuestionnaire.Depression.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Compact,
                producer = producer,
                questionnaire = DailyScoredQuestionnaire.Depression,
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
fun DepressionChartMediumPreview()
{
    val data = List(100) { Random.nextInt(DailyScoredQuestionnaire.Depression.minScore, DailyScoredQuestionnaire.Depression.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Medium,
                producer = producer,
                questionnaire = DailyScoredQuestionnaire.Depression,
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
fun DepressionChartMediumPreviewDarkTheme()
{
    val data = List(100) { Random.nextInt(DailyScoredQuestionnaire.Depression.minScore, DailyScoredQuestionnaire.Depression.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Medium,
                producer = producer,
                questionnaire = DailyScoredQuestionnaire.Depression,
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
fun LonelinessChartCompactPreview()
{
    val data = List(100) { Random.nextInt(DailyScoredQuestionnaire.Loneliness.minScore, DailyScoredQuestionnaire.Loneliness.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Compact,
                producer = producer,
                questionnaire = DailyScoredQuestionnaire.Loneliness,
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
fun LonelinessChartCompactPreviewDarkTheme()
{
    val data = List(100) { Random.nextInt(DailyScoredQuestionnaire.Loneliness.minScore, DailyScoredQuestionnaire.Loneliness.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Compact,
                producer = producer,
                questionnaire = DailyScoredQuestionnaire.Loneliness,
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
fun LonelinessChartMediumPreview()
{
    val data = List(100) { Random.nextInt(DailyScoredQuestionnaire.Loneliness.minScore, DailyScoredQuestionnaire.Loneliness.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Medium,
                producer = producer,
                questionnaire = DailyScoredQuestionnaire.Loneliness,
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
fun LonelinessChartMediumPreviewDarkTheme()
{
    val data = List(100) { Random.nextInt(DailyScoredQuestionnaire.Loneliness.minScore, DailyScoredQuestionnaire.Loneliness.maxScore)}
    val producer = ChartEntryModelProducer()
    producer.setEntries(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.toFloat())})

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DrawLineChart(
                heightSize = WindowHeightSizeClass.Medium,
                producer = producer,
                questionnaire = DailyScoredQuestionnaire.Loneliness,
                timeGranularity = TimeGranularity.Day,
            )
        }
    }
}