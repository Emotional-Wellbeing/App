package es.upm.bienestaremocional.ui.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.AppConstants
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.remote.RemoteAPI
import es.upm.bienestaremocional.data.usage.Usage
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.component.BackHandlerMinimizeApp
import es.upm.bienestaremocional.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.destinations.MeasureScreenDestination
import es.upm.bienestaremocional.destinations.UncompletedQuestionnairesScreenDestination
import es.upm.bienestaremocional.di.AppModule.provideAppInfo
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepository
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepositoryImpl
import es.upm.bienestaremocional.ui.responsive.computeWindowHeightSize
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Home Screen has the latest news about user and is displayed when splash ends
 */
@RequiresApi(Build.VERSION_CODES.Q)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator,
               viewModel: HomeViewModel = hiltViewModel())
{
    BackHandlerMinimizeApp(LocalContext.current)

    val uncompletedQuestionnaires by viewModel.uncompletedQuestionnaires.collectAsStateWithLifecycle()

    HomeScreen(navigator = navigator,
        questionnairesToShow = viewModel.questionnaires,
        widthSize = computeWindowWidthSize(),
        heightSize = computeWindowHeightSize(),
        uncompletedQuestionnaires = uncompletedQuestionnaires,
        getStressScore = viewModel::getStressScore,
        getDepressionScore = viewModel::getDepressionScore,
        getLonelinessScore = viewModel::getLonelinessScore
    )
}


@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreen(
    navigator: DestinationsNavigator,
    questionnairesToShow : List<DailyScoredQuestionnaire>,
    widthSize : WindowWidthSizeClass,
    heightSize : WindowHeightSizeClass,
    uncompletedQuestionnaires : Boolean,
    getStressScore: suspend () -> Int?,
    getDepressionScore : suspend () -> Int?,
    getLonelinessScore : suspend () -> Int?
)
{
    var stressScore : Int? by remember { mutableStateOf(null) }
    var depressionScore : Int? by remember { mutableStateOf(null) }
    var lonelinesssScore : Int? by remember { mutableStateOf(null) }

    val snackbarHostState = remember { SnackbarHostState() }

    val uncompletedQuestionnairesAdviceText  = stringResource(R.string.uncompleted_questionnaires_advice)
    val reviewText = stringResource(R.string.review)

    //usage info
    val coroutineScope = rememberCoroutineScope()

    val remoteAPI: RemoteAPI = Retrofit.Builder()
        .baseUrl(AppConstants.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RemoteAPI::class.java)

    val logTag= "logTag"
    val remoteRepository: RemoteRepository = RemoteRepositoryImpl(logTag,remoteAPI)

    val usage = Usage()
    val listApps = usage.getAppUsage(LocalContext.current)

    val appInfo : AppInfo = provideAppInfo(LocalContext.current)

    coroutineScope.launch {
        val userId = appInfo.getUserID()
        val message = "{ \"userId\": \"$userId\", \"databg\": {\"UsageInfo\": {$listApps}}}"
        val success = remoteRepository.postBackgroundData(message)
        if (success == true)
            println("Inserted usage info")
    }


    LaunchedEffect(Unit)
    {
        questionnairesToShow.forEach { questionnaire ->
            when(questionnaire)
            {
                DailyScoredQuestionnaire.Stress -> stressScore = getStressScore()
                DailyScoredQuestionnaire.Depression -> depressionScore = getDepressionScore()
                DailyScoredQuestionnaire.Loneliness -> lonelinesssScore = getLonelinessScore()
            }
        }
    }
    LaunchedEffect(uncompletedQuestionnaires)
    {
        if (uncompletedQuestionnaires)
        {
            showQuestionnaireAlert(snackbarHostState = snackbarHostState,
                uncompletedQuestionnairesAdviceText,
                reviewText)
            {
                navigator.navigate(UncompletedQuestionnairesScreenDestination)
            }
        }
    }

    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.HomeScreen,
        snackbarHostState = snackbarHostState,
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
                        DailyScoredQuestionnaire.Stress -> stressScore
                        DailyScoredQuestionnaire.Depression -> depressionScore
                        DailyScoredQuestionnaire.Loneliness -> lonelinesssScore
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
                    DailyScoredQuestionnaire.Stress -> stressScore
                    DailyScoredQuestionnaire.Depression -> depressionScore
                    DailyScoredQuestionnaire.Loneliness -> lonelinesssScore
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

private suspend fun showQuestionnaireAlert(snackbarHostState: SnackbarHostState,
                                           message : String,
                                           actionLabel : String,
                                           onClick : () -> Unit
)
{
    val result = snackbarHostState.showSnackbar(message = message,
        actionLabel = actionLabel,
        withDismissAction = true,
        duration = SnackbarDuration.Indefinite)
    if (result === SnackbarResult.ActionPerformed)
        onClick()
}



@RequiresApi(Build.VERSION_CODES.Q)
@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun HomeScreenOneQuestionnaireCompactPreview()
{
    BienestarEmocionalTheme{
        HomeScreen(navigator = EmptyDestinationsNavigator,
            questionnairesToShow = listOf(DailyScoredQuestionnaire.Stress),
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            uncompletedQuestionnaires = false,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
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
            questionnairesToShow = listOf(DailyScoredQuestionnaire.Stress),
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            uncompletedQuestionnaires = false,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
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
            uncompletedQuestionnaires = false,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
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
            uncompletedQuestionnaires = false,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun HomeScreenAllQuestionnairesCompactPreview()
{
    BienestarEmocionalTheme{
        HomeScreen(navigator = EmptyDestinationsNavigator,
            questionnairesToShow = DailyScoredQuestionnaire.values().toList(),
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            uncompletedQuestionnaires = false,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
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
            questionnairesToShow = DailyScoredQuestionnaire.values().toList(),
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            uncompletedQuestionnaires = false,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun HomeScreenAllQuestionnairesShowUncompletedCompactPreview()
{
    BienestarEmocionalTheme{
        HomeScreen(navigator = EmptyDestinationsNavigator,
            questionnairesToShow = DailyScoredQuestionnaire.values().toList(),
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            uncompletedQuestionnaires = true,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun HomeScreenAllQuestionnairesShowUncompletedCompactPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        HomeScreen(navigator = EmptyDestinationsNavigator,
            questionnairesToShow = DailyScoredQuestionnaire.values().toList(),
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            uncompletedQuestionnaires = true,
            getStressScore = { 27 },
            getDepressionScore = { 14 },
            getLonelinessScore = { 33 }
        )
    }
}