package es.upm.bienestaremocional.data.remote.senders

import androidx.health.connect.client.records.FloorsClimbedRecord
import es.upm.bienestaremocional.data.healthconnect.sources.FloorsClimbed
import es.upm.bienestaremocional.utils.obtainTimestamp

/**
 * Object used to send [FloorsClimbed], usually through JSON files.
 */
data class FloorsClimbedSender(
    val startTime: Long,
    val endTime: Long,
    val floors: Double
)
{
    companion object
    {
        fun FloorsClimbedRecord.toSender(): FloorsClimbedSender
        {
            val startTime = obtainTimestamp(startTime, startZoneOffset)
            val endTime = obtainTimestamp(endTime, endZoneOffset)
            return FloorsClimbedSender(
                startTime = startTime,
                endTime = endTime,
                floors = floors
            )
        }
    }
}