package es.upm.bienestaremocional.data.remote.senders

import androidx.health.connect.client.records.WeightRecord
import es.upm.bienestaremocional.utils.obtainTimestamp

/**
 * Object used to send [WeightRecord], usually through JSON files.
 * Distance value is expressed on kilograms
 */
data class WeightSender(
    val timestamp: Long,
    val weight: Double
)
{
    companion object
    {
        fun WeightRecord.toSender(): WeightSender
        {
            val time = obtainTimestamp(time, zoneOffset)
            return WeightSender(
                timestamp = time,
                weight = weight.inKilograms
            )
        }
    }
}
