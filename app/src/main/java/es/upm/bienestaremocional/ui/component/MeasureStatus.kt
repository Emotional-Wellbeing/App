package es.upm.bienestaremocional.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.Advice
import es.upm.bienestaremocional.data.Measure
import es.upm.bienestaremocional.data.questionnaire.Level
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffQuestionnaire
import es.upm.bienestaremocional.domain.processing.levelToAdvice
import es.upm.bienestaremocional.domain.processing.scoreToLevel
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

@Composable
fun DailyStressStatus(
    navigator: DestinationsNavigator,
    data: Int?,
    widthSize : WindowWidthSizeClass,
    indicatorColor : Color = MaterialTheme.colorScheme.secondary,
    indicatorContainerColor : Color = MaterialTheme.colorScheme.secondaryContainer,
    backgroundColor : Color = MaterialTheme.colorScheme.background,
    showAdvice : Boolean = true,
)
{
    val level = data?.let { scoreToLevel(it, DailyScoredQuestionnaire.Stress) }
    val advice = if (showAdvice) level?.let { levelToAdvice(it, Measure.Stress) } else null

    MeasureStatus(
        navigator = navigator,
        data = data,
        introLabel = stringResource(id = R.string.level_of_stress),
        level = data?.let { scoreToLevel(it, DailyScoredQuestionnaire.Stress) },
        advice = advice,
        widthSize = widthSize,
        indicatorColor = indicatorColor,
        indicatorContainerColor = indicatorContainerColor,
        backgroundColor = backgroundColor,
        minValue = DailyScoredQuestionnaire.Stress.minScore,
        maxValue = DailyScoredQuestionnaire.Stress.maxScore,
    )
}

@Composable
fun DailyDepressionStatus(
    navigator: DestinationsNavigator,
    data: Int?,
    widthSize : WindowWidthSizeClass,
    indicatorColor : Color = MaterialTheme.colorScheme.secondary,
    indicatorContainerColor : Color = MaterialTheme.colorScheme.secondaryContainer,
    backgroundColor : Color = MaterialTheme.colorScheme.background,
    showAdvice : Boolean = true,
)
{
    val level = data?.let { scoreToLevel(it, DailyScoredQuestionnaire.Depression) }
    val advice = if (showAdvice) level?.let { levelToAdvice(it, Measure.Depression) } else null

    MeasureStatus(
        navigator = navigator,
        data = data,
        introLabel = stringResource(id = R.string.level_of_depression),
        level = data?.let { scoreToLevel(it, DailyScoredQuestionnaire.Depression) },
        advice = advice,
        widthSize = widthSize,
        indicatorColor = indicatorColor,
        indicatorContainerColor = indicatorContainerColor,
        backgroundColor = backgroundColor,
        minValue = DailyScoredQuestionnaire.Depression.minScore,
        maxValue = DailyScoredQuestionnaire.Depression.maxScore
    )
}

@Composable
fun DailyLonelinessStatus(
    navigator: DestinationsNavigator,
    data: Int?,
    widthSize : WindowWidthSizeClass,
    indicatorColor : Color = MaterialTheme.colorScheme.secondary,
    indicatorContainerColor : Color = MaterialTheme.colorScheme.secondaryContainer,
    backgroundColor : Color = MaterialTheme.colorScheme.background,
    showAdvice : Boolean = true,
)
{
    val level = data?.let { scoreToLevel(it, DailyScoredQuestionnaire.Loneliness) }
    val advice = if (showAdvice) level?.let { levelToAdvice(it, Measure.Loneliness) } else null

    MeasureStatus(
        navigator = navigator,
        data = data,
        introLabel = stringResource(id = R.string.level_of_loneliness),
        level = data?.let { scoreToLevel(it, OneOffQuestionnaire.Loneliness) },
        advice = advice,
        widthSize = widthSize,
        indicatorColor = indicatorColor,
        indicatorContainerColor = indicatorContainerColor,
        backgroundColor = backgroundColor,
        minValue = DailyScoredQuestionnaire.Loneliness.minScore,
        maxValue = DailyScoredQuestionnaire.Loneliness.maxScore
    )
}

@Composable
fun OneOffStressStatus(
    navigator: DestinationsNavigator,
    data: Int?,
    widthSize : WindowWidthSizeClass,
    indicatorColor : Color = MaterialTheme.colorScheme.secondary,
    indicatorContainerColor : Color = MaterialTheme.colorScheme.secondaryContainer,
    backgroundColor : Color = MaterialTheme.colorScheme.background,
    showAdvice : Boolean = true,
)
{
    val level = data?.let { scoreToLevel(it, OneOffQuestionnaire.Stress) }
    val advice = if (showAdvice) level?.let { levelToAdvice(it, Measure.Stress) } else null

    MeasureStatus(
        navigator = navigator,
        data = data,
        introLabel = stringResource(id = R.string.level_of_stress),
        level = data?.let { scoreToLevel(it, OneOffQuestionnaire.Stress) },
        advice = advice,
        widthSize = widthSize,
        indicatorColor = indicatorColor,
        indicatorContainerColor = indicatorContainerColor,
        backgroundColor = backgroundColor,
        minValue = OneOffQuestionnaire.Stress.minScore,
        maxValue = OneOffQuestionnaire.Stress.maxScore,
    )
}

