package es.upm.bienestaremocional.app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.questionnaire.LevelLabel
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.domain.processing.scoreToLevelLabel
import es.upm.bienestaremocional.core.ui.component.CircularProgressIndicator
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme


@Composable
fun DoubleStressStatus(data: Pair<Int?,Int?>,
                       headers: Pair<String, String>,
                       widthSize : WindowWidthSizeClass
)
{
    // If we have score and level label, put it. If we don't have any of them,
    // put R.string.unknown_display
    val firstSubtitle = stringResource(data.first?.let {
        scoreToLevelLabel(it, Questionnaire.PSS)?.label
            ?: R.string.unknown_display }
        ?: R.string.unknown_display)

    val secondSubtitle = stringResource(data.second?.let {
        scoreToLevelLabel(it, Questionnaire.PSS)?.label
            ?: R.string.unknown_display }
        ?: R.string.unknown_display)

    DoubleMeasureStatus(
        data = data,
        measureLabel = stringResource(R.string.stress),
        headers = headers,
        subtitle = Pair(firstSubtitle,secondSubtitle),
        widthSize = widthSize,
        minValue = Questionnaire.PSS.minScore,
        maxValue = Questionnaire.PSS.maxScore,
    )
}

@Composable
fun DoubleDepressionStatus(data: Pair<Int?,Int?>,
                           headers: Pair<String, String>,
                           widthSize : WindowWidthSizeClass)
{
    // If we have score and level label, put it. If we don't have any of them,
    // put R.string.unknown_display
    val firstSubtitle = stringResource(data.first?.let {
        scoreToLevelLabel(it, Questionnaire.PHQ)?.label
            ?: R.string.unknown_display }
        ?: R.string.unknown_display)

    val secondSubtitle = stringResource(data.second?.let {
        scoreToLevelLabel(it, Questionnaire.PHQ)?.label
            ?: R.string.unknown_display }
        ?: R.string.unknown_display)

    DoubleMeasureStatus(
        data = data,
        measureLabel = stringResource(R.string.depression),
        headers = headers,
        subtitle = Pair(firstSubtitle,secondSubtitle),
        widthSize = widthSize,
        minValue = Questionnaire.PHQ.minScore,
        maxValue = Questionnaire.PHQ.maxScore,
    )
}

@Composable
fun DoubleLonelinessStatus(data: Pair<Int?,Int?>,
                           headers: Pair<String, String>,
                           widthSize : WindowWidthSizeClass)
{
    // If we have score and level label, put it. If we don't have any of them,
    // put R.string.unknown_display
    val firstSubtitle = stringResource(data.first?.let {
        scoreToLevelLabel(it, Questionnaire.UCLA)?.label
            ?: R.string.unknown_display }
        ?: R.string.unknown_display)

    val secondSubtitle = stringResource(data.second?.let {
        scoreToLevelLabel(it, Questionnaire.UCLA)?.label
            ?: R.string.unknown_display }
        ?: R.string.unknown_display)

    DoubleMeasureStatus(
        data = data,
        measureLabel = stringResource(R.string.loneliness),
        headers = headers,
        subtitle = Pair(firstSubtitle,secondSubtitle),
        widthSize = widthSize,
        minValue = Questionnaire.UCLA.minScore,
        maxValue = Questionnaire.UCLA.maxScore,
    )
}

@Composable
private fun DoubleMeasureStatus(data: Pair<Int?, Int?>,
                                measureLabel : String,
                                headers : Pair<String,String>,
                                subtitle : Pair<String,String>,
                                widthSize : WindowWidthSizeClass,
                                minValue: Int = 0,
                                maxValue : Int = 100)
{
    // Scale factor to reduce the height of row in bigger screens
    val scaleFactor = when(widthSize)
    {
        WindowWidthSizeClass.Compact -> 1f
        WindowWidthSizeClass.Medium -> 0.75f
        WindowWidthSizeClass.Expanded -> 0.5f
        else -> 1f
    }

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

    // Text to display
    val introLabel = stringResource(R.string.measure)
    val headlineText = "$introLabel: $measureLabel"

    // Composable elements
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(headlineText, style = headlineTextStyle)

        BoxWithConstraints(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        )
        {
            val cpiWidth = this.minWidth / 2.25f
            val spacerWidth = this.minWidth - (cpiWidth.times(2))

            // Height of the cpi is the width of the CPI * scaleFactor

            val cpiHeight = cpiWidth * scaleFactor

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            )
            {
                TextAndIndicator(
                    header = headers.first,
                    data = data.first,
                    subtitle = subtitle.first,
                    cpiHeight = cpiHeight,
                    minValue = minValue,
                    maxValue = maxValue,
                    headlineTextStyle = headlineTextStyle,
                    dataTextStyle = dataTextStyle,
                    subtitleTextStyle = subtitleTextStyle
                )

                Spacer(modifier = Modifier.width(spacerWidth))

                TextAndIndicator(
                    header = headers.second,
                    data = data.second,
                    subtitle = subtitle.second,
                    cpiHeight = cpiHeight,
                    minValue = minValue,
                    maxValue = maxValue,
                    headlineTextStyle = headlineTextStyle,
                    dataTextStyle = dataTextStyle,
                    subtitleTextStyle = subtitleTextStyle
                )
            }
        }
    }
}

