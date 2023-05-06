package es.upm.bienestaremocional.core.ui.component

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import java.lang.Float.min

/**
 * Implementation of circular progress indicator consisting of an inner circle containing the
 * measurement text and an outer circumferential arc showing [data].
 *
 * The measurement displayed on the arc is the angle corresponding to the value of the
 * data with respect to the maximum.
 *
 * The arc also has a background for improving visualization ([indicatorContainerColor]).
 * This background is an arc of 360 degrees.
 *
 * Based on the following implementation:
 * https://semicolonspace.com/circular-progressbar-android-compose/
 *
 * @param data Data to show
 * @param minValue Minimum value that the data variable can take (Zero by default)
 * @param maxValue Maximum value that the data variable can take (100 by default)
 * @param size Size of the whole element
 * @param indicatorScaleFactor Proportion (number between 0 and 0.5) that indicates the size of the
 * indicator
 * @param animationDuration Animation's duration in milliseconds
 * @param indicatorColor Color of the indicator
 * @param indicatorContainerColor Color of the container of the indicator.
 * @param backgroundColor Background color of the whole element
 * @param textStyle TextStyle of the text
 */
@Composable
fun CircularProgressIndicator(
    data : Int?,
    minValue : Int = 0,
    maxValue : Int = 100,
    size : Dp = 250.dp,
    indicatorScaleFactor : Float = 0.1f,
    animationDuration : Int = 1000,
    indicatorColor : Color = MaterialTheme.colorScheme.tertiary,
    indicatorContainerColor : Color = MaterialTheme.colorScheme.tertiaryContainer,
    backgroundColor : Color = MaterialTheme.colorScheme.background,
    textStyle : TextStyle = MaterialTheme.typography.displayMedium,
)
{
    val textContent : @Composable (State<Int>) -> Unit = { dataUsageAnimate ->

        val unknownLabel = stringResource(id = R.string.unknown_display)

        // Display value in text in the middle of the element
        // More info at
        // https://medium.com/androiddevelopers/fixing-font-padding-in-compose-text-768cd232425b
        Text(
            text = data?.let { (dataUsageAnimate.value).toString() } ?: unknownLabel,
            style = textStyle.merge(
                TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None
                    )
                )
            ),
        )
    }

    CircularProgressIndicator(data = data,
        textContent = textContent,
        minValue = minValue,
        maxValue = maxValue,
        size = size,
        indicatorScaleFactor = indicatorScaleFactor,
        animationDuration = animationDuration,
        indicatorColor = indicatorColor,
        indicatorContainerColor = indicatorContainerColor,
        backgroundColor = backgroundColor
    )
}

/**
 * Implementation of circular progress indicator consisting of an inner circle containing the
 * measurement text ([data] and [subtitle]) and an outer circumferential arc showing [data].
 *
 * The measurement displayed on the arc is the angle corresponding to the value of the
 * data with respect to the maximum.
 *
 * The arc also has a background for improving visualization ([indicatorContainerColor]).
 * This background is an arc of 360 degrees.
 *
 * Based on the following implementation:
 * https://semicolonspace.com/circular-progressbar-android-compose/
 *
 * @param data Data to show
 * @param subtitle Subtitle of the data, placed below data
 * @param minValue Minimum value that the data variable can take (Zero by default)
 * @param maxValue Maximum value that the data variable can take (100 by default)
 * @param size Size of the whole element
 * @param indicatorScaleFactor Proportion (number between 0 and 0.5) that indicates the size of the
 * indicator
 * @param subtitleWidth Subtitle's text width. Recommended: size - (size * 3 * [indicatorScaleFactor]))
 * @param animationDuration Animation's duration in milliseconds
 * @param indicatorColor Color of the indicator
 * @param indicatorContainerColor Color of the container of the indicator.
 * @param backgroundColor Background color of the whole element
 * @param dataTextStyle TextStyle of the text that indicates the data
 * @param subtitleTextStyle TextStyle of the text that contains the subtitle
 */
@Composable
fun CircularProgressIndicator(
    data : Int?,
    subtitle : String,
    minValue : Int = 0,
    maxValue : Int = 100,
    size : Dp = 250.dp,
    indicatorScaleFactor : Float = 0.1f,
    subtitleWidth : Dp = 175.dp,
    animationDuration : Int = 1000,
    indicatorColor : Color = MaterialTheme.colorScheme.tertiary,
    indicatorContainerColor : Color = MaterialTheme.colorScheme.tertiaryContainer,
    backgroundColor : Color = MaterialTheme.colorScheme.background,
    dataTextStyle : TextStyle = MaterialTheme.typography.displayMedium,
    subtitleTextStyle : TextStyle = MaterialTheme.typography.titleMedium,
)
{
    val textContent : @Composable (State<Int>) -> Unit = { dataUsageAnimate ->

        val unknownLabel = stringResource(id = R.string.unknown_display)

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = data?.let { (dataUsageAnimate.value).toString() } ?: unknownLabel,
                style = dataTextStyle
            )
            Text(
                text = subtitle,
                style = subtitleTextStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(subtitleWidth)
            )
        }
    }

    CircularProgressIndicator(data = data,
        textContent = textContent,
        minValue = minValue,
        maxValue = maxValue,
        size = size,
        indicatorScaleFactor = indicatorScaleFactor,
        animationDuration = animationDuration,
        indicatorColor = indicatorColor,
        indicatorContainerColor = indicatorContainerColor,
        backgroundColor = backgroundColor
    )
}

