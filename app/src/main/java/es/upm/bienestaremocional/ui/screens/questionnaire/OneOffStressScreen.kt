package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffQuestionnaireDrawable
import es.upm.bienestaremocional.ui.component.OneOffStressStatus
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize

@Destination
@Composable
fun OneOffStressScreen(
    navController: NavController,
    entityId: Long,
    questionnaireIndex : Int = 0,
    questionnaireSize : Int = 1,
    viewModel : OneOffStressViewModel = hiltViewModel(),
)
{
    val questionnaire = OneOffQuestionnaireDrawable.Stress

    val state by viewModel.state.collectAsStateWithLifecycle()

    val summaryContent : @Composable (Int, WindowWidthSizeClass) -> Unit  = { score, widthSize ->
        OneOffStressStatus(
            data = score,
            widthSize = widthSize,
            indicatorColor = MaterialTheme.colorScheme.primary,
            indicatorContainerColor = MaterialTheme.colorScheme.primaryContainer,
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            showAdvice = true,
        )
    }

    QuestionnaireStringAnswersScreen(
        state = state,
        questionnaire = questionnaire,
        widthSize = computeWindowWidthSize(),
        title = "${stringResource(R.string.questionnaire)} ${questionnaireIndex + 1}/${questionnaireSize} ${stringResource(questionnaire.measureRes)}",
        answerSelected = viewModel::answerSelected,
        answersRemaining = viewModel::answersRemaining,
        getScore = viewModel::score,
        onAnswer = { question, answer -> viewModel.onAnswer(question,answer) },
        onInProgress = viewModel::onInProgress,
        onSkippingAttempt = viewModel::onSkippingAttempt,
        onSkipped = viewModel::onSkipped,
        onFinishAttempt = viewModel::onFinishAttempt,
        onSummary = viewModel::onSummary,
        onExit = {
            navController.previousBackStackEntry?.savedStateHandle?.set("finished", true)
            navController.popBackStack()
        },
        summaryContent = summaryContent
    )
}