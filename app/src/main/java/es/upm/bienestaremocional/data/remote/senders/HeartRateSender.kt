package es.upm.bienestaremocional.data.remote.senders

import androidx.health.connect.client.records.HeartRateRecord
import es.upm.bienestaremocional.data.remote.senders.HeartRateSampleSender.Companion.toSender
import es.upm.bienestaremocional.utils.obtainTimestamp

/**
 * Object used to send [HeartRateRecord], usually through JSON files.
 */
data class HeartRateSender(
    val startTime: Long,
    val endTime: Long,
    val samples : List<HeartRateSampleSender>
)
{
    companion object
    {
        fun HeartRateRecord.toSender(): HeartRateSender
        {
            val startTime = obtainTimestamp(startTime, startZoneOffset)
            val endTime = obtainTimestamp(endTime, endZoneOffset)
            val samples = samples.map { it.toSender() }
            return HeartRateSender(
                startTime = startTime,
                endTime = endTime,
                samples = samples
            )
        }
    }
}