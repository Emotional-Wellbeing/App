package es.upm.bienestaremocional.ui.component.chart

import android.graphics.RectF
import com.patrykandpatrick.vico.core.component.Component
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.context.DrawContext
import com.patrykandpatrick.vico.core.extension.half
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.Marker.EntryModel
/**
 * Implements a Marker only with an indicator drawn at a given point belonging to the data entry.
 * @param indicator The indicator itself.
 * @param indicatorSizeDp The indicator size (in dp).
 * @param onApplyEntryColor Apply the color associated with a given data entry to [Component].
 * @see MarkerComponent
 */
class SimpleMarkerComponent(
    private val indicator: Component,
    private val indicatorSizeDp: Float = 0f,
    private val onApplyEntryColor: ((entryColor: Int) -> Unit)
) : Marker
{

    /**
     * Draws the marker.
     * @param context the [DrawContext] used to draw the marker.
     * @param bounds the bounds in which the marker is drawn.
     * @param markedEntries a list of [EntryModel]s representing the entries to which the marker refers.
     */
    override fun draw(context: DrawContext, bounds: RectF, markedEntries: List<EntryModel>)
    {
        with(context)
        {
            val halfIndicatorSize = indicatorSizeDp.half.pixels

            markedEntries.forEach { model ->
                onApplyEntryColor.invoke(model.color)
                indicator.draw(
                    context,
                    model.location.x - halfIndicatorSize,
                    model.location.y - halfIndicatorSize,
                    model.location.x + halfIndicatorSize,
                    model.location.y + halfIndicatorSize,
                )
            }
        }
    }
}
