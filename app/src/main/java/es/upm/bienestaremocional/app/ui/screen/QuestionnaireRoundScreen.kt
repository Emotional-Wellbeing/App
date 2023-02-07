package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.app.ui.state.QuestionnaireRoundState
import es.upm.bienestaremocional.app.ui.viewmodel.QuestionnaireRoundViewModel

/**
 * Plots questionnaires
 */
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
    when(viewModel.state)
    {
        is QuestionnaireRoundState.InProgress -> {
            QuestionnaireScreen(questionnaireData = viewModel.getQuestionnaireData(),
                onFinish = { viewModel.onFinish() },
                onSkip = { viewModel.onSkip() })
            }
        QuestionnaireRoundState.Finishing -> {//TODO add insert database
            viewModel.state = QuestionnaireRoundState.Finished }
        QuestionnaireRoundState.Finished -> {navigator.popBackStack()}
    }
}




