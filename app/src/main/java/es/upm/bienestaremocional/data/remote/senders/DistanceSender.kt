package es.upm.bienestaremocional.data.remote.senders

import androidx.health.connect.client.records.DistanceRecord
import es.upm.bienestaremocional.utils.obtainTimestamp

/**
 * Object used to send [DistanceRecord], usually through JSON files.
 * Distance value is expressed on kilometers
 */
data class DistanceSender(
    val startTime: Long,
    val endTime: Long,
    val distance: Double
) {
    companion object {
        fun DistanceRecord.toSender(): DistanceSender {
            val startTime = obtainTimestamp(startTime, startZoneOffset)
            val endTime = obtainTimestamp(endTime, endZoneOffset)
            val distance = distance.inKilometers
            return DistanceSender(
                startTime = startTime,
                endTime = endTime,
                distance = distance
            )
        }
    }
}
