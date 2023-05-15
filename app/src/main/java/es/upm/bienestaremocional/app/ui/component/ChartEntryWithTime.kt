package es.upm.bienestaremocional.app.ui.component

import com.patrykandpatrick.vico.core.entry.ChartEntry
import java.time.ZonedDateTime

class ChartEntryWithTime(
    val time: ZonedDateTime,
    override val x: Float,
    override val y: Float,
) : ChartEntry {
    override fun withY(y: Float) = ChartEntryWithTime(time, x, y)
}
