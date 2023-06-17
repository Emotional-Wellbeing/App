package es.upm.bienestaremocional.ui.component.questionnaire

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
    text : String,
    selected : Boolean,
    onClick : () -> Unit
)
{
    val color by animateColorAsState(
        targetValue = if(selected)
            MaterialTheme.colorScheme.inversePrimary
        else
            MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(durationMillis = animationDurationMillis)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = color)
    )
    {
        Text(
            text =text,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}
@Composable
fun OptionSlider(
    initialValue : Int?,
    onAnswer: (Int) -> Unit,
    range : IntRange,
    textStyle : TextStyle = MaterialTheme.typography.labelMedium,
    textColor : Color = MaterialTheme.colorScheme.onBackground
)
{
    // Mutable state that stores the float position of the slider
    var sliderPosition by remember { mutableStateOf(initialValue?.toFloat() ?: 0f) }

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
private fun OptionSliderWithLine(
    initialValue : Int?,
    onAnswer: (Int) -> Unit,
    range : IntRange,
    textStyle : TextStyle = MaterialTheme.typography.labelMedium,
    textColor : Color = MaterialTheme.colorScheme.onBackground
)
{
    // Mutable state that stores the float position of the slider
    var sliderPosition by remember { mutableStateOf(initialValue?.toFloat() ?: 0f) }

    val rangeSize = (range.last - range.first)

    // Wrapper to not expose float type
    val onValueChange: (Float) -> Unit = {
        onAnswer(it.toInt())
    }

    // Canvas variables

    // Padding to start drawing
    val drawPadding = with(LocalDensity.current) { 10.dp.toPx() }
    // Size of the font in pixels
    val textSize = with(LocalDensity.current) { textStyle.fontSize.toPx() }
    // Height of total element
    val canvasHeight = 50.dp

    //Canvas object to draw text
    val textPaint = android.graphics.Paint().apply {
        color = textColor.toArgb()
        textAlign = android.graphics.Paint.Align.CENTER
        this.textSize = textSize
    }

    //Line variables
    val lineHeightDp = 10.dp
    val lineHeightPx = with(LocalDensity.current) { lineHeightDp.toPx() }



    Box(contentAlignment = Alignment.Center)
    {
        Canvas(
            modifier = Modifier
                .height(canvasHeight)
                .fillMaxWidth()
                .padding(
                    top = canvasHeight
                        .div(2)
                        .minus(lineHeightDp.div(2))
                )
        ) {
            // Line variables
            // Vertical axis is y on canvas
            val verticalStart = 0f

            val distance = (size.width.minus(2 * drawPadding)).div(rangeSize)
            range.forEachIndexed { index, point ->

                //Draw line marker
                drawLine(
                    color = Color.DarkGray,
                    start = Offset(x = drawPadding + index.times(distance), y = verticalStart),
                    end = Offset(x = drawPadding + index.times(distance), y = lineHeightPx)
                )

                // Draw label text
                this.drawContext.canvas.nativeCanvas.drawText(
                    point.toString(),
                    drawPadding + index.times(distance),
                    size.height,
                    textPaint
                )
            }
        }
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            modifier = Modifier.fillMaxWidth(),
            // ValueRange is float range
            valueRange = range.first.toFloat()..range.last.toFloat(),
            // We want to make available all the integers between first and last of the range
            steps = rangeSize - 1,
            // Set tick elements to color transparent in order to hide them
            colors = SliderDefaults.colors(
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            ),
            onValueChangeFinished = {
                onValueChange(sliderPosition)
            }
        )
    }
}

@Composable
@Preview
fun OptionCardNotSelectedPreview()
{
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
fun OptionCardNotSelectedDarkThemePreview()
{
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
fun OptionCardSelectedPreview()
{
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
fun OptionCardSelectedDarkThemePreview()
{
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
fun OptionSliderPreview()
{
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
fun OptionSliderDarkThemePreview()
{
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

@Composable
@Preview
fun OptionSliderWithLinePreview()
{
    BienestarEmocionalTheme {
        Surface {
            OptionSliderWithLine(
                initialValue = 2,
                onAnswer = {},
                range = 0..10
            )
        }
    }
}

@Composable
@Preview
fun OptionSliderWithLineDarkThemePreview()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            OptionSliderWithLine(
                initialValue = 2,
                onAnswer = {},
                range = 0..10
            )
        }
    }
}