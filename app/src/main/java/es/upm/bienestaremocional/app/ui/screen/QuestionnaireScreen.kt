package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.ui.state.QuestionnaireState
import es.upm.bienestaremocional.app.ui.viewmodel.QuestionnaireData
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun QuestionnaireScreen(questionnaireData: QuestionnaireData,
                        pagerState: PagerState,
                        onFinish: () -> Unit,
                        onSkip: () -> Unit
)
{

    DrawQuestionnaire(
        state = questionnaireData.state,
        pagerState = pagerState,
        questionnaire = questionnaireData.questionnaire,
        title = questionnaireData.title(),
        answerSelected = {index -> questionnaireData.getAnswer(index)},
        answersRemaining = { questionnaireData.answersRemaining() },
        getScore = {questionnaireData.getScore()},
        onAnswer = { question, answer ->
            if (questionnaireData.getAnswer(question) != answer)
                questionnaireData.setAnswer(question,answer)
            else
                questionnaireData.removeAnswer(question)
        },
        onFinish = onFinish,
        onSkip = onSkip)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DrawQuestionnaire(state: MutableState<QuestionnaireState>,
                      pagerState: PagerState,
                      questionnaire: Questionnaire,
                      title: String,
                      answerSelected: (Int) -> Int?,
                      answersRemaining: () -> List<Int>,
                      getScore : () -> Int?,
                      onAnswer: (Int,Int) -> Unit,
                      onFinish: () -> Unit,
                      onSkip: () -> Unit,
)
{
    val questions = stringArrayResource(questionnaire.questionRes)
    val answers = stringArrayResource(questionnaire.answerRes)

    if (state.value !is QuestionnaireState.Summary || state.value !is QuestionnaireState.Finished)
    {
        Surface()
        {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp))
            {
                //header
                Column(modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    )
                    {
                        Text(title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f))
                        IconButton(onClick = {state.value = QuestionnaireState.Skipping})
                        {
                            Icon(Icons.Filled.Close, contentDescription = "Finish")
                        }
                    }
                }

                //internal page
                HorizontalPager(
                    count = questionnaire.numberOfQuestions,
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                )
                {
                        page -> QuestionnaireInternalPage(question = questions[page],
                    answers = answers,
                    answerSelectedPrevious = answerSelected(page),
                    pagerState = pagerState,
                    onAnswer = {answer -> onAnswer(page,answer)},
                    onExit = {state.value = QuestionnaireState.Skipping},
                    onFinish = {state.value = QuestionnaireState.FinishingQuestions})
                }
            }
        }
    }
    when(state.value)
    {
        QuestionnaireState.Skipping ->
        {
            ExitDialog(onDismiss = { state.value = QuestionnaireState.InProgress},
                onConfirm = { state.value = QuestionnaireState.Skipped })
        }
        QuestionnaireState.Skipped -> {onSkip()}
        QuestionnaireState.FinishingQuestions ->
        {
            val listAnswersRemaining = answersRemaining()
            if (listAnswersRemaining.isEmpty())
                state.value = QuestionnaireState.Summary
            else
                AnswersRemainingDialog(answersRemaining = listAnswersRemaining) {
                    state.value = QuestionnaireState.InProgress
                }
        }
        QuestionnaireState.Summary ->
        {
            Summary(score = getScore()!!, questionnaire = questionnaire) {
                state.value = QuestionnaireState.Finished
            }}
        QuestionnaireState.Finished -> {onFinish()}
        QuestionnaireState.InProgress -> {}
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun QuestionnaireInternalPage(question: String,
                                      answers : Array<String>,
                                      answerSelectedPrevious : Int?,
                                      pagerState : PagerState,
                                      onAnswer: (Int) -> Unit,
                                      onExit: () -> Unit,
                                      onFinish: () -> Unit)
{
    val corrutineScope = rememberCoroutineScope()
    val answerSelected = remember { mutableStateOf(answerSelectedPrevious) }

    //content
    Column(modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        //q&a
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            //question
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text("${(pagerState.currentPage + 1)}. $question", textAlign = TextAlign.Justify)
            }

            //answer
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                answers.forEachIndexed { index, answer ->
                    Spacer(modifier = Modifier.size(16.dp))
                    OptionCard(answer,
                        selected = index == answerSelected.value,
                        onClick = {
                            onAnswer(index)
                            if (answerSelected.value != index)
                                answerSelected.value = index
                            else
                                answerSelected.value = null
                        })
                }
            }

        }

        //footer
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround)
        {
            //footer
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically)
            {
                if (pagerState.currentPage == 0)
                {
                    TextButton(onClick = onExit)
                    {
                        Text(text = stringResource(id = R.string.exit))
                    }
                }
                else
                {
                    TextButton(onClick = {
                        corrutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage - 1,
                                pagerState.currentPageOffset
                            )
                        }
                    })
                    {
                        Text(text = stringResource(id = R.string.back))
                    }
                }

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = MaterialTheme.colorScheme.primary,
                    indicatorWidth = if(pagerState.pageCount > 12) 4.dp else 8.dp
                )
                if (pagerState.currentPage == pagerState.pageCount - 1) {
                    TextButton(onClick = onFinish)
                    {
                        Text(text = stringResource(id = R.string.finish))
                    }
                }
                else
                {
                    TextButton(onClick = {
                        corrutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1,
                                pagerState.currentPageOffset
                            )
                        }
                    })
                    {
                        Text(text = stringResource(id = R.string.next))
                    }
                }
            }
        }
    }
}

