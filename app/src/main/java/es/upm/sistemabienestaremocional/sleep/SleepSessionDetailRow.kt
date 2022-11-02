
package es.upm.sistemabienestaremocional.sleep

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.sistemabienestaremocional.R
import es.upm.sistemabienestaremocional.ui.theme.SistemaBienestarEmocionalTheme

@Composable
fun SleepSessionDetailRow(
    @StringRes labelId: Int,
    item: String?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(0.25f),
            text = stringResource(id = labelId),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier
                .weight(0.4f),
            text = item ?: "N/A",
            textAlign = TextAlign.Start
        )
    }
}

@Preview
@Composable
fun SleepSessionDetailRowPreview() {
    SistemaBienestarEmocionalTheme {
        Column {
            SleepSessionDetailRow(
                labelId = R.string.sleep_notes,
                item = "Slept well"
            )
        }
    }
}
