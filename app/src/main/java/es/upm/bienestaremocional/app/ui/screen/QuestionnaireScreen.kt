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
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.ui.viewmodel.QuestionnaireState
import es.upm.bienestaremocional.app.ui.viewmodel.QuestionnaireViewModel
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.launch

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
fun QuestionnaireScreen(navigator: DestinationsNavigator,
                        viewModel: QuestionnaireViewModel)
{
    //header

    val previousTitleText = stringResource(viewModel.previousTitleTextRes)
    when(viewModel.state)
    {
        QuestionnaireState.InProgress ->
        {
            val questionnaireLabel = stringResource(viewModel.questionnaire.labelRes)
            DrawQuestionnaire(
                title = "$previousTitleText $questionnaireLabel",
                questions = stringArrayResource(viewModel.questionnaire.questionRes),
                answers = stringArrayResource(viewModel.questionnaire.answerRes),
                answerSelected = {index -> viewModel.getAnswer(index)},
                onAnswer = { question, answer ->
                    if (viewModel.getAnswer(question) != answer)
                        viewModel.setAnswer(question,answer)
                    else
                        viewModel.removeAnswer(question)
                },
                onFinish = {viewModel.state = QuestionnaireState.Summary(viewModel.getScore()!!)},
                onSkip = {viewModel.state = QuestionnaireState.Skip},
                answersRemaining = { viewModel.answersRemaining() },
            )
        }
        is QuestionnaireState.Summary ->
        {
            Summary(score = (viewModel.state as QuestionnaireState.Summary).score, onSucess = { viewModel.state = QuestionnaireState.Finish })
        }
        QuestionnaireState.Skip ->
        {
            viewModel.state = QuestionnaireState.Finish
            //Button(onClick = {viewModel.state = QuestionnaireState.Finish}) {
            //    Text(text = "Aceptar")
            //}
        }
        QuestionnaireState.Finish -> {navigator.popBackStack()}
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DrawQuestionnaire(title: String,
                      questions : Array<String>,
                      answers: Array<String>,
                      answerSelected: (Int) -> Int?,
                      onAnswer: (Int,Int) -> Unit,
                      onFinish: () -> Unit,
                      onSkip: () -> Unit,
                      answersRemaining: () -> List<Int>)
{
    val showAlertDialog = remember { mutableStateOf(false) }
    val finishPressed = remember { mutableStateOf(false) }

    Surface()
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp))
        {
            val pagerState = rememberPagerState()

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
                    IconButton(onClick = {showAlertDialog.value = true})
                    {
                        Icon(Icons.Filled.Close, contentDescription = "Finish")
                    }
                }

            }

            HorizontalPager(
                count = questions.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            )
            {
                    page -> QuestionnairePage(question = questions[page],
                answers = answers,
                answerSelectedPrevious = answerSelected(page),
                pagerState = pagerState,
                onAnswer = {answer -> onAnswer(page,answer)},
                onExit = {showAlertDialog.value = true},
                onFinish = {finishPressed.value = true})
            }
        }
    }

    //show when user closes screen
    if (showAlertDialog.value)
    {
        ExitDialog(onDismiss = { showAlertDialog.value = false },
            onConfirm = {
                showAlertDialog.value = false
                onSkip()
            })
    }

    //show when user press finish
    if (finishPressed.value)
    {
        val listAnswersRemaining = answersRemaining()
        if (listAnswersRemaining.isEmpty())
        {
            finishPressed.value = false
            onFinish()
        }
        else
        {
            AnswersRemainingDialog(answersRemaining = listAnswersRemaining) {finishPressed.value = false}
        }
        //TODO hacer un dialogo que diga que no has acabado. Hay que recibir la funcion de cuestionario completado

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun QuestionnairePage(question: String,
                                  answers : Array<String>,
                                  answerSelectedPrevious : Int?,
                                  pagerState : PagerState,
                                  onAnswer: (Int) -> Unit,
                                  onExit: () -> Unit,
                                  onFinish: () -> Unit
)
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
                    activeColor = MaterialTheme.colorScheme.primary)

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

@Preview(showBackground = true)
@Composable
fun QuestionnaireScreenPreview()
{
    val questionnaire = Questionnaire.PHQ
    val answers : Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)
    val questionnaireLabel = stringResource(questionnaire.labelRes)

    BienestarEmocionalTheme {
        DrawQuestionnaire(
            title = "Cuestionario $questionnaireLabel",
            questions = stringArrayResource(questionnaire.questionRes),
            answers = stringArrayResource(questionnaire.answerRes),
            answerSelected = {question -> answers[question]},
            onAnswer = {question, answer -> answers[question] = answer},
            onFinish = { },
            onSkip = { },
            answersRemaining = { List(answers.filter { it == null }.size) { index->index}},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionnaireScreenPreviewDarkTheme()
{
    val questionnaire = Questionnaire.PHQ
    val answers : Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)
    val questionnaireLabel = stringResource(questionnaire.labelRes)

    BienestarEmocionalTheme(darkTheme = true) {
        DrawQuestionnaire(
            title = "Cuestionario $questionnaireLabel",
            questions = stringArrayResource(questionnaire.questionRes),
            answers = stringArrayResource(questionnaire.answerRes),
            answerSelected = {question -> answers[question]},
            onAnswer = {question, answer -> answers[question] = answer},
            onFinish = { },
            onSkip = { },
            answersRemaining = { List(answers.filter { it == null }.size) { index->index}},
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview(showBackground = true)
fun QuestionnairePagePreview()
{
    val questionnaire = Questionnaire.PHQ
    val questions = stringArrayResource(questionnaire.questionRes)
    val possibleAnswers = stringArrayResource(questionnaire.answerRes)
    val pagerState = rememberPagerState()

    val answers : Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)

    BienestarEmocionalTheme {
        Surface {
            QuestionnairePage(question = questions[0],
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
fun QuestionnairePageDarkThemePreview()
{
    val questionnaire = Questionnaire.PHQ
    val questions = stringArrayResource(questionnaire.questionRes)
    val possibleAnswers = stringArrayResource(questionnaire.answerRes)
    val pagerState = rememberPagerState()

    val answers : Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            QuestionnairePage(question = questions[0],
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
fun Summary(score : Int, onSucess : () -> Unit)
{
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Tu puntuación es: $score")
            Button(onClick = onSucess ) {
                Text("OK")
            }
        }

    }
}

@Composable
@Preview(showBackground = true)
fun SummaryPreview()
{
    BienestarEmocionalTheme {
        Summary(score = 10, onSucess = {})
    }
}

@Composable
@Preview(showBackground = true)
fun SummaryDarkThemePreview()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Summary(score = 10, onSucess = {})
    }
}