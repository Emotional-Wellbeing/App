package es.upm.bienestaremocional.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.domain.processing.scoreToLevel
import es.upm.bienestaremocional.ui.component.CircularProgressIndicator
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MeasureSummary(
    questionnaire: DailyScoredQuestionnaire,
    score: Int?,
    pagerState: PagerState,
    onClick: () -> Unit,
    widthSize: WindowWidthSizeClass,
    heightSize: WindowHeightSizeClass
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        MeasureSummary(
            questionnaire = questionnaire,
            score = score,
            onClick = onClick,
            widthSize = widthSize,
            heightSize = heightSize,
            heightFraction = 0.9f
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun MeasureSummary(
    questionnaire: DailyScoredQuestionnaire,
    score: Int?,
    onClick: () -> Unit,
    widthSize: WindowWidthSizeClass,
    heightSize: WindowHeightSizeClass,
    heightFraction: Float = 1f,
) {
    // Checks
    require(heightFraction in 0f..1f)

    val level = score?.let { scoreToLevel(it, questionnaire) }

    // Text to display
    val introLabel = stringResource(
        when (questionnaire) {
            DailyScoredQuestionnaire.Stress -> R.string.level_of_stress
            DailyScoredQuestionnaire.Depression -> R.string.level_of_depression
            DailyScoredQuestionnaire.Loneliness -> R.string.level_of_loneliness
        }
    )

    val levelLabel = stringResource(id = (level?.label ?: R.string.unknown_display))
    val headlineText = "$introLabel $levelLabel"

    val adviceRes = questionnaire.measure.advices?.get(level)?.get(0)
    val advice = adviceRes?.let { stringResource(it) }

    // Styles
    val titleStyle = if (widthSize >= WindowWidthSizeClass.Medium &&
        heightSize >= WindowHeightSizeClass.Medium
    )
        MaterialTheme.typography.titleLarge
    else
        MaterialTheme.typography.titleMedium

    val adviceStyle = if (widthSize >= WindowWidthSizeClass.Medium &&
        heightSize >= WindowHeightSizeClass.Medium
    )
        MaterialTheme.typography.bodyLarge
    else
        MaterialTheme.typography.bodyMedium

    // Text sizes
    val cpiTextStyle = when (widthSize) {
        WindowWidthSizeClass.Compact -> MaterialTheme.typography.displaySmall
        WindowWidthSizeClass.Medium -> MaterialTheme.typography.displayMedium
        WindowWidthSizeClass.Expanded -> MaterialTheme.typography.displayMedium
        else -> MaterialTheme.typography.displaySmall
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight(heightFraction)
    )
    {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        )
        {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            )
            {
                CircularProgressIndicator(
                    data = score,
                    minValue = questionnaire.minScore,
                    maxValue = questionnaire.maxScore,
                    size = maxHeight,
                    textStyle = cpiTextStyle,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    indicatorContainerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        )
        {
            Text(
                text = headlineText,
                color = MaterialTheme.colorScheme.secondary,
                style = titleStyle
            )
            advice?.let { Text(text = it, style = adviceStyle) }
            TextButton(onClick = onClick) { Text(stringResource(R.string.more_details)) }
        }
    }
}


@Preview
@Composable
fun MeasureSummaryPreview() {
    BienestarEmocionalTheme {
        Surface {
            MeasureSummary(
                questionnaire = DailyScoredQuestionnaire.Stress,
                score = 27,
                onClick = {},
                heightSize = WindowHeightSizeClass.Compact,
                widthSize = WindowWidthSizeClass.Compact,
            )
        }
    }
}

@Preview
@Composable
fun MeasureSummaryPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            MeasureSummary(
                questionnaire = DailyScoredQuestionnaire.Stress,
                score = 27,
                onClick = {},
                heightSize = WindowHeightSizeClass.Compact,
                widthSize = WindowWidthSizeClass.Compact,
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun MeasureSummaryWithPagerPreview() {
    BienestarEmocionalTheme {
        Surface {
            MeasureSummary(
                questionnaire = DailyScoredQuestionnaire.Stress,
                score = 27,
                pagerState = rememberPagerState(),
                onClick = {},
                heightSize = WindowHeightSizeClass.Compact,
                widthSize = WindowWidthSizeClass.Compact,
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun MeasureSummaryWithPagerPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            MeasureSummary(
                questionnaire = DailyScoredQuestionnaire.Stress,
                score = 27,
                pagerState = rememberPagerState(),
                onClick = {},
                heightSize = WindowHeightSizeClass.Compact,
                widthSize = WindowWidthSizeClass.Compact,
            )
        }
    }
}