package es.upm.bienestaremocional.data.remote.senders

import androidx.health.connect.client.records.StepsRecord
import es.upm.bienestaremocional.utils.obtainTimestamp

/**
 * Object used to send [StepsRecord], usually through JSON files.
 */
data class StepsSender(
    val startTime: Long,
    val endTime: Long,
    val steps: Long
)
{
    companion object
    {
        fun StepsRecord.toSender(): StepsSender
        {
            val startTime = obtainTimestamp(startTime, startZoneOffset)
            val endTime = obtainTimestamp(endTime, endZoneOffset)
            return StepsSender(
                startTime = startTime,
                endTime = endTime,
                steps = count
            )
        }
    }
}
