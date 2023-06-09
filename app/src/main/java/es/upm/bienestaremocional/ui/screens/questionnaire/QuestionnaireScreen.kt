package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.data.questionnaire.QuestionnaireDrawableNumericAnswers
import es.upm.bienestaremocional.data.questionnaire.QuestionnaireDrawableStringAnswers
import es.upm.bienestaremocional.data.questionnaire.daily.DailyNotScoredQuestionnaireDrawable
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaireDrawable
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffQuestionnaireDrawable
import es.upm.bienestaremocional.ui.component.OneOffStressStatus
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalPagerApi::class)
@Composable
fun QuestionnaireStringAnswersScreen(
    state: QuestionnaireState,
    questionnaire: QuestionnaireDrawableStringAnswers,
    widthSize: WindowWidthSizeClass,
    title: String,
    answerSelected: (Int) -> Int?,
    answersRemaining: () -> List<Int>,
    getScore : () -> Int?,
    onAnswer: (Int,Int) -> Unit,
    onInProgress : () -> Unit,
    onSkippingAttempt : () -> Unit,
    onSkipped: () -> Unit,
    onFinishAttempt: () -> Unit,
    onSummary: () -> Unit,
    onExit: () -> Unit,
    summaryContent: @Composable (Int, WindowWidthSizeClass) -> Unit,
)
{
    val questions = stringArrayResource(questionnaire.questionRes)
    val answers = questionnaire.answerRes.map {stringArrayResource(it) }

    val pagerContent : @Composable (PagerScope.(Int, PagerState) -> Unit) = { page, pagerState -> 
        QuestionnaireStringAnswersPage(question = questions[page],
            answers = if(answers.size == 1) answers[0] else answers[page],
            answerSelectedPrevious = answerSelected(page),
            pagerState = pagerState,
            onAnswer = {answer -> onAnswer(page,answer)},
            onExit = onSkippingAttempt,
            onFinish = onFinishAttempt
        )
    }

    QuestionnaireScoredScreen(
        state = state,
        questionnaire = questionnaire,
        widthSize = widthSize,
        title = title,
        answersRemaining = answersRemaining,
        getScore = getScore,
        onInProgress = onInProgress,
        onSkippingAttempt = onSkippingAttempt,
        onSkipped = onSkipped,
        onSummary = onSummary,
        onExit = onExit,
        pagerContent = pagerContent,
        summaryContent = summaryContent
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun QuestionnaireStringAnswersScreen(
    state: QuestionnaireState,
    questionnaire: QuestionnaireDrawableStringAnswers,
    title: String,
    answerSelected: (Int) -> Int?,
    answersRemaining: () -> List<Int>,
    onAnswer: (Int,Int) -> Unit,
    onInProgress : () -> Unit,
    onSkippingAttempt : () -> Unit,
    onSkipped: () -> Unit,
    onFinishAttempt: () -> Unit,
    onSummary: () -> Unit,
    onExit: () -> Unit,
)
{
    val questions = stringArrayResource(questionnaire.questionRes)
    val answers = questionnaire.answerRes.map {stringArrayResource(it) }

    val pagerContent : @Composable (PagerScope.(Int, PagerState) -> Unit) = { page, pagerState -> 
        QuestionnaireStringAnswersPage(question = questions[page],
            answers = if(answers.size == 1) answers[0] else answers[page],
            answerSelectedPrevious = answerSelected(page),
            pagerState = pagerState,
            onAnswer = {answer -> onAnswer(page,answer)},
            onExit = onSkippingAttempt,
            onFinish = onFinishAttempt
        )
    }

    QuestionnaireNotScoredScreen(
        state = state,
        questionnaire = questionnaire,
        title = title,
        answersRemaining = answersRemaining,
        onInProgress = onInProgress,
        onSkippingAttempt = onSkippingAttempt,
        onSkipped = onSkipped,
        onSummary = onSummary,
        onExit = onExit,
        pagerContent = pagerContent
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun QuestionnaireNumericAnswersScreen(
    state: QuestionnaireState,
    questionnaire: QuestionnaireDrawableNumericAnswers,
    widthSize: WindowWidthSizeClass,
    title: String,
    answerSelected: (Int) -> Int?,
    answersRemaining: () -> List<Int>,
    getScore : () -> Int?,
    onAnswer: (Int,Int) -> Unit,
    onInProgress : () -> Unit,
    onSkippingAttempt : () -> Unit,
    onSkipped: () -> Unit,
    onFinishAttempt: () -> Unit,
    onSummary: () -> Unit,
    onExit: () -> Unit,
    summaryContent: @Composable (Int, WindowWidthSizeClass) -> Unit,
)
{
    val questions = stringArrayResource(questionnaire.questionRes)
    val answerRange = questionnaire.answerRange

    val pagerContent : @Composable (PagerScope.(Int, PagerState) -> Unit) = { page, pagerState ->
        QuestionnaireNumericAnswersPage(question = questions[page],
            answerRange = answerRange,
            answerSelectedPrevious = answerSelected(page),
            pagerState = pagerState,
            onAnswer = {answer -> onAnswer(page,answer)},
            onExit = onSkippingAttempt,
            onFinish = onFinishAttempt)
    }

    QuestionnaireScoredScreen(
        state = state,
        questionnaire = questionnaire,
        widthSize = widthSize,
        title = title,
        answersRemaining = answersRemaining,
        getScore = getScore,
        onInProgress = onInProgress,
        onSkippingAttempt = onSkippingAttempt,
        onSkipped = onSkipped,
        onSummary = onSummary,
        onExit = onExit,
        pagerContent = pagerContent,
        summaryContent = summaryContent
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun QuestionnaireScoredScreen(
    state: QuestionnaireState,
    questionnaire: Questionnaire,
    widthSize: WindowWidthSizeClass,
    title: String,
    answersRemaining: () -> List<Int>,
    getScore : () -> Int?,
    onInProgress : () -> Unit,
    onSkippingAttempt : () -> Unit,
    onSkipped: () -> Unit,
    onSummary: () -> Unit,
    onExit: () -> Unit,
    pagerContent: @Composable PagerScope.(Int, PagerState) -> Unit,
    summaryContent : @Composable (Int, WindowWidthSizeClass) -> Unit,
)
{
    val pagerState = rememberPagerState()
    if (state !is QuestionnaireState.Summary || state !is QuestionnaireState.Finished)
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
                        IconButton(onClick = onSkippingAttempt)
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
                ) { page ->
                    pagerContent(page,pagerState)
                }
            }
        }
    }
    when(state)
    {
        QuestionnaireState.InProgress -> {}
        QuestionnaireState.SkipAttempt ->
        {
            ExitDialog(onDismiss = onInProgress, onConfirm = onSkipped)
        }
        QuestionnaireState.Skipped -> onExit()
        QuestionnaireState.FinishAttempt ->
        {
            AnswersRemainingDialog(answersRemaining = answersRemaining(),
                onDismiss = onInProgress)
        }
        QuestionnaireState.Summary ->
        {
            Summary(
                score = getScore()!!,
                widthSize = widthSize,
                content = summaryContent,
                onSuccess = onSummary
            )
        }
        QuestionnaireState.Finished -> onExit()

    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun QuestionnaireNotScoredScreen(
    state: QuestionnaireState,
    questionnaire: Questionnaire,
    title: String,
    answersRemaining: () -> List<Int>,
    onInProgress : () -> Unit,
    onSkippingAttempt : () -> Unit,
    onSkipped: () -> Unit,
    onSummary: () -> Unit,
    onExit: () -> Unit,
    pagerContent: @Composable (PagerScope.(Int, PagerState) -> Unit),
)
{
    val pagerState = rememberPagerState()

    if (state !is QuestionnaireState.Summary || state !is QuestionnaireState.Finished)
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
                        IconButton(onClick = onSkippingAttempt)
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
                ) { page ->
                    pagerContent(page,pagerState)
                }
            }
        }
    }
    when(state)
    {
        QuestionnaireState.InProgress -> {}
        QuestionnaireState.SkipAttempt ->
        {
            ExitDialog(onDismiss = onInProgress, onConfirm = onSkipped)
        }
        QuestionnaireState.Skipped -> onExit()
        QuestionnaireState.FinishAttempt ->
        {
            AnswersRemainingDialog(answersRemaining = answersRemaining(),
                onDismiss = onInProgress)
        }
        QuestionnaireState.Summary -> onSummary()
        QuestionnaireState.Finished -> onExit()

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun QuestionnaireStringAnswersPage(
    question: String,
    answers : Array<String>,
    answerSelectedPrevious : Int?,
    pagerState : PagerState,
    onAnswer: (Int) -> Unit,
    onExit: () -> Unit,
    onFinish: () -> Unit
)
{
    val answerContent = @Composable {
        StringAnswer(
            answers = answers,
            answerSelectedPrevious = answerSelectedPrevious,
            onAnswer = onAnswer)
    }

    QuestionnairePage(
        question = question,
        pagerState = pagerState,
        answerContent = answerContent,
        onExit = onExit,
        onFinish = onFinish
    )

}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun QuestionnaireNumericAnswersPage(
    question: String,
    answerRange : IntRange,
    answerSelectedPrevious : Int?,
    pagerState : PagerState,
    onAnswer: (Int) -> Unit,
    onExit: () -> Unit,
    onFinish: () -> Unit)
{
    val answerContent = @Composable {
        NumericAnswer(
            answerRange = answerRange,
            answerSelectedPrevious = answerSelectedPrevious,
            onAnswer = onAnswer
        )
    }

    QuestionnairePage(
        question = question,
        pagerState = pagerState,
        answerContent = answerContent,
        onExit = onExit,
        onFinish = onFinish
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun QuestionnairePage(
    question: String,
    pagerState : PagerState,
    answerContent: @Composable (() -> Unit),
    onExit: () -> Unit,
    onFinish: () -> Unit
)
{
    val coroutineScope = rememberCoroutineScope()

    //content
    Column(modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        //q&a
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text("${(pagerState.currentPage + 1)}. $question", textAlign = TextAlign.Justify)
            answerContent()
        }

        //footer
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround
        )
        {
            //footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                if (pagerState.currentPage == 0) {
                    TextButton(onClick = onExit)
                    {
                        Text(text = stringResource(id = R.string.exit))
                    }
                }
                else {
                    TextButton(onClick = {
                        coroutineScope.launch {
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
                    indicatorWidth = if (pagerState.pageCount > 12) 4.dp else 8.dp
                )
                if (pagerState.currentPage == pagerState.pageCount - 1) {
                    TextButton(onClick = onFinish)
                    {
                        Text(text = stringResource(id = R.string.finish))
                    }
                }
                else {
                    TextButton(onClick = {
                        coroutineScope.launch {
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
private fun StringAnswer(
    answers : Array<String>,
    answerSelectedPrevious : Int?,
    onAnswer: (Int) -> Unit,
)
{
    val answerSelected = remember { mutableStateOf(answerSelectedPrevious) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        answers.forEachIndexed { index, answer ->
            OptionCard(
                text = answer,
                selected = index == answerSelected.value,
                onClick = {
                    onAnswer(index)
                    if (answerSelected.value != index)
                        answerSelected.value = index
                    else
                        answerSelected.value = null
                }
            )
        }
    }


}

@Composable
private fun NumericAnswer(
    answerRange : IntRange,
    answerSelectedPrevious : Int?,
    onAnswer: (Int) -> Unit,
)
{
    val answerSelected = remember { mutableStateOf(answerSelectedPrevious) }
    OptionSlider(
        initialValue = answerSelected.value?.toFloat(),
        onAnswer = {onAnswer(it.roundToInt())},
        valueRange = answerRange.first.toFloat()..answerRange.last.toFloat()
    )
}

@Composable
private fun ExitDialog(
    onDismiss : () -> Unit, 
    onConfirm : () -> Unit
)
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
            Text(stringResource(R.string.exit_questionnaire))
        },
        text = {
            Text(stringResource(R.string.sure_skip_questionnaire))
        })
}

@Composable
private fun AnswersRemainingDialog(
    answersRemaining: List<Int>,
    onDismiss : () -> Unit
)
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
            Text(stringResource(R.string.questionnaire_not_completed))
        },
        text = {
            Text(textToShow)
        })
}

@Composable
private fun OptionCard(
    text : String,
    selected : Boolean,
    onClick : () -> Unit
)
{
    val color by animateColorAsState(
        targetValue = if(selected)
            MaterialTheme.colorScheme.inversePrimary
        else
            MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(durationMillis = 1000)
    )
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = color)
    )
    {
        Text(text,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
    }
}

@Composable
private fun OptionSlider(
    initialValue : Float?,
    onAnswer: (Float) -> Unit,
    valueRange : ClosedFloatingPointRange<Float>
)
{
    var sliderPosition by remember { mutableStateOf(initialValue ?: 0f) }

    Column()
    {
        Text(text = sliderPosition.toString())
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = valueRange,
            onValueChangeFinished = {
                onAnswer(sliderPosition)
            },
            steps = (valueRange.endInclusive - valueRange.start - 1).toInt()
        )
    }
}


@Composable
private fun Summary(
    score : Int,
    widthSize : WindowWidthSizeClass,
    content : @Composable (Int, WindowWidthSizeClass) -> Unit,
    onSuccess : () -> Unit
)
{
    Surface(modifier = Modifier.fillMaxSize())
    {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Card(modifier = Modifier.padding(16.dp))
            {
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    content(score, widthSize)
                    TextButton(onClick = onSuccess)
                    {
                        Text(stringResource(R.string.continue_word))
                    }
                }

            }
        }
    }
}

@Composable
@Preview
fun QuestionnaireStringAnswersWithScoreScreenPreview()
{
    val questionnaire = OneOffQuestionnaireDrawable.Stress
    val answers: Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)
    val numberOfAnswers = questionnaire.numberOfAnswers
    val questionsWithInvertedScore = questionnaire.questionsWithInvertedScore
    val questionScoreOffset = questionnaire.questionScoreOffset

    val score : () -> Int? = {
        if (answers.all { it != null })
        {
            var auxiliaryScore = 0
            answers.forEachIndexed { index, answer ->

                var tempScore = answer!!
                if (questionsWithInvertedScore.contains(index))
                    tempScore = numberOfAnswers - 1 - tempScore
                tempScore += questionScoreOffset

                auxiliaryScore += tempScore
            }
            auxiliaryScore
        }
        else
            null
    }

    val summaryContent : @Composable (Int, WindowWidthSizeClass) -> Unit = { data, widthSize ->
        OneOffStressStatus(
            data = data,
            widthSize = widthSize
        )
    }

    BienestarEmocionalTheme {
        Surface {
            QuestionnaireStringAnswersScreen(
                state = QuestionnaireState.InProgress,
                questionnaire = questionnaire,
                widthSize = WindowWidthSizeClass.Compact,
                title = "${stringResource(R.string.questionnaire)} 1/1 ${stringResource(questionnaire.measureRes)}",
                answerSelected = { index -> answers[index] },
                answersRemaining = {
                    answers.mapIndexed { index, value -> if (value == null) index else null }
                        .filterNotNull()
                },
                getScore = score,
                onAnswer = { question, answer -> answers[question] = answer },
                onInProgress = {},
                onSkippingAttempt = {},
                onSkipped = {},
                onFinishAttempt = {},
                onSummary = {},
                onExit = {},
                summaryContent = summaryContent
            )
        }
    }
}