@Composable
private fun TextAndIndicator(
    header : String,
    data: Int?,
    subtitle: String,
    cpiHeight : Dp,
    minValue: Int = 0,
    maxValue : Int = 100,
    headlineTextStyle : TextStyle,
    dataTextStyle : TextStyle,
    subtitleTextStyle : TextStyle
)
{
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(text = header, style = headlineTextStyle)

        CircularProgressIndicator(
            data = data,
            subtitle = subtitle,
            minValue = minValue,
            maxValue = maxValue,
            size = cpiHeight,
            subtitleWidth = cpiHeight.times(0.7f),
            indicatorColor = MaterialTheme.colorScheme.secondary,
            indicatorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            dataTextStyle = dataTextStyle,
            subtitleTextStyle = subtitleTextStyle
        )
    }
}


@Preview(
    group = "Light Theme",
)
@Composable
fun DoubleStressStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            DoubleStressStatus(data = Pair(17,27),
                headers = Pair(
                    stringResource(R.string.yesterday),
                    stringResource(R.string.last_seven_days)
                ),
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun DoubleStressStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            DoubleStressStatus(data = Pair(17,27),
                headers = Pair(
                    stringResource(R.string.yesterday),
                    stringResource(R.string.last_seven_days)
                ),
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun DoubleDepressionStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            DoubleDepressionStatus(data = Pair(6,16),
                headers = Pair(
                    stringResource(R.string.yesterday),
                    stringResource(R.string.last_seven_days)
                ),
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun DoubleDepressionStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            DoubleDepressionStatus(data = Pair(6,16),
                headers = Pair(
                    stringResource(R.string.yesterday),
                    stringResource(R.string.last_seven_days)
                ),
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun DoubleLonelinessStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            DoubleLonelinessStatus(data = Pair(37,47),
                headers = Pair(
                    stringResource(R.string.yesterday),
                    stringResource(R.string.last_seven_days)
                ),
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun DoubleLonelinessStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            DoubleLonelinessStatus(data = Pair(37,47),
                headers = Pair(
                    stringResource(R.string.yesterday),
                    stringResource(R.string.last_seven_days)
                ),
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun DoubleMeasureStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            DoubleMeasureStatus(
                data = Pair(22,33),
                measureLabel = stringResource(id = R.string.stress),
                headers = Pair("Ayer","Semana"),
                subtitle = Pair(
                    stringResource(LevelLabel.Low.label),
                    stringResource(LevelLabel.ModeratelySevere.label)
                ),
                widthSize = WindowWidthSizeClass.Compact,
            )
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
            DoubleMeasureStatus(
                data = Pair(22,33),
                measureLabel = stringResource(id = R.string.stress),
                headers = Pair("Ayer","Semana"),
                subtitle = Pair(
                    stringResource(LevelLabel.Low.label),
                    stringResource(LevelLabel.ModeratelySevere.label)
                ),
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun DoubleMeasureStatusNullCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            DoubleMeasureStatus(
                data = Pair(null,null),
                measureLabel = stringResource(id = R.string.stress),
                headers = Pair("Ayer","Semana"),
                subtitle = Pair(
                    stringResource(LevelLabel.Low.label),
                    stringResource(LevelLabel.ModeratelySevere.label)
                ),
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun DoubleMeasureStatusNullCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            DoubleMeasureStatus(
                data = Pair(null,null),
                measureLabel = stringResource(id = R.string.stress),
                headers = Pair("Ayer","Semana"),
                subtitle = Pair(
                    stringResource(LevelLabel.Low.label),
                    stringResource(LevelLabel.ModeratelySevere.label)
                ),
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}