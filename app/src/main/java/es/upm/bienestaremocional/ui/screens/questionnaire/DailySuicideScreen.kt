package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import es.upm.bienestaremocional.data.questionnaire.daily.DailyNotScoredQuestionnaireDrawable

@Destination
@Composable
fun DailySuicideScreen(
    navController: NavController,
    entityId: Long,
    moment: DailyRound.Moment,
    questionnaireIndex : Int = 0,
    questionnaireSize : Int = 1,
    viewModel : DailySuicideViewModel = hiltViewModel(),
)
{
    val questionnaire = if (moment == DailyRound.Moment.Morning)
        DailyNotScoredQuestionnaireDrawable.MorningSuicide
    else
        DailyNotScoredQuestionnaireDrawable.NightSuicide

    val state by viewModel.state.collectAsStateWithLifecycle()

    QuestionnaireStringAnswersScreen(
        state = state,
        questionnaire = questionnaire,
        title = "${stringResource(R.string.questionnaire)} ${questionnaireIndex + 1}/${questionnaireSize} ${stringResource(questionnaire.measureRes)}",
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