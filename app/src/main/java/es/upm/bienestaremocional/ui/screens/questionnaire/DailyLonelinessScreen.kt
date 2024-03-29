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
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaireDrawable
import es.upm.bienestaremocional.ui.component.DailyLonelinessStatus
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize

@Destination
@Composable
fun DailyLonelinessScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    entityId: Long,
    moment: DailyRound.Moment,
    questionnaireIndex: Int = 0,
    questionnaireSize: Int = 1,
    viewModel: DailyLonelinessViewModel = hiltViewModel(),
) {
    val questionnaire = if (moment == DailyRound.Moment.Morning)
        DailyScoredQuestionnaireDrawable.MorningLoneliness
    else
        DailyScoredQuestionnaireDrawable.NightLoneliness

    val state by viewModel.state.collectAsStateWithLifecycle()

    val summaryContent: @Composable (Int, WindowWidthSizeClass) -> Unit = { score, widthSize ->
        DailyLonelinessStatus(
            navigator = navigator,
            data = score,
            widthSize = widthSize,
            indicatorColor = MaterialTheme.colorScheme.primary,
            indicatorContainerColor = MaterialTheme.colorScheme.primaryContainer,
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            showAdvice = true,
        )
    }

    QuestionnaireNumericAnswersScreen(
        state = state,
        questionnaire = questionnaire,
        widthSize = computeWindowWidthSize(),
        title = "${stringResource(R.string.questionnaire)} ${questionnaireIndex + 1}/${questionnaireSize} ${
            stringResource(
                questionnaire.measureRes
            )
        }",
        answerSelected = viewModel::answerSelected,
        answersRemaining = viewModel::answersRemaining,
        getScore = viewModel::score,
        onQuestionLoad = { question -> viewModel.onLoadQuestion(question) },
        onAnswer = { question, answer -> viewModel.onAnswer(question, answer) },
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