package es.upm.bienestaremocional.data.remote.senders

import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_AWAKE
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_DEEP
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_LIGHT
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_OUT_OF_BED
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_REM
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_SLEEPING
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_UNKNOWN
import androidx.health.connect.client.records.SleepSessionRecord.Stage
import es.upm.bienestaremocional.utils.obtainTimestamp

/**
 * Object used to send [Stage], usually through JSON files.
 */
data class SleepStageSender(
    val startTime: Long,
    val endTime: Long,
    val stage: String
) {
    companion object {
        fun Stage.toSender(): SleepStageSender {
            val startTime = obtainTimestamp(startTime, null)
            val endTime = obtainTimestamp(endTime, null)
            val stage = when (stage) {
                STAGE_TYPE_UNKNOWN -> "unknown"
                STAGE_TYPE_AWAKE -> "awake"
                STAGE_TYPE_SLEEPING -> "sleeping"
                STAGE_TYPE_OUT_OF_BED -> "out_of_bed"
                STAGE_TYPE_LIGHT -> "light"
                STAGE_TYPE_DEEP -> "deep"
                STAGE_TYPE_REM -> "rem"
                else -> "unknown"
            }

            return SleepStageSender(
                startTime = startTime,
                endTime = endTime,
                stage = stage
            )
        }
    }
}