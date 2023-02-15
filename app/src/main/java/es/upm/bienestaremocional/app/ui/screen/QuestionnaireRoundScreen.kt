package es.upm.bienestaremocional.app.ui.screen

import android.util.Log
import androidx.compose.runtime.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.ui.state.QuestionnaireRoundState
import es.upm.bienestaremocional.app.ui.viewmodel.QuestionnaireRoundViewModel

/**
 * Plots questionnaires
 */
@OptIn(ExperimentalPagerApi::class)
@Destination(
    deepLinks = [
        DeepLink(
            uriPattern = "be://questionnaire"
        )
    ]
)
@Composable
fun QuestionnaireRoundScreen(navigator: DestinationsNavigator,
                             viewModel: QuestionnaireRoundViewModel
)
{
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState()
    when(state)
    {
        QuestionnaireRoundState.Init ->
        {
            Log.d(MainApplication.logTag,"Init")
            LaunchedEffect(Unit)
            {
                viewModel.initAction()
            }
        }
        QuestionnaireRoundState.PreShow -> {
            Log.d(MainApplication.logTag,"Preshow")
            LaunchedEffect(true)
            {
                //if we call this previous to first page, it will never end
                pagerState.scrollToPage(0)
                viewModel.updateState(QuestionnaireRoundState.Show)
            }
        }
        QuestionnaireRoundState.Show -> {
            Log.d(MainApplication.logTag,"Show")
            QuestionnaireScreen(questionnaireData = viewModel.getQuestionnaireData(),
                pagerState = pagerState,
                onFinish = { viewModel.onFinish() },
                onSkip = { viewModel.onSkip() })
            }
        QuestionnaireRoundState.Finishing -> {//TODO add insert database
            Log.d(MainApplication.logTag,"Finishing")
            viewModel.updateState(QuestionnaireRoundState.Finished) }
        QuestionnaireRoundState.Finished -> {navigator.popBackStack()}
    }
}




