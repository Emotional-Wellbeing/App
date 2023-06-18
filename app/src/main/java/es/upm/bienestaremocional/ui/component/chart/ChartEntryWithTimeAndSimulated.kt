package es.upm.bienestaremocional.ui.component.chart

import com.patrykandpatrick.vico.core.entry.ChartEntry
import java.time.ZonedDateTime

class ChartEntryWithTimeAndSimulated(
    val time: ZonedDateTime,
    val simulated: Boolean,
    override val x: Float,
    override val y: Float,
) : ChartEntry {
    override fun withY(y: Float) = ChartEntryWithTimeAndSimulated(time, simulated,  x, y)
}