@Composable
private fun ExitDialog(onDismiss : () -> Unit, onConfirm : () -> Unit)
{
    AlertDialog(onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.accept))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.back))
            }
        },
        title = {
            //todo hacer más chico
            Text(stringResource(R.string.exit_questionnaire))
        },
        text = {
            Text(stringResource(R.string.sure_skip_questionnaire))
        })
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AnswersRemainingDialog(answersRemaining: List<Int>,
                                   onDismiss : () -> Unit)
{
    val textToShow = pluralStringResource(
        R.plurals.number_of_questions_left,
        answersRemaining.size,
        answersRemaining.map { it+1 }.joinToString())

    AlertDialog(onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.ok))
            }
        },
        title = {
            //todo hacer más chico
            Text(stringResource(R.string.questionnaire_not_completed))
        },
        text = {
            Text(textToShow)
        })
}

@Composable
private fun OptionCard(text : String,
                       selected : Boolean,
                       onClick : () -> Unit)
{
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor =
        if(selected)
            MaterialTheme.colorScheme.inversePrimary
        else
            MaterialTheme.colorScheme.surfaceVariant
        )
    )
    {
        Text(text,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
    }
}

@Composable
private fun Summary(score : Int,
                    questionnaire: Questionnaire,
                    onSucess : () -> Unit)
{
    val previousLabel = stringResource(R.string.questionnaire)
    val scoreLabel = stringResource(R.string.score)
    val questionnaireLabel = stringResource(questionnaire.labelRes)

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("$previousLabel $questionnaireLabel")
            Text("$scoreLabel: $score")
            Button(onClick = onSucess ) {
                Text("OK")
            }
        }

    }
}


@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun QuestionnaireScreenPreview()
{
    val pagerState = rememberPagerState()
    BienestarEmocionalTheme {
        QuestionnaireScreen(
            questionnaireData = QuestionnaireData(Questionnaire.UCLA),
            pagerState = pagerState,
            onFinish = {},
            onSkip = {})
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun QuestionnaireScreenPreviewDarkTheme()
{
    val pagerState = rememberPagerState()
    BienestarEmocionalTheme(darkTheme = true) {
        QuestionnaireScreen(
            questionnaireData = QuestionnaireData(Questionnaire.PSS),
            pagerState = pagerState,
            onFinish = {},
            onSkip = {})
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview(showBackground = true)
fun QuestionnaireInternalPagePreview()
{
    val questionnaire = Questionnaire.PHQ
    val questions = stringArrayResource(questionnaire.questionRes)
    val possibleAnswers = stringArrayResource(questionnaire.answerRes)
    val pagerState = rememberPagerState()

    val answers : Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)

    BienestarEmocionalTheme {
        Surface {
            QuestionnaireInternalPage(question = questions[0],
                answers = possibleAnswers,
                answerSelectedPrevious = null,
                pagerState = pagerState,
                onAnswer = { answer -> answers[pagerState.currentPage] = answer },
                onExit = {},
                onFinish = {})
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview(showBackground = true)
fun QuestionnaireInternalPageDarkThemePreview()
{
    val questionnaire = Questionnaire.PHQ
    val questions = stringArrayResource(questionnaire.questionRes)
    val possibleAnswers = stringArrayResource(questionnaire.answerRes)
    val pagerState = rememberPagerState()

    val answers : Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            QuestionnaireInternalPage(question = questions[0],
                answers = possibleAnswers,
                answerSelectedPrevious = null,
                pagerState = pagerState,
                onAnswer = {answer -> answers[pagerState.currentPage] = answer},
                onExit = {},
                onFinish = {})
        }
    }
}

@Composable
@Preview
fun ExitDialogPreview()
{
    BienestarEmocionalTheme {
        ExitDialog(onConfirm = {}, onDismiss = {})
    }
}

@Composable
@Preview
fun ExitDialogDarkThemePreview()
{
    BienestarEmocionalTheme(darkTheme = true) {
        ExitDialog(onConfirm = {}, onDismiss = {})
    }
}

@Composable
@Preview
fun AnswersRemainingDialogPreview()
{
    BienestarEmocionalTheme {
        AnswersRemainingDialog(answersRemaining = listOf(1,2,4,7,8)) {}
    }
}

@Composable
@Preview
fun AnswersRemainingDialogDarkThemePreview()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AnswersRemainingDialog(answersRemaining = listOf(1,2,4,7)) {}
    }
}

@Composable
@Preview
fun OptionCardNotSelectedPreview()
{
    val question = stringArrayResource(id = R.array.four_answers_questionnaire)[0]
    BienestarEmocionalTheme {
        OptionCard(
            text = question,
            selected = false,
            onClick = {})
    }
}

@Composable
@Preview
fun OptionCardNotSelectedDarkThemePreview()
{
    val question = stringArrayResource(id = R.array.four_answers_questionnaire)[0]
    BienestarEmocionalTheme(darkTheme = true) {
        OptionCard(
            text = question,
            selected = false,
            onClick = {})
    }
}

@Composable
@Preview
fun OptionCardSelectedPreview()
{
    val question = stringArrayResource(id = R.array.four_answers_questionnaire)[0]
    BienestarEmocionalTheme {
        OptionCard(
            text = question,
            selected = true,
            onClick = {})
    }
}

@Composable
@Preview
fun OptionCardSelectedDarkThemePreview()
{
    val question = stringArrayResource(id = R.array.four_answers_questionnaire)[0]
    BienestarEmocionalTheme(darkTheme = true) {
        OptionCard(
            text = question,
            selected = true,
            onClick = {})
    }
}

@Composable
@Preview(showBackground = true)
fun SummaryPreview()
{
    BienestarEmocionalTheme {
        Summary(score = 10, questionnaire = Questionnaire.PHQ, onSucess = {})
    }
}

@Composable
@Preview(showBackground = true)
fun SummaryDarkThemePreview()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Summary(score = 10, questionnaire = Questionnaire.PHQ, onSucess = {})
    }
}