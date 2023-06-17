package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.daily.DailyNotScoredQuestionnaireDrawable

@Destination
@Composable
fun DailySymptomsScreen(
    navController: NavController,
    entityId: Long,
    viewModel : DailySymptomsViewModel = hiltViewModel(),
)
{
    val questionnaire = DailyNotScoredQuestionnaireDrawable.Symptoms

    val state by viewModel.state.collectAsStateWithLifecycle()

    QuestionnaireStringAnswersScreen(
        state = state,
        questionnaire = questionnaire,
        title = stringResource(id = R.string.daily_symptoms_label),
        answerSelected = viewModel::answerSelected,
        answersRemaining = viewModel::answersRemaining,
        onAnswer = { question, answer -> viewModel.onAnswer(question,answer) },
        onInProgress = viewModel::onInProgress,
        onSkippingAttempt = viewModel::onSkippingAttempt,
        onSkipped = viewModel::onSkipped,
        onFinishAttempt = viewModel::onFinishAttempt,
        onSummary = viewModel::onSummary,
        onExit = {
            navController.previousBackStackEntry?.savedStateHandle?.set("finished", true)
            navController.popBackStack()
        }
    )
}