package es.upm.bienestaremocional.core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SwitchWithLabel(label: String)
{
    //without mutableStateOf this value doesn't change
    val isChecked = remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically)
    {
        Text(text = label)
        Spacer(Modifier.weight(1f))
        Switch(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it },
        )
    }
}