package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.Measure
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import es.upm.bienestaremocional.data.questionnaire.Level
import es.upm.bienestaremocional.data.questionnaire.daily.DailyNotScoredQuestionnaireDrawable
import es.upm.bienestaremocional.domain.processing.levelToAdvice
import es.upm.bienestaremocional.ui.component.ShowAdviceHeadline
import es.upm.bienestaremocional.ui.component.questionnaire.ExitDialog
import es.upm.bienestaremocional.ui.component.questionnaire.QuestionnaireLayout
import es.upm.bienestaremocional.ui.component.questionnaire.StringAnswer
import es.upm.bienestaremocional.ui.component.questionnaire.Summary
import kotlinx.coroutines.launch

@Destination
@Composable
fun DailySuicideScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    entityId: Long,
    moment: DailyRound.Moment,
    questionnaireIndex: Int = 0,
    questionnaireSize: Int = 1,
    viewModel: DailySuicideViewModel = hiltViewModel(),
) {
    val questionnaire = if (moment == DailyRound.Moment.Morning)
        DailyNotScoredQuestionnaireDrawable.MorningSuicide
    else
        DailyNotScoredQuestionnaireDrawable.NightSuicide

    val questions = stringArrayResource(questionnaire.questionRes)
    val answers = questionnaire.answerRes.map { stringArrayResource(it) }

    val coroutineScope = rememberCoroutineScope()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val questionNumber by viewModel.questionNumber.collectAsStateWithLifecycle()

    DailySuicideScreen(
        navigator = navigator,
        state = state,
        questionNumber = questionNumber,
        questions = questions,
        answers = answers,
        title = "${stringResource(R.string.questionnaire)} ${questionnaireIndex + 1}/${questionnaireSize} ${
            stringResource(
                questionnaire.measureRes
            )
        }",
        level = viewModel.level,
        answerSelected = viewModel::answerSelected,
        onAnswer = { question, answer ->
            coroutineScope.launch {
                viewModel.onAnswer(question, answer)
            }
        },
        onInProgress = viewModel::onInProgress,
        onSkippingAttempt = viewModel::onSkippingAttempt,
        onSkipped = viewModel::onSkipped,
        onSummary = viewModel::onSummary,
        onExit = {
            navController.previousBackStackEntry?.savedStateHandle?.set("finished", true)
            navController.popBackStack()
        }
    )
}

@Composable
private fun DailySuicideScreen(
    navigator: DestinationsNavigator,
    state: SuicideScreenState,
    questionNumber: Int,
    questions: Array<String>,
    answers: List<Array<String>>,
    title: String,
    level: Level,
    answerSelected: (Int) -> Int?,
    onAnswer: (Int, Int) -> Unit,
    onInProgress: () -> Unit,
    onSkippingAttempt: () -> Unit,
    onSkipped: () -> Unit,
    onSummary: () -> Unit,
    onExit: () -> Unit,
) {
    val content: @Composable () -> Unit = {
        ShowPage(
            question = questions[questionNumber],
            answers = if (answers.size == 1) answers[0] else answers[questionNumber],
            answerSelectedPrevious = answerSelected(questionNumber),
            onAnswer = { answer -> onAnswer(questionNumber, answer) }
        )
    }

    if (state !is SuicideScreenState.Summary && state !is SuicideScreenState.Finished) {
        QuestionnaireLayout(
            title = title,
            onSkippingAttempt = onSkippingAttempt,
            content = content
        )
    }
    when (state) {
        SuicideScreenState.InProgress -> {}
        SuicideScreenState.SkipAttempt -> {
            ExitDialog(onDismiss = onInProgress, onConfirm = onSkipped)
        }

        SuicideScreenState.Skipped -> onExit()
        SuicideScreenState.Summary -> {
            ShowSummary(
                navigator = navigator,
                level = level,
                onSuccess = onSummary
            )
        }

        SuicideScreenState.Finished -> onExit()
    }
}

@Composable
private fun ShowPage(
    question: String,
    answers: Array<String>,
    answerSelectedPrevious: Int?,
    onAnswer: (Int) -> Unit
) {
    val answerContent = @Composable {
        StringAnswer(
            answers = answers,
            answerSelectedPrevious = answerSelectedPrevious,
            onAnswer = onAnswer
        )
    }


    ShowQuestion(
        question = question,
        answerContent = answerContent
    )
}

@Composable
private fun ShowQuestion(
    question: String,
    answerContent: @Composable (() -> Unit),
) {
    //content
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        //q&a
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            //question
            Text(question, textAlign = TextAlign.Justify)

            //answer
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
            {
                answerContent()
            }
        }
    }
}

@Composable
private fun ShowSummary(
    navigator: DestinationsNavigator,
    level: Level,
    onSuccess: () -> Unit
) {
    val advice = levelToAdvice(level, Measure.Suicide)

    Summary(
        content = {
            advice?.let {
                ShowAdviceHeadline(
                    navigator = navigator,
                    advice = it,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                )
            }
        },
        onSuccess = onSuccess
    )
}