package es.upm.bienestaremocional.ui.component.text

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.openDial


@Composable
fun LinkText(
    data: List<LinkTextData>,
    textStyle: TextStyle,
    normalTextSpanStyle: SpanStyle,
    clickableTextSpanStyle: SpanStyle,
    modifier: Modifier = Modifier,
) {
    val annotatedString = createAnnotatedString(
        data = data,
        normalTextSpanStyle = normalTextSpanStyle,
        clickableTextSpanStyle = clickableTextSpanStyle
    )

    ClickableText(
        text = annotatedString,
        style = textStyle,
        onClick = { offset ->
            data.forEach { annotatedStringData ->
                if (annotatedStringData.tag != null && annotatedStringData.annotation != null) {
                    annotatedString.getStringAnnotations(
                        tag = annotatedStringData.tag,
                        start = offset,
                        end = offset,
                    ).firstOrNull()?.let {
                        annotatedStringData.onClick?.invoke(it)
                    }
                }
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun createAnnotatedString(
    data: List<LinkTextData>,
    normalTextSpanStyle: SpanStyle,
    clickableTextSpanStyle: SpanStyle,
): AnnotatedString {
    return buildAnnotatedString {
        data.forEach { linkTextData ->
            if (linkTextData.tag != null && linkTextData.annotation != null) {
                //push annotation and stylish text with linkTextData
                pushStringAnnotation(
                    tag = linkTextData.tag,
                    annotation = linkTextData.annotation,
                )
                withStyle(style = clickableTextSpanStyle)
                {
                    append(linkTextData.text)
                }
                pop()
            }
            else {
                //Apply normalTextStyle and go on
                pushStyle(style = normalTextSpanStyle)
                append(linkTextData.text)
                pop()
            }
        }
    }
}

@Preview
@Composable
private fun LinkTextPreview() {
    val context = LocalContext.current
    val pieces = stringArrayResource(id = R.array.high_loneliness)

    BienestarEmocionalTheme {
        Surface(modifier = Modifier.padding(16.dp))
        {
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
    }
}

@Preview
@Composable
private fun LinkTextPreviewDarkTheme() {
    val context = LocalContext.current
    val pieces = stringArrayResource(id = R.array.high_loneliness)

    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface(modifier = Modifier.padding(16.dp))
        {
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
    }
}