@Composable
@Preview
fun QuestionnaireStringAnswersWithScoreDarkThemeScreenPreview()
{
    val questionnaire = OneOffQuestionnaireDrawable.Stress
    val answers: Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)
    val numberOfAnswers = questionnaire.numberOfAnswers
    val questionsWithInvertedScore = questionnaire.questionsWithInvertedScore
    val questionScoreOffset = questionnaire.questionScoreOffset

    val score : () -> Int? = {
        if (answers.all { it != null })
        {
            var auxiliaryScore = 0
            answers.forEachIndexed { index, answer ->

                var tempScore = answer!!
                if (questionsWithInvertedScore.contains(index))
                    tempScore = numberOfAnswers - 1 - tempScore
                tempScore += questionScoreOffset

                auxiliaryScore += tempScore
            }
            auxiliaryScore
        }
        else
            null
    }

    val summaryContent : @Composable (Int, WindowWidthSizeClass) -> Unit = { data, widthSize ->
        OneOffStressStatus(
            data = data,
            widthSize = widthSize
        )
    }

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            QuestionnaireStringAnswersScreen(
                state = QuestionnaireState.InProgress,
                questionnaire = questionnaire,
                widthSize = WindowWidthSizeClass.Compact,
                title = "${stringResource(R.string.questionnaire)} 1/1 ${stringResource(questionnaire.measureRes)}",
                answerSelected = { index -> answers[index] },
                answersRemaining = {
                    answers.mapIndexed { index, value -> if (value == null) index else null }
                        .filterNotNull()
                },
                getScore = score,
                onAnswer = { question, answer -> answers[question] = answer },
                onInProgress = {},
                onSkippingAttempt = {},
                onSkipped = {},
                onFinishAttempt = {},
                onSummary = {},
                onExit = {},
                summaryContent = summaryContent
            )
        }
    }
}

