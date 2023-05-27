package es.upm.bienestaremocional.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

@Composable
fun DrawPair(key: String, value: String, textStyle: TextStyle? = null)
{
    Row(horizontalArrangement = Arrangement.SpaceAround)
    {
        if (textStyle != null)
        {
            Text(text = "$key: ",
                color = MaterialTheme.colorScheme.onSurface,
                style = textStyle)
            Text(text = value,
                color = MaterialTheme.colorScheme.tertiary,
                style = textStyle)
        }
        else
        {
            Text(text = "$key: ",
                color = MaterialTheme.colorScheme.onSurface)
            Text(text = value,
                color = MaterialTheme.colorScheme.tertiary)
        }

    }
}

@Preview(group = "Light Theme")
@Composable
fun DrawPairPreview()
{
    val key = "Motivación"
    val value = "100%"
    BienestarEmocionalTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            DrawPair(key = key, value = value)
        }
    }
}

@Preview(group = "Dark Theme")
@Composable
fun DrawPairDarkThemePreview()
{
    val key = "Motivación"
    val value = "100%"
    BienestarEmocionalTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            DrawPair(key = key, value = value)
        }
    }
}