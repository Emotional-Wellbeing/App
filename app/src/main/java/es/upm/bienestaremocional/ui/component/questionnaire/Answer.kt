package es.upm.bienestaremocional.ui.component.questionnaire

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

@Composable
fun StringAnswer(
    answers : Array<String>,
    answerSelectedPrevious : Int?,
    onAnswer: (Int) -> Unit,
)
{
    val answerSelected = remember { mutableStateOf(answerSelectedPrevious) }
    // Refresh answerSelected if answers change
    LaunchedEffect(answers)
    {
        answerSelected.value = answerSelectedPrevious
    }

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
fun NumericAnswer(
    answerRange : IntRange,
    answerSelectedPrevious : Int?,
    onAnswer: (Int) -> Unit,
)
{
    OptionSlider(
        initialValue = answerSelectedPrevious,
        onAnswer = onAnswer,
        range = answerRange
    )
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