@Composable
@Preview
fun QuestionnaireStringAnswersWithoutScoreScreenPreview()
{
    val questionnaire = DailyNotScoredQuestionnaireDrawable.Symptoms
    val answers: Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)

    BienestarEmocionalTheme {
        Surface {
            QuestionnaireStringAnswersScreen(
                state = QuestionnaireState.InProgress,
                questionnaire = questionnaire,
                title = "${stringResource(R.string.questionnaire)} 1/1 ${stringResource(questionnaire.measureRes)}",
                answerSelected = { index -> answers[index] },
                answersRemaining = {
                    answers.mapIndexed { index, value -> if (value == null) index else null }
                        .filterNotNull()
                },
                onAnswer = { question, answer -> answers[question] = answer },
                onInProgress = {},
                onSkippingAttempt = {},
                onSkipped = {},
                onFinishAttempt = {},
                onSummary = {},
                onExit = {}
            )
        }
    }
}

@Composable
@Preview
fun QuestionnaireStringAnswersWithoutScoreDarkThemeScreenPreview()
{
    val questionnaire = DailyNotScoredQuestionnaireDrawable.Symptoms
    val answers: Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            QuestionnaireStringAnswersScreen(
                state = QuestionnaireState.InProgress,
                questionnaire = questionnaire,
                title = "${stringResource(R.string.questionnaire)} 1/1 ${stringResource(questionnaire.measureRes)}",
                answerSelected = { index -> answers[index] },
                answersRemaining = {
                    answers.mapIndexed { index, value -> if (value == null) index else null }
                        .filterNotNull()
                },
                onAnswer = { question, answer -> answers[question] = answer },
                onInProgress = {},
                onSkippingAttempt = {},
                onSkipped = {},
                onFinishAttempt = {},
                onSummary = {},
                onExit = {}
            )
        }
    }
}

