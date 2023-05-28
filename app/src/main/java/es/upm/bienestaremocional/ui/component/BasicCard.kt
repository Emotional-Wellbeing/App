package es.upm.bienestaremocional.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

@Composable
fun BasicCard(content: @Composable (ColumnScope.() -> Unit))
{
    Card(modifier = Modifier.fillMaxWidth())
    {
        Column(modifier = Modifier.padding(16.dp))
        {
            content()
        }
    }
}

@Preview(group = "Light Theme")
@Composable
fun BasicCardPreview()
{
    BienestarEmocionalTheme {
        BasicCard{
            Text(text = "Hello world!")
        }
    }
}

@Preview(group = "Dark Theme")
@Composable
fun BasicCardPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        BasicCard{
            Text(text = "Hello world!")
        }
    }
}