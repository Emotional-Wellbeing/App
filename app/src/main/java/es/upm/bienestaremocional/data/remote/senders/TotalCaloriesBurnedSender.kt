package es.upm.bienestaremocional.data.remote.senders

import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import es.upm.bienestaremocional.utils.obtainTimestamp

/**
 * Object used to send [TotalCaloriesBurnedRecord], usually through JSON files.
 * Energy value is expressed on kilocalories (kcal)
 */
data class TotalCaloriesBurnedSender(
    val startTime: Long,
    val endTime: Long,
    val energy: Double
) {
    companion object {
        fun TotalCaloriesBurnedRecord.toSender(): TotalCaloriesBurnedSender {
            val startTime = obtainTimestamp(startTime, startZoneOffset)
            val endTime = obtainTimestamp(endTime, endZoneOffset)
            return TotalCaloriesBurnedSender(
                startTime = startTime,
                endTime = endTime,
                energy = energy.inKilocalories
            )
        }
    }
}