@Composable
@Preview
fun QuestionnaireNumericAnswersWithScoreScreenPreview()
{
    val questionnaire = DailyScoredQuestionnaireDrawable.NightStress
    val answers: Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)

    val score : () -> Int? = {
        if (answers.all { it != null })
            answers.filterNotNull().sum()
        else
            null
    }

    val summaryContent : @Composable (Int, WindowWidthSizeClass) -> Unit = { data, widthSize ->
        OneOffStressStatus(
            data = data,
            widthSize = widthSize
        )
    }

    BienestarEmocionalTheme {
        Surface {
            QuestionnaireNumericAnswersScreen(
                state = QuestionnaireState.InProgress,
                questionnaire = questionnaire,
                widthSize = WindowWidthSizeClass.Compact,
                title = "${stringResource(R.string.questionnaire)} 1/1 ${stringResource(questionnaire.measureRes)}",
                answerSelected = { index -> answers[index] },
                answersRemaining = {
                    answers.mapIndexed { index, value -> if (value == null) index else null }
                        .filterNotNull()
                },
                getScore = score,
                onAnswer = { question, answer -> answers[question] = answer },
                onInProgress = {},
                onSkippingAttempt = {},
                onSkipped = {},
                onFinishAttempt = {},
                onSummary = {},
                onExit = {},
                summaryContent = summaryContent
            )
        }
    }
}