/**
 * Implementation of circular progress indicator consisting of an inner circle containing the
 * measurement text ([textContent]) and an outer circumferential arc showing [data].
 *
 * The measurement displayed on the arc is the angle corresponding to the value of the
 * data with respect to the maximum.
 *
 * The arc also has a background for improving visualization ([indicatorContainerColor]).
 * This background is an arc of 360 degrees.
 *
 * Based on the following implementation:
 * https://semicolonspace.com/circular-progressbar-android-compose/
 *
 * @param data Data to show
 * @param textContent Function to show in the internal circle
 * @param minValue Minimum value that the data variable can take (Zero by default)
 * @param maxValue Maximum value that the data variable can take (100 by default)
 * @param size Size of the whole element
 * @param indicatorScaleFactor Proportion (number between 0 and 0.5) that indicates the size of the
 * indicator
 * @param animationDuration Animation's duration in milliseconds
 * @param indicatorColor Color of the indicator
 * @param indicatorContainerColor Color of the container of the indicator.
 * @param backgroundColor Background color of the whole element
 */
@Composable
private fun CircularProgressIndicator(data : Int?,
                                      textContent : @Composable (State<Int>) -> Unit,
                                      minValue : Int = 0,
                                      maxValue : Int = 100,
                                      size : Dp = 250.dp,
                                      indicatorScaleFactor : Float = 0.1f,
                                      animationDuration : Int = 1000,
                                      indicatorColor : Color = MaterialTheme.colorScheme.tertiary,
                                      indicatorContainerColor : Color = MaterialTheme.colorScheme.tertiaryContainer,
                                      backgroundColor : Color = MaterialTheme.colorScheme.background,
)
{
    // Checks
    require(maxValue > minValue)
    require(indicatorScaleFactor in 0f..0.5f)

    // Thickness of the indicator in Dp
    val indicatorThickness : Dp = size.times(indicatorScaleFactor)

    // Remembers the data value to update it
    var dataRemembered by remember { mutableStateOf(minValue) }

    // This is to animate the foreground indicator
    val dataUsageAnimate = animateIntAsState(
        targetValue = dataRemembered,
        animationSpec = tween(durationMillis = animationDuration)
    )

    // Convert the data to an angle. Max is 360
    val sweepAngle = min(((dataUsageAnimate.value - minValue).toFloat() / (maxValue - minValue)),
        1f) * 360

    // Start the animation
    LaunchedEffect(data)
    {
        dataRemembered = (data ?: minValue)
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

        textContent(dataUsageAnimate)
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun CircularProgressIndicatorPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            CircularProgressIndicator(data = 75)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun CircularProgressIndicatorPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            CircularProgressIndicator(data = 75)
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun CircularProgressIndicatorNullPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            CircularProgressIndicator(data = null)
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun CircularProgressIndicatorNullPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            CircularProgressIndicator(data = null)
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun CircularProgressIndicatorCustomPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            CircularProgressIndicator(
                data = 75,
                minValue = 10,
                maxValue = 30,
                size = 100.dp,
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun CircularProgressIndicatorCustomPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            CircularProgressIndicator(
                data = 20,
                minValue = 10,
                maxValue = 30,
                size = 300.dp,
                textStyle = MaterialTheme.typography.displayLarge
            )
        }
    }
}
@Preview(
    group = "Light Theme"
)
@Composable
fun CircularProgressIndicatorWithSubtitlePreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            CircularProgressIndicator(
                data = 75,
                subtitle = stringResource(id = R.string.stress)
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun CircularProgressIndicatorWithSubtitlePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            CircularProgressIndicator(
                data = 75,
                subtitle = stringResource(id = R.string.stress)
            )
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun CircularProgressIndicatorWithSubtitleNullPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            CircularProgressIndicator(
                data = null,
                subtitle = stringResource(id = R.string.stress)
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun CircularProgressIndicatorWithSubtitleNullPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            CircularProgressIndicator(
                data = null,
                subtitle = stringResource(id = R.string.stress)
            )
        }
    }
}

@Preview(
    group = "Light Theme"
)
@Composable
fun CircularProgressIndicatorWithSubtitleCustomPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            CircularProgressIndicator(
                data = 75,
                subtitle = stringResource(id = R.string.stress),
                minValue = 10,
                maxValue = 30,
                size = 100.dp,
                subtitleWidth = 70.dp,
                dataTextStyle = MaterialTheme.typography.headlineSmall,
                subtitleTextStyle = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(
    group = "Dark Theme"
)
@Composable
fun CircularProgressIndicatorWithSubtitleCustomPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            CircularProgressIndicator(
                data = 20,
                subtitle = stringResource(id = R.string.stress),
                minValue = 10,
                maxValue = 30,
                size = 300.dp,
                subtitleWidth = 210.dp,
                dataTextStyle = MaterialTheme.typography.displayLarge,
                subtitleTextStyle = MaterialTheme.typography.titleLarge
            )
        }
    }
}