package es.upm.bienestaremocional.app.data.alarm

import java.time.LocalDateTime

/**
 * Class with alarm data: the time when the alarm should be generated and the code of the pending
 * intent associated with the alarm
 * @see AlarmsAvailable
 * @see AlarmScheduler
 */
data class AlarmItem(val time: LocalDateTime, val code: Int)