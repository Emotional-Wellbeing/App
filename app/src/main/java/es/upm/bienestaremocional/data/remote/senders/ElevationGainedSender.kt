package es.upm.bienestaremocional.data.remote.senders

import androidx.health.connect.client.records.ElevationGainedRecord
import es.upm.bienestaremocional.utils.obtainTimestamp

/**
 * Object used to send [ElevationGainedRecord], usually through JSON files.
 * Elevation value is expressed on kilometers
 */
data class ElevationGainedSender(
    val startTime: Long,
    val endTime: Long,
    val elevation: Double
)
{
    companion object
    {
        fun ElevationGainedRecord.toSender(): ElevationGainedSender
        {
            val startTime = obtainTimestamp(startTime, startZoneOffset)
            val endTime = obtainTimestamp(endTime, endZoneOffset)
            val elevation = elevation.inKilometers
            return ElevationGainedSender(
                startTime = startTime,
                endTime = endTime,
                elevation = elevation
            )
        }
    }
}

