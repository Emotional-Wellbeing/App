package es.upm.bienestaremocional.app.data.remote.senders

import androidx.health.connect.client.records.FloorsClimbedRecord
import es.upm.bienestaremocional.app.data.healthconnect.sources.FloorsClimbed
import es.upm.bienestaremocional.app.utils.obtainTimestamp

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