@Composable
fun OneOffDepressionStatus(
    navigator: DestinationsNavigator,
    data: Int?,
    widthSize : WindowWidthSizeClass,
    indicatorColor : Color = MaterialTheme.colorScheme.secondary,
    indicatorContainerColor : Color = MaterialTheme.colorScheme.secondaryContainer,
    backgroundColor : Color = MaterialTheme.colorScheme.background,
    showAdvice : Boolean = true,
)
{
    val level = data?.let { scoreToLevel(it, OneOffQuestionnaire.Depression) }
    val advice = if (showAdvice) level?.let { levelToAdvice(it, Measure.Depression) } else null

    MeasureStatus(
        navigator = navigator,
        data = data,
        introLabel = stringResource(id = R.string.level_of_depression),
        level = data?.let { scoreToLevel(it, OneOffQuestionnaire.Depression) },
        advice = advice,
        widthSize = widthSize,
        indicatorColor = indicatorColor,
        indicatorContainerColor = indicatorContainerColor,
        backgroundColor = backgroundColor,
        minValue = OneOffQuestionnaire.Depression.minScore,
        maxValue = OneOffQuestionnaire.Depression.maxScore
    )
}

@Composable
fun OneOffLonelinessStatus(
    navigator: DestinationsNavigator,
    data: Int?,
    widthSize : WindowWidthSizeClass,
    indicatorColor : Color = MaterialTheme.colorScheme.secondary,
    indicatorContainerColor : Color = MaterialTheme.colorScheme.secondaryContainer,
    backgroundColor : Color = MaterialTheme.colorScheme.background,
    showAdvice : Boolean = true,
)
{
    val level = data?.let { scoreToLevel(it, OneOffQuestionnaire.Loneliness) }
    val advice = if (showAdvice) level?.let { levelToAdvice(it, Measure.Loneliness) } else null

    MeasureStatus(
        navigator = navigator,
        data = data,
        introLabel = stringResource(id = R.string.level_of_loneliness),
        level = data?.let { scoreToLevel(it, OneOffQuestionnaire.Loneliness) },
        advice = advice,
        widthSize = widthSize,
        indicatorColor = indicatorColor,
        indicatorContainerColor = indicatorContainerColor,
        backgroundColor = backgroundColor,
        minValue = OneOffQuestionnaire.Loneliness.minScore,
        maxValue = OneOffQuestionnaire.Loneliness.maxScore
    )
}



@Composable
private fun MeasureStatus(
    navigator : DestinationsNavigator,
    data: Int?,
    introLabel : String,
    level: Level?,
    advice : Advice?,
    widthSize : WindowWidthSizeClass,
    indicatorColor : Color,
    indicatorContainerColor : Color,
    backgroundColor : Color,
    minValue: Int = 0,
    maxValue : Int = 100,
)
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
    val cpiTextStyle = when(widthSize)
    {
        WindowWidthSizeClass.Compact -> MaterialTheme.typography.displaySmall
        WindowWidthSizeClass.Medium -> MaterialTheme.typography.displayMedium
        WindowWidthSizeClass.Expanded -> MaterialTheme.typography.displayMedium
        else -> MaterialTheme.typography.displaySmall
    }

    val headlineTextStyle = when(widthSize)
    {
        WindowWidthSizeClass.Compact -> MaterialTheme.typography.titleSmall
        WindowWidthSizeClass.Medium -> MaterialTheme.typography.titleMedium
        WindowWidthSizeClass.Expanded -> MaterialTheme.typography.titleLarge
        else -> MaterialTheme.typography.titleSmall
    }

    val adviceTextStyle = when(widthSize)
    {
        WindowWidthSizeClass.Compact -> MaterialTheme.typography.bodySmall
        WindowWidthSizeClass.Medium -> MaterialTheme.typography.bodyMedium
        WindowWidthSizeClass.Expanded -> MaterialTheme.typography.bodyLarge
        else -> MaterialTheme.typography.bodySmall
    }

    // Text to display
    val levelLabel = stringResource(id = (level?.label ?: R.string.unknown_display))
    val headlineText = "$introLabel: $levelLabel"

    // Composable elements
    BoxWithConstraints(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    )
    {
        // Since we use maxSize, maxHeight and maxWidth are infinity
        // minHeight is always 0.0 and minWidth in all scenarios is different from zero

        // Due to weight limitations, we cannot use it directly, so we compute the sizes of
        // the elements. The width of cpi box is 25% of the whole row, while text width is the
        // rest (75%)

        val cpiWidth = this.minWidth / 4
        val textWidth = this.minWidth - cpiWidth

        // Height of the cpi is the width of the CPI * scaleFactor

        val cpiHeight = cpiWidth * scaleFactor

        Row(verticalAlignment = Alignment.CenterVertically)
        {
            Box(
                modifier = Modifier
                    .width(cpiWidth)
                    .height(cpiHeight),
                propagateMinConstraints = true,
                contentAlignment = Alignment.Center
            )
            {
                CircularProgressIndicator(
                    data = data,
                    minValue = minValue,
                    maxValue = maxValue,
                    size = cpiHeight,
                    indicatorColor = indicatorColor,
                    indicatorContainerColor = indicatorContainerColor,
                    backgroundColor = backgroundColor,
                    textStyle = cpiTextStyle
                )
            }

            Column(
                modifier = Modifier
                    .width(textWidth)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterVertically),
            )
            {
                Text(headlineText, style = headlineTextStyle)
                advice?.let {
                    ShowAdviceHeadline(
                        navigator = navigator,
                        advice = it,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        textStyle = adviceTextStyle
                    )
                }
            }
        }
    }
}


