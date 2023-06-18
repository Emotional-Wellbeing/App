package es.upm.bienestaremocional.ui.screens.advice

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.data.Advice
import es.upm.bienestaremocional.data.Measure
import es.upm.bienestaremocional.data.questionnaire.Level
import es.upm.bienestaremocional.ui.component.TextScreen
import es.upm.bienestaremocional.ui.component.text.LinkText
import es.upm.bienestaremocional.ui.component.text.LinkTextData
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.openDial

@Destination
@Composable
fun AdviceScreen(
    navigator: DestinationsNavigator,
    advice: Advice,
)
{
    AdviceScreen(
        navigator = navigator,
        advice = advice,
        widthSize = computeWindowWidthSize(),
    )
}

@Composable
private fun AdviceScreen(
    navigator: DestinationsNavigator,
    advice: Advice,
    widthSize: WindowWidthSizeClass,
)
{
    val context = LocalContext.current

    val content : @Composable (TextStyle) -> Unit = { textStyle ->

        advice.body?.let { bodyRes ->
            val pieces = stringArrayResource(id = bodyRes)

            if (pieces.size > 1)
            {
                val data = pieces.mapIndexed { index, item ->
                    //Add space trimmed in xml-loading
                    val text = if(index > 0)
                        " $item"
                    else
                        item

                    // number of phones are present in odd indexes
                    if (index % 2 == 1)
                        LinkTextData(
                            text = text,
                            tag = "number_${(index-1)/2}",
                            annotation = pieces[index],
                            onClick = { openDial(context, it.item) },
                        )
                    else
                        LinkTextData(text = text)
                }
                LinkText(
                    data = data,
                    textStyle = textStyle,
                    normalTextSpanStyle = SpanStyle(color = MaterialTheme.colorScheme.onSurface),
                    clickableTextSpanStyle = SpanStyle(color = MaterialTheme.colorScheme.tertiary)
                )
            }
            else
            {
                Text(
                    text = pieces[0],
                    textAlign = TextAlign.Justify,
                    style = textStyle
                )
            }
        }
    }

    TextScreen(
        navigator = navigator,
        textContent = content,
        widthSize = widthSize
    )
}

@Preview
@Composable
fun LowStressAdvicePreview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Stress.advices!![Level.Low]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}
@Preview
@Composable
fun LowStressAdvicePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Stress.advices!![Level.Low]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun ModerateStressAdvicePreview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Stress.advices!![Level.Moderate]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun ModerateStressAdvicePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Stress.advices!![Level.Moderate]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun ModerateStressAdvice2Preview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Stress.advices!![Level.Moderate]!![1],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun ModerateStressAdvice2PreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Stress.advices!![Level.Moderate]!![1],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun HighStressAdvicePreview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Stress.advices!![Level.High]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun HighStressAdvicePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Stress.advices!![Level.High]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun LowDepressionAdvicePreview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Depression.advices!![Level.Low]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}
@Preview
@Composable
fun LowDepressionAdvicePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Depression.advices!![Level.Low]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun ModerateDepressionAdvicePreview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Depression.advices!![Level.Moderate]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun ModerateDepressionAdvicePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Depression.advices!![Level.Moderate]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun HighDepressionAdvicePreview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Depression.advices!![Level.High]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun HighDepressionAdvicePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Depression.advices!![Level.High]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun LowLonelinessAdvicePreview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Loneliness.advices!![Level.Low]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}
@Preview
@Composable
fun LowLonelinessAdvicePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Loneliness.advices!![Level.Low]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun ModerateLonelinessAdvicePreview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Loneliness.advices!![Level.Moderate]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun ModerateLonelinessAdvicePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Loneliness.advices!![Level.Moderate]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun HighLonelinessAdvicePreview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Loneliness.advices!![Level.High]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun HighLonelinessAdvicePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Loneliness.advices!![Level.High]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun LowSuicideAdvicePreview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Suicide.advices!![Level.Low]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}
@Preview
@Composable
fun LowSuicideAdvicePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Suicide.advices!![Level.Low]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun ModerateSuicideAdvicePreview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Suicide.advices!![Level.Moderate]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun ModerateSuicideAdvicePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Suicide.advices!![Level.Moderate]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun HighSuicideAdvicePreview()
{
    BienestarEmocionalTheme {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Suicide.advices!![Level.High]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview
@Composable
fun HighSuicideAdvicePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        AdviceScreen(
            navigator = EmptyDestinationsNavigator,
            advice = Measure.Suicide.advices!![Level.High]!![0],
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}