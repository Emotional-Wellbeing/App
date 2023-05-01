package es.upm.bienestaremocional.core.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import java.lang.Float.min

/**
 * Implementation of circular progress indicator consisting of an inner circle containing the
 * measurement text and an outer circumferential arc showing the data.
 *
 * The measurement displayed on the arc is the angle corresponding to the value of the
 * data with respect to the maximum.
 *
 * The arc also has a background for improving visualization.
 * This background is an arc of 360 degrees.
 *
 * Based on the following implementation:
 * https://semicolonspace.com/circular-progressbar-android-compose/
 *
 * @param data Data to show
 * @param minValue Minimum value that the data variable can take (Zero by default=
 * @param maxValue Maximum value that the data variable can take (100 by default)
 * @param size Size of the whole element
 * @param indicatorThickness Size of the indicator
 * @param animationDuration Animation's duration in milliseconds
 * @param indicatorColor Color of the indicator
 * @param indicatorContainerColor Color of the container of the indicator.
 * @param backgroundColor Background color of the whole element
 * @param textStyle TextStyle of the text
 */
@Composable
fun CircularProgressIndicator(
    data : Float,
    minValue : Float = 0f,
    maxValue: Float = 100f,
    size : Dp = 260.dp,
    indicatorThickness : Dp = 24.dp,
    animationDuration : Int = 1000,
    indicatorColor : Color = MaterialTheme.colorScheme.tertiary,
    indicatorContainerColor : Color = MaterialTheme.colorScheme.tertiaryContainer,
    backgroundColor : Color = MaterialTheme.colorScheme.background,
    textStyle : TextStyle = MaterialTheme.typography.displayMedium,
)
{
    // Remembers the data value to update it
    var dataRemembered by remember { mutableStateOf(minValue) }

    // This is to animate the foreground indicator
    val dataUsageAnimate = animateFloatAsState(
        targetValue = dataRemembered,
        animationSpec = tween(durationMillis = animationDuration)
    )

    // Convert the data to an angle. Max is 360
    val sweepAngle = min(((dataUsageAnimate.value - minValue) / (maxValue - minValue)) * 360, 360f)

    // Start the animation
    LaunchedEffect(Unit)
    {
        dataRemembered = data
    }

    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    )
    {
        Canvas(modifier = Modifier.size(size))
        {
            // External circle, used as background of indicator arc
            drawCircle(
                color = indicatorContainerColor,
                radius = this.size.height / 2,
                center = Offset(x = this.size.width / 2, y = this.size.height / 2)
            )

            // Circle inside external circle used to view correctly the text of the data
            drawCircle(
                color = backgroundColor,
                radius = (size / 2 - indicatorThickness).toPx(),
                center = Offset(x = this.size.width / 2, y = this.size.height / 2)
            )

            // Draws the indicator using an arc
            drawArc(
                color = indicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round),
                size = Size(
                    width = (size - indicatorThickness).toPx(),
                    height = (size - indicatorThickness).toPx()
                ),
                topLeft = Offset(
                    x = (indicatorThickness / 2).toPx(),
                    y = (indicatorThickness / 2).toPx()
                )
            )
        }

        // Display value in text in the middle of the element
        Text(
            text = (dataUsageAnimate.value).toInt().toString(),
            style = textStyle
        )
    }
}

@Preview
@Composable
fun CircularProgressIndicatorPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            CircularProgressIndicator(data = 75f)
        }
    }
}

@Preview
@Composable
fun CircularProgressIndicatorPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            CircularProgressIndicator(data = 75f)
        }
    }
}

@Preview
@Composable
fun CircularProgressIndicatorCustomRangePreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            CircularProgressIndicator(
                data = 75f,
                minValue = 10f,
                maxValue = 30f
            )
        }
    }
}

@Preview
@Composable
fun CircularProgressIndicatorCustomRangePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            CircularProgressIndicator(
                data = 20f,
                minValue = 10f,
                maxValue = 30f
            )
        }
    }
}