@Composable
@Preview
fun QuestionnaireNumericAnswersWithScoreDarkThemeScreenPreview()
{
    val questionnaire = DailyScoredQuestionnaireDrawable.NightStress
    val answers: Array<Int?> = arrayOfNulls(questionnaire.numberOfQuestions)

    val score : () -> Int? = {
        if (answers.all { it != null })
            answers.filterNotNull().sum()
        else
            null
    }

    val summaryContent : @Composable (Int, WindowWidthSizeClass) -> Unit = { data, widthSize ->
        OneOffStressStatus(
            data = data,
            widthSize = widthSize
        )
    }

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            QuestionnaireNumericAnswersScreen(
                state = QuestionnaireState.InProgress,
                questionnaire = questionnaire,
                widthSize = WindowWidthSizeClass.Compact,
                title = "${stringResource(R.string.questionnaire)} 1/1 ${stringResource(questionnaire.measureRes)}",
                answerSelected = { index -> answers[index] },
                answersRemaining = {
                    answers.mapIndexed { index, value -> if (value == null) index else null }
                        .filterNotNull()
                },
                getScore = score,
                onAnswer = { question, answer -> answers[question] = answer },
                onInProgress = {},
                onSkippingAttempt = {},
                onSkipped = {},
                onFinishAttempt = {},
                onSummary = {},
                onExit = {},
                summaryContent = summaryContent
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview
fun QuestionnaireStringAnswersPagePreview()
{
    val question = stringArrayResource(id = R.array.one_off_stress_questions)[0]
    val answers = stringArrayResource(id = R.array.five_answers_questionnaire)

    BienestarEmocionalTheme {
        Surface {
            QuestionnaireStringAnswersPage(
                question = question,
                answers = answers,
                answerSelectedPrevious = null,
                pagerState = rememberPagerState(),
                onAnswer = {} ,
                onExit = { },
                onFinish = {}
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview
fun QuestionnaireStringAnswersPageDarkThemePreview()
{
    val question = stringArrayResource(id = R.array.one_off_stress_questions)[0]
    val answers = stringArrayResource(id = R.array.five_answers_questionnaire)
    
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            QuestionnaireStringAnswersPage(
                question = question,
                answers = answers,
                answerSelectedPrevious = null,
                pagerState = rememberPagerState(),
                onAnswer = {} ,
                onExit = { },
                onFinish = {}
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview
fun QuestionnaireNumericAnswersPagePreview()
{
    val question = stringArrayResource(id = R.array.daily_symptoms_questions)[0]
    BienestarEmocionalTheme {
        Surface {
            QuestionnaireNumericAnswersPage(
                question = question,
                answerRange = 0..10,
                answerSelectedPrevious = null,
                pagerState = rememberPagerState(),
                onAnswer = {} ,
                onExit = { },
                onFinish = {}
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview
fun QuestionnaireNumericAnswersPageDarkThemePreview()
{
    val question = stringArrayResource(id = R.array.daily_symptoms_questions)[0]
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            QuestionnaireNumericAnswersPage(
                question = question,
                answerRange = 0..10,
                answerSelectedPrevious = null,
                pagerState = rememberPagerState(),
                onAnswer = {} ,
                onExit = { },
                onFinish = {}
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview
fun QuestionnairePagePreview()
{
    val question = stringArrayResource(id = R.array.daily_symptoms_questions)[0]
    BienestarEmocionalTheme {
        Surface {
            QuestionnairePage(
                question = question,
                pagerState = rememberPagerState(),
                answerContent = {} ,
                onExit = { },
                onFinish = {}
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview
fun QuestionnairePageDarkThemePreview()
{
    val question = stringArrayResource(id = R.array.daily_symptoms_questions)[0]
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            QuestionnairePage(
                question = question,
                pagerState = rememberPagerState(),
                answerContent = {} ,
                onExit = { },
                onFinish = {}
            )
        }

    }
}

@Composable
@Preview
fun StringAnswerPreview()
{
    val answers = stringArrayResource(id = R.array.four_answers_questionnaire)
    BienestarEmocionalTheme {
        Surface {
            StringAnswer(
                answers = answers,
                answerSelectedPrevious = null,
                onAnswer = {}
            )
        }
    }
}

@Composable
@Preview
fun StringAnswerDarkThemePreview()
{
    val answers = stringArrayResource(id = R.array.four_answers_questionnaire)

    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            StringAnswer(
                answers = answers,
                answerSelectedPrevious = null,
                onAnswer = {}
            )
        }
    }
}

@Composable
@Preview
fun NumericAnswerPreview()
{
    BienestarEmocionalTheme {
        Surface {
            NumericAnswer(
                answerRange = 0..10,
                answerSelectedPrevious = null,
                onAnswer = {}
            )
        }
    }
}

@Composable
@Preview
fun NumericAnswerDarkThemePreview()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            NumericAnswer(
                answerRange = 0..10,
                answerSelectedPrevious = null,
                onAnswer = {}
            )
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
@Preview
fun OptionSliderPreview()
{
    BienestarEmocionalTheme {
        Surface {
            OptionSlider(
                initialValue = 2f,
                onAnswer = {},
                valueRange = 0f..10f
            )
        }
    }
}

@Composable
@Preview
fun OptionSliderDarkThemePreview()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            OptionSlider(
                initialValue = 2f,
                onAnswer = {},
                valueRange = 0f..10f
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun SummaryPreview()
{
    val summaryContent : @Composable (Int, WindowWidthSizeClass) -> Unit = { data, widthSize ->
        OneOffStressStatus(
            data = data,
            widthSize = widthSize
        )
    }

    BienestarEmocionalTheme {
        Summary(
            score = 10,
            widthSize = WindowWidthSizeClass.Compact,
            content = summaryContent,
            onSuccess = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SummaryDarkThemePreview()
{
    val summaryContent : @Composable (Int, WindowWidthSizeClass) -> Unit = { data, widthSize ->
        OneOffStressStatus(
            data = data,
            widthSize = widthSize
        )
    }

    BienestarEmocionalTheme(darkTheme = true) {
        Summary(
            score = 10,
            widthSize = WindowWidthSizeClass.Compact,
            content = summaryContent,
            onSuccess = {}
        )
    }
}