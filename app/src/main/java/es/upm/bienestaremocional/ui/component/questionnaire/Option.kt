package es.upm.bienestaremocional.ui.component.questionnaire

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

const val animationDurationMillis = 1000

@Composable
fun OptionCard(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color by animateColorAsState(
        targetValue = if (selected)
            MaterialTheme.colorScheme.inversePrimary
        else
            MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(durationMillis = animationDurationMillis),
        label = "color"
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = color)
    )
    {
        Text(
            text = text,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun OptionSlider(
    initialValue: Int?,
    onAnswer: (Int) -> Unit,
    range: IntRange,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {
    // Mutable state that stores the float position of the slider
    var sliderPosition by remember { mutableFloatStateOf(initialValue?.toFloat() ?: 0f) }

    val rangeSize = (range.last - range.first)

    // Wrapper to not expose float type
    val onValueChange: (Float) -> Unit = {
        onAnswer(it.toInt())
    }

    // Padding to start drawing
    val drawPadding = with(LocalDensity.current) { 10.dp.toPx() }
    // Size of the font
    val textSizeDp = with(LocalDensity.current) { textStyle.fontSize.toDp() }
    val textSizePx = with(LocalDensity.current) { textStyle.fontSize.toPx() }

    //Canvas object to draw text
    val textPaint = android.graphics.Paint().apply {
        color = textColor.toArgb()
        textAlign = android.graphics.Paint.Align.CENTER
        this.textSize = textSizePx
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally)
    {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            modifier = Modifier.fillMaxWidth(),
            // ValueRange is float range
            valueRange = range.first.toFloat()..range.last.toFloat(),
            // We want to make available all the integers between first and last of the range
            steps = rangeSize - 1,
            onValueChangeFinished = {
                onValueChange(sliderPosition)
            }
        )
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(textSizeDp)
        ) {
            val distance = (size.width.minus(2 * drawPadding)).div(rangeSize)
            range.forEachIndexed { index, point ->
                // Draw label text
                this.drawContext.canvas.nativeCanvas.drawText(
                    point.toString(),
                    drawPadding + index.times(distance),
                    size.height,
                    textPaint
                )
            }
        }
    }
}

@Composable
@Preview
fun OptionCardNotSelectedPreview() {
    val question = stringArrayResource(id = R.array.four_answers_questionnaire)[0]
    BienestarEmocionalTheme {
        OptionCard(
            text = question,
            selected = false,
            onClick = {})
    }
}

@Composable
@Preview
fun OptionCardNotSelectedDarkThemePreview() {
    val question = stringArrayResource(id = R.array.four_answers_questionnaire)[0]
    BienestarEmocionalTheme(darkTheme = true) {
        OptionCard(
            text = question,
            selected = false,
            onClick = {})
    }
}

@Composable
@Preview
fun OptionCardSelectedPreview() {
    val question = stringArrayResource(id = R.array.four_answers_questionnaire)[0]
    BienestarEmocionalTheme {
        OptionCard(
            text = question,
            selected = true,
            onClick = {})
    }
}

@Composable
@Preview
fun OptionCardSelectedDarkThemePreview() {
    val question = stringArrayResource(id = R.array.four_answers_questionnaire)[0]
    BienestarEmocionalTheme(darkTheme = true) {
        OptionCard(
            text = question,
            selected = true,
            onClick = {})
    }
}

@Composable
@Preview
fun OptionSliderPreview() {
    BienestarEmocionalTheme {
        Surface {
            OptionSlider(
                initialValue = 2,
                onAnswer = {},
                range = 0..10
            )
        }
    }
}

@Composable
@Preview
fun OptionSliderDarkThemePreview() {
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            OptionSlider(
                initialValue = 2,
                onAnswer = {},
                range = 0..10
            )
        }
    }
}