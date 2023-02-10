package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
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
    val pagerState = rememberPagerState()
    when(viewModel.state)
    {
        QuestionnaireRoundState.PreShow -> {
            LaunchedEffect(true)
            {
                pagerState.scrollToPage(0)
                viewModel.state = QuestionnaireRoundState.Show
            }
        }
        QuestionnaireRoundState.Show -> {
            QuestionnaireScreen(questionnaireData = viewModel.getQuestionnaireData(),
                pagerState = pagerState,
                onFinish = { viewModel.onFinish() },
                onSkip = { viewModel.onSkip() })
            }
        QuestionnaireRoundState.Finishing -> {//TODO add insert database
            viewModel.state = QuestionnaireRoundState.Finished }
        QuestionnaireRoundState.Finished -> {navigator.popBackStack()}
    }
}




