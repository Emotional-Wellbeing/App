package es.upm.bienestaremocional.ui.component.chart

import android.graphics.Typeface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.overlayingComponent
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.chart.segment.SegmentProperties
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.extension.copyColor
import com.patrykandpatrick.vico.core.marker.Marker

/**
 * Implements Marker to use in Charts when user taps screen, based on the official example
 */
@Composable
fun rememberMarker(): Marker {
    // Bubble background
    // Color background
    val labelBackgroundColor = MaterialTheme.colorScheme.surface
    // Shape background component with their color and radius
    val labelBackground = remember(labelBackgroundColor) {
        ShapeComponent(labelBackgroundShape, labelBackgroundColor.toArgb()).setShadow(
            radius = LABEL_BACKGROUND_SHADOW_RADIUS,
            dy = LABEL_BACKGROUND_SHADOW_DY,
            applyElevationOverlay = true,
        )
    }
    // Label inside bubble
    val label = textComponent(
        background = labelBackground,
        lineCount = LABEL_LINE_COUNT,
        padding = labelPadding,
        typeface = Typeface.MONOSPACE,
    )

    // Indicator elements (concentric circles)
    // Inner ring
    val indicatorInnerComponent = shapeComponent(
        shape = Shapes.pillShape,
        color = MaterialTheme.colorScheme.surface
    )
    // Central ring
    val indicatorCenterComponent = shapeComponent(
        shape = Shapes.pillShape,
        color = MaterialTheme.colorScheme.background
    )
    // Outside ring
    val indicatorOuterComponent = shapeComponent(
        shape = Shapes.pillShape,
        color = MaterialTheme.colorScheme.background
    )
    // Full indicator component
    val indicator = overlayingComponent(
        outer = indicatorOuterComponent,
        inner = overlayingComponent(
            outer = indicatorCenterComponent,
            inner = indicatorInnerComponent,
            innerPaddingAll = indicatorInnerAndCenterComponentPaddingValue,
        ),
        innerPaddingAll = indicatorCenterAndOuterComponentPaddingValue,
    )

    // Guideline parallel of y axis
    val guideline = lineComponent(
        // Transparency inversely proportional to alpha (0% full transparent, 100% opaque)
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = GUIDELINE_ALPHA),
        // Width of the line
        thickness = guidelineThickness,
        // Shape of the guideline (dashed line)
        shape = guidelineShape,
    )

    return remember(label, indicator, guideline) {
        object : MarkerComponent(label, indicator, guideline) {
            init {
                // Indicator size set at start
                indicatorSizeDp = INDICATOR_SIZE_DP
                // Color is changed to ensure that is the same as graphic color (more util in bar chart)
                onApplyEntryColor = { entryColor ->
                    indicatorOuterComponent.color = entryColor.copyColor(
                        INDICATOR_OUTER_COMPONENT_ALPHA
                    )
                    with(indicatorCenterComponent) {
                        color = entryColor
                        setShadow(
                            radius = INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS,
                            color = entryColor
                        )
                    }
                }
            }

            // Set indicator in the top of the chart, near upper axis
            override fun getInsets(
                context: MeasureContext,
                outInsets: Insets,
                segmentProperties: SegmentProperties
            ) =
                with(context) {
                    outInsets.top =
                        label.getHeight(context) + labelBackgroundShape.tickSizeDp.pixels +
                                LABEL_BACKGROUND_SHADOW_RADIUS.pixels * SHADOW_RADIUS_MULTIPLIER -
                                LABEL_BACKGROUND_SHADOW_DY.pixels
                }
        }
    }
}

/**
 * Implements a simply marker, used in permanent ones
 */
@Composable
fun rememberSimpleMarker(): Marker {
    // Indicator elements (concentric circles)
    // Inner ring
    val indicatorInnerComponent = shapeComponent(
        shape = Shapes.pillShape,
        color = MaterialTheme.colorScheme.surface
    )
    // Outher ring
    val indicatorCenterComponent = shapeComponent(
        shape = Shapes.pillShape,
        color = MaterialTheme.colorScheme.background
    )
    // Full indicator component
    val indicator = overlayingComponent(
        outer = indicatorCenterComponent,
        inner = indicatorInnerComponent,
        innerPaddingAll = indicatorInnerAndCenterComponentPaddingValue,
    )

    val indicatorSizeDp = INDICATOR_SIZE_DP - 2 * indicatorCenterAndOuterComponentPaddingValue.value

    val onApplyEntryColor: (Int) -> Unit = { entryColor ->
        indicatorCenterComponent.color = entryColor.copyColor(
            INDICATOR_OUTER_COMPONENT_ALPHA
        )
        with(indicatorCenterComponent) {
            color = entryColor
        }
    }

    return remember(indicator, indicatorSizeDp, onApplyEntryColor) {
        SimpleMarkerComponent(indicator, indicatorSizeDp, onApplyEntryColor)
    }
}

// Marker consts

private const val LABEL_BACKGROUND_SHADOW_RADIUS = 4f
private const val LABEL_BACKGROUND_SHADOW_DY = 2f
private const val LABEL_LINE_COUNT = 1
private const val GUIDELINE_ALPHA = .2f
private const val INDICATOR_SIZE_DP = 36f
private const val INDICATOR_OUTER_COMPONENT_ALPHA = 32
private const val INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS = 12f
private const val GUIDELINE_DASH_LENGTH_DP = 8f
private const val GUIDELINE_GAP_LENGTH_DP = 4f
private const val SHADOW_RADIUS_MULTIPLIER = 1.3f

private val labelBackgroundShape = MarkerCorneredShape(Corner.FullyRounded)
private val labelHorizontalPaddingValue = 8.dp
private val labelVerticalPaddingValue = 4.dp
private val labelPadding = dimensionsOf(labelHorizontalPaddingValue, labelVerticalPaddingValue)
private val indicatorInnerAndCenterComponentPaddingValue = 5.dp
private val indicatorCenterAndOuterComponentPaddingValue = 10.dp
private val guidelineThickness = 2.dp
private val guidelineShape =
    DashedShape(Shapes.pillShape, GUIDELINE_DASH_LENGTH_DP, GUIDELINE_GAP_LENGTH_DP)