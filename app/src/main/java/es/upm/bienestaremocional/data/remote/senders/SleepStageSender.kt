package es.upm.bienestaremocional.data.remote.senders

import androidx.health.connect.client.records.SleepStageRecord
import es.upm.bienestaremocional.utils.obtainTimestamp

/**
 * Object used to send [SleepStageRecord], usually through JSON files.
 */
data class SleepStageSender(
    val startTime: Long,
    val endTime: Long,
    val stage: String
)
{
    companion object
    {
        fun SleepStageRecord.toSender(): SleepStageSender
        {
            val startTime = obtainTimestamp(startTime, startZoneOffset)
            val endTime = obtainTimestamp(endTime, endZoneOffset)
            val stage = when(stage)
            {
                SleepStageRecord.STAGE_TYPE_UNKNOWN -> "unknown"
                SleepStageRecord.STAGE_TYPE_AWAKE -> "awake"
                SleepStageRecord.STAGE_TYPE_SLEEPING -> "sleeping"
                SleepStageRecord.STAGE_TYPE_OUT_OF_BED -> "out_of_bed"
                SleepStageRecord.STAGE_TYPE_LIGHT -> "light"
                SleepStageRecord.STAGE_TYPE_DEEP -> "deep"
                SleepStageRecord.STAGE_TYPE_REM -> "rem"
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