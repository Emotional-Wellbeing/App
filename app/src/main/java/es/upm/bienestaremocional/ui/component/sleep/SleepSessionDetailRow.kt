
package es.upm.bienestaremocional.ui.component.sleep

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Shows label and content in row format (sleep duration, notes, etc)
 */
@Composable
fun SleepSessionDetailRow(@StringRes labelId: Int, item: String?)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(modifier = Modifier.weight(0.5f),
            text = stringResource(id = labelId),
            color = MaterialTheme.colorScheme.secondary)

        Text(modifier = Modifier.weight(0.5f), text = item ?: "N/A")
    }
}

@Preview
@Composable
fun SleepSessionDetailRowPreview()
{
    BienestarEmocionalTheme {
        Column {
            SleepSessionDetailRow(
                labelId = R.string.sleep_notes,
                item = "Slept well"
            )
        }
    }
}