@Preview(
    group = "Light Theme",
)
@Composable
fun DailyStressStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            DailyStressStatus(
                navigator = EmptyDestinationsNavigator,
                data = 27,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun DailyStressStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            DailyStressStatus(
                navigator = EmptyDestinationsNavigator,
                data = 27,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun DailyDepressionStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            DailyDepressionStatus(
                navigator = EmptyDestinationsNavigator,
                data = 16,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun DailyDepressionStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            DailyDepressionStatus(
                navigator = EmptyDestinationsNavigator,
                data = 16,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun DailyLonelinessStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            DailyLonelinessStatus(
                navigator = EmptyDestinationsNavigator,
                data = 47,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun DailyLonelinessStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            DailyLonelinessStatus(
                navigator = EmptyDestinationsNavigator,
                data = 47,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Light Theme",
)
@Composable
fun OneOffStressStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            OneOffStressStatus(
                navigator = EmptyDestinationsNavigator,
                data = 27,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun OneOffStressStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            OneOffStressStatus(
                navigator = EmptyDestinationsNavigator,
                data = 27,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun OneOffDepressionStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            OneOffDepressionStatus(
                navigator = EmptyDestinationsNavigator,
                data = 16,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun OneOffDepressionStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            OneOffDepressionStatus(
                navigator = EmptyDestinationsNavigator,
                data = 16,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun OneOffLonelinessStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            OneOffLonelinessStatus(
                navigator = EmptyDestinationsNavigator,
                data = 47,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun OneOffLonelinessStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            OneOffLonelinessStatus(
                navigator = EmptyDestinationsNavigator,
                data = 47,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun MeasureStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            MeasureStatus(
                navigator = EmptyDestinationsNavigator,
                data = 33,
                introLabel = stringResource(R.string.measure),
                level = Level.Low,
                advice = Advice(head = R.string.low_stress_advice, body = null),
                widthSize = WindowWidthSizeClass.Compact,
                indicatorColor = MaterialTheme.colorScheme.secondary,
                indicatorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                backgroundColor = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun MeasureStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            MeasureStatus(
                navigator = EmptyDestinationsNavigator,
                data = 33,
                introLabel = stringResource(R.string.measure),
                level = Level.Low,
                advice = Advice(head = R.string.low_stress_advice, body = null),
                widthSize = WindowWidthSizeClass.Compact,
                indicatorColor = MaterialTheme.colorScheme.secondary,
                indicatorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                backgroundColor = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun MeasureStatusNullCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            MeasureStatus(
                navigator = EmptyDestinationsNavigator,
                data = null,
                introLabel = stringResource(R.string.measure),
                level = null,
                advice = null,
                widthSize = WindowWidthSizeClass.Compact,
                indicatorColor = MaterialTheme.colorScheme.secondary,
                indicatorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                backgroundColor = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun MeasureStatusNullCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            MeasureStatus(
                navigator = EmptyDestinationsNavigator,
                data = null,
                introLabel = stringResource(R.string.measure),
                level = null,
                advice = null,
                widthSize = WindowWidthSizeClass.Compact,
                indicatorColor = MaterialTheme.colorScheme.secondary,
                indicatorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                backgroundColor = MaterialTheme.colorScheme.background
            )
        }
    }
}