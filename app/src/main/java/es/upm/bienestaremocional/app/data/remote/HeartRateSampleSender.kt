package es.upm.bienestaremocional.app.data.remote

import androidx.health.connect.client.records.HeartRateRecord
import es.upm.bienestaremocional.app.utils.obtainTimestamp

/**
 * Object used to send [HeartRateRecord.Sample], usually through JSON files.
 */
data class HeartRateSampleSender(
    val timestamp: Long,
    val beatsPerMinute : Long
)
{
    companion object
    {
        fun HeartRateRecord.Sample.toSender(): HeartRateSampleSender
        {
            return HeartRateSampleSender(
                timestamp = obtainTimestamp(time,null),
                beatsPerMinute = beatsPerMinute
            )
        }
    }
}