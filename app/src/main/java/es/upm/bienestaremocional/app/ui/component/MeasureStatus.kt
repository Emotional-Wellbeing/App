package es.upm.bienestaremocional.app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.domain.processing.scoreToLevelLabel
import es.upm.bienestaremocional.core.ui.component.CircularProgressIndicator
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun StressStatus(data: Float?)
{
    val introLabel = stringResource(id = R.string.level_of_stress)
    val levelLabel = data?.let {
        scoreToLevelLabel(it,Questionnaire.PSS)?.label?.let { label ->
            stringResource(id = label)
        } ?: stringResource(id = R.string.unknown_display)
    } ?: stringResource(id = R.string.unknown_display)

    MeasureStatus(data = data,
        headlineText = "$introLabel : $levelLabel",
        minValue = Questionnaire.PSS.minScore.toFloat(),
        maxValue = Questionnaire.PSS.maxScore.toFloat(),
    )
}

@Composable
fun DepressionStatus(data: Float?)
{
    val introLabel = stringResource(id = R.string.level_of_depression)
    val levelLabel = data?.let {
        scoreToLevelLabel(it,Questionnaire.PHQ)?.label?.let { label ->
            stringResource(id = label)
        } ?: stringResource(id = R.string.unknown_display)
    } ?: stringResource(id = R.string.unknown_display)

    MeasureStatus(data = data,
        headlineText = "$introLabel : $levelLabel",
        minValue = Questionnaire.PHQ.minScore.toFloat(),
        maxValue = Questionnaire.PHQ.maxScore.toFloat(),
    )
}

@Composable
fun LonelinessStatus(data: Float?)
{
    val introLabel = stringResource(id = R.string.level_of_loneliness)
    val levelLabel = data?.let {
        scoreToLevelLabel(it,Questionnaire.UCLA)?.label?.let { label ->
            stringResource(id = label)
        } ?: stringResource(id = R.string.unknown_display)
    } ?: stringResource(id = R.string.unknown_display)

    MeasureStatus(data = data,
        headlineText = "$introLabel : $levelLabel",
        minValue = Questionnaire.UCLA.minScore.toFloat(),
        maxValue = Questionnaire.UCLA.maxScore.toFloat(),
    )
}


@Composable
fun MeasureStatus(
    data: Float?,
    headlineText : String,
    minValue: Float = 0f,
    maxValue : Float = 100f,
)
{
    Card()
    {
        Row(modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(16.dp))
        {
            Box(modifier = Modifier.weight(1f))
            {
                CircularProgressIndicator(data = data,
                    minValue = minValue,
                    maxValue = maxValue,
                    size = 100.dp,
                    indicatorThickness = 10.dp)
            }

            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            )
            {
                Text(headlineText, style = MaterialTheme.typography.titleMedium)
                //TODO add advices
                //Text("Advice", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

}

@Preview(
    group = "Light Theme"
)
@Composable
fun StressStatusPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            StressStatus(data = 27f)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun StressStatusPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            StressStatus(data = 27f)
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun DepressionStatusPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            DepressionStatus(data = 16f)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun DepressionStatusPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            DepressionStatus(data = 16f)
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun LonelinessStatusPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            LonelinessStatus(data = 47f)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun LonelinessStatusPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            LonelinessStatus(data = 47f)
        }
    }
}


@Preview(
    group = "Light Theme"
)
@Composable
fun MeasureStatusPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            MeasureStatus(
                data = 33f,
                headlineText = "Level of measure: low"
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun MeasureStatusPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            MeasureStatus(
                data = 33f,
                headlineText = "Level of measure: low"
            )
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun MeasureStatusNullPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            MeasureStatus(
                data = null,
                headlineText = "Level of measure: ${stringResource(id = R.string.unknown_display)}"
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun MeasureStatusNullPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            MeasureStatus(
                data = null,
                headlineText = "Level of measure: ${stringResource(id = R.string.unknown_display)}"
            )
        }
    }
}