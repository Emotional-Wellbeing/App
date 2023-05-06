package es.upm.bienestaremocional.app.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.ui.component.BackHandlerMinimizeApp
import es.upm.bienestaremocional.app.ui.component.DepressionStatus
import es.upm.bienestaremocional.app.ui.component.LonelinessStatus
import es.upm.bienestaremocional.app.ui.component.StressStatus
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.BasicCard
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
        widthSize = computeWindowWidthSize(),
        questionnairesToShow = viewModel.questionnaires,
        getStressScore = viewModel::getStressScore,
        getDepressionScore = viewModel::getDepressionScore,
        getLonelinessScore = viewModel::getLonelinessScore
    )
}


@Composable
private fun HomeScreen(
    navigator: DestinationsNavigator,
    widthSize : WindowWidthSizeClass,
    questionnairesToShow : Set<Questionnaire>,
    getStressScore: suspend () -> Float?,
    getDepressionScore : suspend () -> Float?,
    getLonelinessScore : suspend () -> Float?
)
{
    var stressScore : Float? by remember { mutableStateOf(null) }
    var depressionScore : Float? by remember { mutableStateOf(null) }
    var lonelinesssScore : Float? by remember { mutableStateOf(null) }

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
        //https://developer.android.com/jetpack/compose/gestures for verticalScroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        )
        {

            BasicCard{
                Text("Message placeholder")
            }

            questionnairesToShow.forEach { questionnaire ->
                when(questionnaire)
                {
                    Questionnaire.PSS -> StressStatus(data = stressScore, widthSize = widthSize)
                    Questionnaire.PHQ -> DepressionStatus(data = depressionScore, widthSize = widthSize)
                    Questionnaire.UCLA -> LonelinessStatus(data = lonelinesssScore, widthSize = widthSize)
                }
            }

            BasicCard{
                Text("Feedback placeholder")
            }

            BasicCard{
                Text("Last week stats placeholder")
            }
        }
    }
}


@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun HomeScreenCompactPreview()
{
    BienestarEmocionalTheme{
        HomeScreen(navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Compact,
            questionnairesToShow = Questionnaire.getMandatory().toSet() + Questionnaire.getOptional(),
            getStressScore = { 27f },
            getDepressionScore = { 14f },
            getLonelinessScore = { 33f }
        )
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun HomeScreenCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        HomeScreen(navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Compact,
            questionnairesToShow = Questionnaire.getMandatory().toSet() + Questionnaire.getOptional(),
            getStressScore = { 27f },
            getDepressionScore = { 14f },
            getLonelinessScore = { 33f }
        )
    }
}