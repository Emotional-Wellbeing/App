package es.upm.bienestaremocional.app.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.ui.component.BackHandlerMinimizeApp
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.ui.screens.destinations.MeasureScreenDestination
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.responsive.computeWindowHeightSize
import es.upm.bienestaremocional.core.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Home Screen has the latest news about user and is displayed when splash ends
 */
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator,
               viewModel: HomeViewModel = hiltViewModel())
{
    BackHandlerMinimizeApp(LocalContext.current)

    HomeScreen(navigator = navigator,
        questionnairesToShow = viewModel.questionnaires,
        widthSize = computeWindowWidthSize(),
        heightSize = computeWindowHeightSize(),
        getStressScore = viewModel::getStressScore,
        getDepressionScore = viewModel::getDepressionScore,
        getLonelinessScore = viewModel::getLonelinessScore
    )
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreen(
    navigator: DestinationsNavigator,
    questionnairesToShow : List<Questionnaire>,
    widthSize : WindowWidthSizeClass,
    heightSize : WindowHeightSizeClass,
    getStressScore: suspend () -> Int?,
    getDepressionScore : suspend () -> Int?,
    getLonelinessScore : suspend () -> Int?
)
{
    var stressScore : Int? by remember { mutableStateOf(null) }
    var depressionScore : Int? by remember { mutableStateOf(null) }
    var lonelinesssScore : Int? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit)
    {
        questionnairesToShow.forEach { questionnaire ->
            when(questionnaire)
            {
                Questionnaire.PSS -> stressScore = getStressScore()
                Questionnaire.PHQ -> depressionScore = getDepressionScore()
                Questionnaire.UCLA -> lonelinesssScore = getLonelinessScore()
            }
        }
    }

    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.HomeScreen,
        label = R.string.app_name)
    {
        //TODO check landscape
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        )
        {
            if (questionnairesToShow.size > 1)
            {
                val pagerState = rememberPagerState()

                HorizontalPager(
                    count = questionnairesToShow.size,
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->

                    val score = when (questionnairesToShow[page])
                    {
                        Questionnaire.PSS -> stressScore
                        Questionnaire.PHQ -> depressionScore
                        Questionnaire.UCLA -> lonelinesssScore
                    }

                    val onClick = {
                        navigator.navigate(MeasureScreenDestination(
                            questionnaire = questionnairesToShow[page]))
                    }

                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    )
                    {
                        MeasureSummary(questionnaire = questionnairesToShow[page],
                            score = score,
                            pagerState = pagerState,
                            onClick = onClick,
                            widthSize = widthSize,
                            heightSize = heightSize,
                        )

                    }
                }
            }
            else if (questionnairesToShow.size == 1)
            {
                val score = when (questionnairesToShow[0]) {
                    Questionnaire.PSS -> stressScore
                    Questionnaire.PHQ -> depressionScore
                    Questionnaire.UCLA -> lonelinesssScore
                }

                val onClick = {
                    navigator.navigate(MeasureScreenDestination(
                        questionnaire = questionnairesToShow[0]))
                }

                MeasureSummary(
                    questionnaire = questionnairesToShow[0],
                    score = score,
                    onClick = onClick,
                    widthSize = widthSize,
                    heightSize = heightSize,
                    heightFraction = 0.95f
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun HomeScreenOneQuestionnaireCompactPreview()
{
    BienestarEmocionalTheme{
        HomeScreen(navigator = EmptyDestinationsNavigator,
            questionnairesToShow = listOf(Questionnaire.PSS),
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun HomeScreenOneQuestionnaireCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        HomeScreen(navigator = EmptyDestinationsNavigator,
            questionnairesToShow = listOf(Questionnaire.PSS),
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun HomeScreenNoQuestionnaireCompactPreview()
{
    BienestarEmocionalTheme{
        HomeScreen(navigator = EmptyDestinationsNavigator,
            questionnairesToShow = listOf(),
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun HomeScreenNoQuestionnaireCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        HomeScreen(navigator = EmptyDestinationsNavigator,
            questionnairesToShow = listOf(),
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun HomeScreenAllQuestionnairesCompactPreview()
{
    BienestarEmocionalTheme{
        HomeScreen(navigator = EmptyDestinationsNavigator,
            questionnairesToShow = Questionnaire.getMandatory() + Questionnaire.getOptional(),
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun HomeScreenAllQuestionnairesCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        HomeScreen(navigator = EmptyDestinationsNavigator,
            questionnairesToShow = Questionnaire.getMandatory() + Questionnaire.getOptional(),
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}