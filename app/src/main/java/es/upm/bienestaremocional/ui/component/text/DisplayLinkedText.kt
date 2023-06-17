package es.upm.bienestaremocional.ui.component.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.openDial

@Composable
fun DisplayHighLonelinessGuideline()
{
    val context = LocalContext.current
    val pieces = stringArrayResource(id = R.array.high_loneliness)

    LinkText(
        data = listOf(
            LinkTextData(text = pieces[0]),
            LinkTextData(
                text = pieces[1],
                tag = "red_cross_number",
                annotation = "+34900107917",
                onClick = { openDial(context, it.item) },
            ),
            LinkTextData(text = pieces[2]),
        ),
        textStyle = MaterialTheme.typography.bodyMedium,
        normalTextSpanStyle = SpanStyle(color = MaterialTheme.colorScheme.onSurface),
        clickableTextSpanStyle = SpanStyle(color = MaterialTheme.colorScheme.tertiary),
    )
}

@Preview
@Composable
fun DisplayHighLonelinessGuildelinePreview()
{
    BienestarEmocionalTheme()
    {
        Card(modifier = Modifier.padding(16.dp))
        {
            DisplayHighLonelinessGuideline()
        }
    }
}

@Preview
@Composable
fun DisplayHighLonelinessGuildelinePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Card(modifier = Modifier.padding(16.dp))
        {
            DisplayHighLonelinessGuideline()
        }
    }
}