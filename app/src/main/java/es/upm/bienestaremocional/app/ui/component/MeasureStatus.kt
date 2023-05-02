package es.upm.bienestaremocional.app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.questionnaire.LevelLabel
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.domain.processing.scoreToLevelLabel
import es.upm.bienestaremocional.core.ui.component.CircularProgressIndicator
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun StressStatus(data: Float?,
                 widthSize : WindowWidthSizeClass
)
{
    MeasureStatus(data = data,
        introLabel = stringResource(id = R.string.level_of_stress),
        level = data?.let { scoreToLevelLabel(it,Questionnaire.PSS) },
        widthSize = widthSize,
        minValue = Questionnaire.PSS.minScore.toFloat(),
        maxValue = Questionnaire.PSS.maxScore.toFloat(),
    )
}

@Composable
fun DepressionStatus(data: Float?,
                     widthSize : WindowWidthSizeClass)
{
    MeasureStatus(data = data,
        introLabel = stringResource(id = R.string.level_of_depression),
        level = data?.let { scoreToLevelLabel(it,Questionnaire.PHQ) },
        widthSize = widthSize,
        minValue = Questionnaire.PHQ.minScore.toFloat(),
        maxValue = Questionnaire.PHQ.maxScore.toFloat(),
    )
}

@Composable
fun LonelinessStatus(data: Float?,
                     widthSize : WindowWidthSizeClass)
{
    MeasureStatus(data = data,
        introLabel = stringResource(id = R.string.level_of_loneliness),
        level = data?.let { scoreToLevelLabel(it,Questionnaire.UCLA) },
        widthSize = widthSize,
        minValue = Questionnaire.UCLA.minScore.toFloat(),
        maxValue = Questionnaire.UCLA.maxScore.toFloat(),
    )
}


@Composable
fun MeasureStatus(
    data: Float?,
    introLabel : String,
    level: LevelLabel?,
    widthSize : WindowWidthSizeClass,
    minValue: Float = 0f,
    maxValue : Float = 100f,
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

    // Text to display
    val levelLabel = stringResource(id = (level?.label ?: R.string.unknown_display))
    val headlineText = "$introLabel : $levelLabel"

    // Composable elements
    Card()
    {
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
                    CircularProgressIndicator(data = data,
                        minValue = minValue,
                        maxValue = maxValue,
                        size = cpiHeight,
                        indicatorColor = MaterialTheme.colorScheme.secondary,
                        indicatorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                        textStyle = cpiTextStyle
                    )
                }

                Column(
                    modifier = Modifier
                        .width(textWidth)
                        .padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center,
                )
                {
                    Text(headlineText, style = headlineTextStyle)
                    //TODO add advices
                    //Text("Advice", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }

}


@Preview(
    group = "Light Theme",
)
@Composable
fun StressStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            StressStatus(data = 27f,
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun StressStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            StressStatus(data = 27f,
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun DepressionStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            DepressionStatus(data = 16f,
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun DepressionStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            DepressionStatus(data = 16f,
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun LonelinessStatusCompactPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            LonelinessStatus(data = 47f,
                widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun LonelinessStatusCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            LonelinessStatus(data = 47f,
                widthSize = WindowWidthSizeClass.Compact)
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
                data = 33f,
                introLabel = "Level of measure",
                level = LevelLabel.Low,
                widthSize = WindowWidthSizeClass.Compact
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
                data = 33f,
                introLabel = "Level of measure",
                level = LevelLabel.Low,
                widthSize = WindowWidthSizeClass.Compact
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
                data = null,
                introLabel = "Level of measure",
                level = null,
                widthSize = WindowWidthSizeClass.Compact
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
                data = null,
                introLabel = "Level of measure",
                level = null,
                widthSize = WindowWidthSizeClass.Compact
            )
        }
    }
}