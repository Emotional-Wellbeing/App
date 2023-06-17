package es.upm.bienestaremocional.ui.component.questionnaire

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

@Composable
fun ExitDialog(
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
