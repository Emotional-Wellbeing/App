package es.upm.bienestaremocional.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.domain.processing.scoreToLevel
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme


@Composable
fun DoubleMeasureStatus(data : Pair<Int?,Int?>,
                        headers : Pair<String, String>,
                        questionnaire : Questionnaire,
                        height: Dp,
                        widthSize : WindowWidthSizeClass,
                        showHeadline : Boolean = true,
                        indicatorColor : Color = MaterialTheme.colorScheme.secondary,
                        indicatorContainerColor : Color = MaterialTheme.colorScheme.secondaryContainer)
{
    // If we have score and level label, put it. If we don't have any of them,
    // put R.string.unknown_display
    val firstSubtitle = stringResource(data.first?.let {
        scoreToLevel(it, questionnaire)?.label
            ?: R.string.unknown_display }
        ?: R.string.unknown_display)

    val secondSubtitle = stringResource(data.second?.let {
        scoreToLevel(it, questionnaire)?.label
            ?: R.string.unknown_display }
        ?: R.string.unknown_display)

    val measureLabel =
        if (showHeadline)
        {
            stringResource(when (questionnaire)
                {
                    Questionnaire.PSS -> R.string.stress
                    Questionnaire.PHQ -> R.string.depression
                    Questionnaire.UCLA -> R.string.loneliness
                }
            )
        }
    else
        null

    DoubleMeasureStatus(
        data = data,
        measureLabel = measureLabel,
        headers = headers,
        subtitle = Pair(firstSubtitle,secondSubtitle),
        height = height,
        widthSize = widthSize,
        indicatorColor = indicatorColor,
        indicatorContainerColor = indicatorContainerColor,
        minValue = questionnaire.minScore,
        maxValue = questionnaire.maxScore,
    )
}

@Composable
private fun DoubleMeasureStatus(data: Pair<Int?, Int?>,
                                measureLabel : String?,
                                headers : Pair<String,String>,
                                subtitle : Pair<String,String>,
                                height : Dp,
                                widthSize : WindowWidthSizeClass,
                                indicatorColor : Color,
                                indicatorContainerColor : Color,
                                minValue : Int = 0,
                                maxValue : Int = 100)
{

    // Text sizes
    val headlineTextStyle = when(widthSize)
    {
        WindowWidthSizeClass.Compact -> MaterialTheme.typography.titleSmall
        WindowWidthSizeClass.Medium -> MaterialTheme.typography.titleMedium
        WindowWidthSizeClass.Expanded -> MaterialTheme.typography.titleLarge
        else -> MaterialTheme.typography.titleSmall
    }

    val dataTextStyle = when(widthSize)
    {
        WindowWidthSizeClass.Compact -> MaterialTheme.typography.displaySmall
        WindowWidthSizeClass.Medium -> MaterialTheme.typography.displayMedium
        WindowWidthSizeClass.Expanded -> MaterialTheme.typography.displayMedium
        else -> MaterialTheme.typography.displaySmall
    }

    val subtitleTextStyle = when(widthSize)
    {
        WindowWidthSizeClass.Compact -> MaterialTheme.typography.bodySmall
        WindowWidthSizeClass.Medium -> MaterialTheme.typography.bodyMedium
        WindowWidthSizeClass.Expanded -> MaterialTheme.typography.bodyLarge
        else -> MaterialTheme.typography.bodySmall
    }

    var headlineText : String? = null

    // Text to display
    measureLabel?.let {
        val introLabel = stringResource(R.string.measure)
        headlineText = "$introLabel: $measureLabel"
    }


    // Composable elements
    Column(
        modifier = Modifier.height(height),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        headlineText?.let { Text(it, style = headlineTextStyle) }

        BoxWithConstraints(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        )
        {
            val cpiWidth = this.minWidth / 2.25f
            val spacerWidth = this.minWidth - (cpiWidth.times(2))

            val cpiHeight = maxHeight
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            )
            {
                Box(modifier = Modifier.width(cpiWidth),
                    contentAlignment = Alignment.Center
                )
                {
                    TextAndIndicator(
                        header = headers.first,
                        data = data.first,
                        subtitle = subtitle.first,
                        height = cpiHeight,
                        minValue = minValue,
                        maxValue = maxValue,
                        indicatorColor = indicatorColor,
                        indicatorContainerColor = indicatorContainerColor,
                        headlineTextStyle = headlineTextStyle,
                        dataTextStyle = dataTextStyle,
                        subtitleTextStyle = subtitleTextStyle
                    )
                }

                Spacer(modifier = Modifier.width(spacerWidth))

                Box(modifier = Modifier.width(cpiWidth),
                    contentAlignment = Alignment.Center
                )
                {
                    TextAndIndicator(
                        header = headers.second,
                        data = data.second,
                        subtitle = subtitle.second,
                        height = cpiHeight,
                        minValue = minValue,
                        maxValue = maxValue,
                        indicatorColor = indicatorColor,
                        indicatorContainerColor = indicatorContainerColor,
                        headlineTextStyle = headlineTextStyle,
                        dataTextStyle = dataTextStyle,
                        subtitleTextStyle = subtitleTextStyle
                    )
                }
            }
        }
    }
}

@Composable
private fun TextAndIndicator(
    header : String,
    data: Int?,
    subtitle: String,
    height : Dp,
    minValue: Int = 0,
    maxValue : Int = 100,
    indicatorColor: Color,
    indicatorContainerColor: Color,
    headlineTextStyle : TextStyle,
    dataTextStyle : TextStyle,
    subtitleTextStyle : TextStyle
)
{
    Column(
        modifier = Modifier.height(height),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(text = header, style = headlineTextStyle)

        BoxWithConstraints()
        {
            val size = min(maxHeight,maxWidth)
            CircularProgressIndicator(
                data = data,
                subtitle = subtitle,
                minValue = minValue,
                maxValue = maxValue,
                size = size,
                subtitleWidth = size.times(0.7f),
                indicatorColor = indicatorColor,
                indicatorContainerColor = indicatorContainerColor,
                dataTextStyle = dataTextStyle,
                subtitleTextStyle = subtitleTextStyle
            )
        }
    }
}


@Preview(
    group = "Light Theme",
)
@Composable
fun DoubleMeasureStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            DoubleMeasureStatus(data = Pair(17,27),
                headers = Pair(
                    stringResource(R.string.yesterday),
                    stringResource(R.string.last_seven_days)
                ),
                height = 200.dp,
                questionnaire = Questionnaire.PSS,
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun DoubleMeasureStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            DoubleMeasureStatus(data = Pair(17,27),
                headers = Pair(
                    stringResource(R.string.yesterday),
                    stringResource(R.string.last_seven_days)
                ),
                height = 200.dp,
                questionnaire = Questionnaire.PSS,
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Light Theme",
)
@Composable
fun DoubleMeasureStatusNoHeadlineCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            DoubleMeasureStatus(data = Pair(17,27),
                headers = Pair(
                    stringResource(R.string.yesterday),
                    stringResource(R.string.last_seven_days)
                ),
                height = 200.dp,
                showHeadline = false,
                questionnaire = Questionnaire.PSS,
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun DoubleMeasureStatusNoHeadlineCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            DoubleMeasureStatus(data = Pair(17,27),
                headers = Pair(
                    stringResource(R.string.yesterday),
                    stringResource(R.string.last_seven_days)
                ),
                height = 200.dp,
                showHeadline = false,
                questionnaire = Questionnaire.PSS,
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}