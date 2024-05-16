@file:Suppress("KotlinDeprecation")

package es.upm.bienestaremocional.ui.screens.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.domain.processing.NullableChartRecord
import es.upm.bienestaremocional.ui.component.DoubleMeasureStatus
import es.upm.bienestaremocional.ui.component.chart.ActualWeekChart

@Composable
fun RemoteMeasure(
    questionnaire: DailyScoredQuestionnaire,
    pagerState: PagerState,
    yesterdayScore: Int?,
    lastSevenDaysScore: Int?,
    currentWeekScores: List<NullableChartRecord>,
    widthSize: WindowWidthSizeClass,
) {
    val titleStyle = when (widthSize) {
        WindowWidthSizeClass.Compact -> MaterialTheme.typography.titleSmall
        WindowWidthSizeClass.Medium -> MaterialTheme.typography.titleMedium
        WindowWidthSizeClass.Expanded -> MaterialTheme.typography.titleLarge
        else -> MaterialTheme.typography.titleSmall
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = stringResource(id = questionnaire.measure.measureRes),
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            style = titleStyle
        )
        Spacer(modifier = Modifier.height(16.dp))
        RemoteMeasure(
            questionnaire = questionnaire,
            yesterdayScore = yesterdayScore,
            lastSevenDaysScore = lastSevenDaysScore,
            currentWeekScores = currentWeekScores,
            widthSize = widthSize,
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
fun RemoteMeasure(
    questionnaire: DailyScoredQuestionnaire,
    yesterdayScore: Int?,
    lastSevenDaysScore: Int?,
    currentWeekScores: List<NullableChartRecord>,
    widthSize: WindowWidthSizeClass,
    heightFraction: Float = 1f,
) {
    Column(
        modifier = Modifier.fillMaxHeight(heightFraction),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    )
    {
        Box(modifier = Modifier.weight(0.4f))
        {
            BoxWithConstraints()
            {
                DoubleMeasureStatus(
                    data = Pair(yesterdayScore, lastSevenDaysScore),
                    headers = Pair(
                        stringResource(R.string.yesterday),
                        stringResource(R.string.last_seven_days)
                    ),
                    questionnaire = questionnaire,
                    height = maxHeight,
                    widthSize = widthSize,
                    showHeadline = false,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    indicatorContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            }

        }
        Box(
            modifier = Modifier
                .weight(0.6f)
                .padding(16.dp)
        )
        {
            if (currentWeekScores.isNotEmpty() && currentWeekScores.any { it.score != null })
                ActualWeekChart(questionnaire, currentWeekScores)
            else
                Text(stringResource(id = R.string.no_data_to_display))
        }
    }
}