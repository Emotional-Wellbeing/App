package es.upm.bienestaremocional.ui.component.questionnaire

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

@Composable
fun AnswersRemainingDialog(
    answersRemaining: List<Int>,
    onDismiss: () -> Unit
) {
    val textToShow = pluralStringResource(
        R.plurals.number_of_questions_left,
        answersRemaining.size,
        answersRemaining.map { it + 1 }.joinToString()
    )

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
@Preview
fun AnswersRemainingDialogPreview() {
    BienestarEmocionalTheme {
        AnswersRemainingDialog(answersRemaining = listOf(1, 2, 4, 7, 8)) {}
    }
}

@Composable
@Preview
fun AnswersRemainingDialogDarkThemePreview() {
    BienestarEmocionalTheme(darkTheme = true) {
        AnswersRemainingDialog(answersRemaining = listOf(1, 2, 4, 7)) {}
    }
}
