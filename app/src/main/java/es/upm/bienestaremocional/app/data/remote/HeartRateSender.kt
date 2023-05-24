package es.upm.bienestaremocional.app.data.remote

import androidx.health.connect.client.records.HeartRateRecord
import es.upm.bienestaremocional.app.data.remote.HeartRateSampleSender.Companion.toSender
import es.upm.bienestaremocional.app.utils.obtainTimestamp

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