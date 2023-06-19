package es.upm.bienestaremocional.ui.screens.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.domain.processing.NullableChartRecord
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize

@Destination
@Composable
fun CommunityScreen(
    navigator: DestinationsNavigator,
    viewModel: CommunityViewModel = hiltViewModel()
) {
    val stressYesterdayScore by viewModel.stressYesterdayScore.collectAsStateWithLifecycle()
    val stressLastSevenDaysScore by viewModel.stressLastSevenDaysScore.collectAsStateWithLifecycle()
    val stressCurrentWeekScores by viewModel.stressCurrentWeekScores.collectAsStateWithLifecycle()

    val depressionYesterdayScore by viewModel.depressionYesterdayScore.collectAsStateWithLifecycle()
    val depressionLastSevenDaysScore by viewModel.depressionLastSevenDaysScore.collectAsStateWithLifecycle()
    val depressionCurrentWeekScores by viewModel.depressionCurrentWeekScores.collectAsStateWithLifecycle()

    val lonelinessYesterdayScore by viewModel.lonelinessYesterdayScore.collectAsStateWithLifecycle()
    val lonelinessLastSevenDaysScore by viewModel.lonelinessLastSevenDaysScore.collectAsStateWithLifecycle()
    val lonelinessCurrentWeekScores by viewModel.lonelinessCurrentWeekScores.collectAsStateWithLifecycle()


    CommunityScreen(
        navigator = navigator,
        questionnairesToShow = DailyScoredQuestionnaire.values().toList(),
        stressYesterdayScore = stressYesterdayScore,
        stressLastSevenDaysScore = stressLastSevenDaysScore,
        stressCurrentWeekScores = stressCurrentWeekScores,
        depressionYesterdayScore = depressionYesterdayScore,
        depressionLastSevenDaysScore = depressionLastSevenDaysScore,
        depressionCurrentWeekScores = depressionCurrentWeekScores,
        lonelinessYesterdayScore = lonelinessYesterdayScore,
        lonelinessLastSevenDaysScore = lonelinessLastSevenDaysScore,
        lonelinessCurrentWeekScores = lonelinessCurrentWeekScores,
        widthSize = computeWindowWidthSize()
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CommunityScreen(
    navigator: DestinationsNavigator,
    questionnairesToShow: List<DailyScoredQuestionnaire>,
    stressYesterdayScore: Int?,
    stressLastSevenDaysScore: Int?,
    stressCurrentWeekScores: List<NullableChartRecord>,
    depressionYesterdayScore: Int?,
    depressionLastSevenDaysScore: Int?,
    depressionCurrentWeekScores: List<NullableChartRecord>,
    lonelinessYesterdayScore: Int?,
    lonelinessLastSevenDaysScore: Int?,
    lonelinessCurrentWeekScores: List<NullableChartRecord>,
    widthSize: WindowWidthSizeClass
) {
    AppBasicScreen(
        navigator = navigator,
        entrySelected = BottomBarDestination.CommunityScreen,
        snackbarHostState = null,
        label = R.string.community_screen_label
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        )
        {
            val pagerState = rememberPagerState()

            HorizontalPager(
                count = questionnairesToShow.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            )
            { page ->

                val yesterdayScore = when (questionnairesToShow[page]) {
                    DailyScoredQuestionnaire.Stress -> stressYesterdayScore
                    DailyScoredQuestionnaire.Depression -> depressionYesterdayScore
                    DailyScoredQuestionnaire.Loneliness -> lonelinessYesterdayScore
                }

                val lastSevenDaysScore = when (questionnairesToShow[page]) {
                    DailyScoredQuestionnaire.Stress -> stressLastSevenDaysScore
                    DailyScoredQuestionnaire.Depression -> depressionLastSevenDaysScore
                    DailyScoredQuestionnaire.Loneliness -> lonelinessLastSevenDaysScore
                }

                val currentWeekScores = when (questionnairesToShow[page]) {
                    DailyScoredQuestionnaire.Stress -> stressCurrentWeekScores
                    DailyScoredQuestionnaire.Depression -> depressionCurrentWeekScores
                    DailyScoredQuestionnaire.Loneliness -> lonelinessCurrentWeekScores
                }

                RemoteMeasure(
                    questionnaire = questionnairesToShow[page],
                    pagerState = pagerState,
                    yesterdayScore = yesterdayScore,
                    lastSevenDaysScore = lastSevenDaysScore,
                    currentWeekScores = currentWeekScores,
                    widthSize = widthSize
                )
            }
        }
    }
}