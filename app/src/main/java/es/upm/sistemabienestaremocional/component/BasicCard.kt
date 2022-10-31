package es.upm.sistemabienestaremocional.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.sistemabienestaremocional.theme.SistemaBienestarEmocionalTheme

@Composable
fun BasicCard(content: @Composable (ColumnScope.() -> Unit))
{
    Card(modifier = Modifier.fillMaxWidth(), elevation = 4.dp)
    {
        Column(modifier = Modifier.padding(15.dp))
        {
            content()
        }
    }
}

@Preview
@Composable
fun BasicCardPreview()
{
    SistemaBienestarEmocionalTheme {
        BasicCard{
            Text(text = "Hello world!")
        }
    }
}