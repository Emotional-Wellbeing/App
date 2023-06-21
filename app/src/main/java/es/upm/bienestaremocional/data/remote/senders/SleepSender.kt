package es.upm.bienestaremocional.data.remote.senders

import es.upm.bienestaremocional.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.data.remote.senders.SleepStageSender.Companion.toSender
import es.upm.bienestaremocional.utils.obtainTimestamp

/**
 * Object used to send [SleepSessionData], usually through JSON files.
 */
data class SleepSender(
    val startTime: Long,
    val endTime: Long,
    val stages: List<SleepStageSender>
) {
    companion object {
        fun SleepSessionData.toSender(): SleepSender {
            val startTime = obtainTimestamp(startTime, startZoneOffset)
            val endTime = obtainTimestamp(endTime, endZoneOffset)
            val stages = stages.map { it.toSender() }
            return SleepSender(
                startTime = startTime,
                endTime = endTime,
                stages = stages
            )
        }
    }

}