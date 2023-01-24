package es.upm.bienestaremocional.app.ui.notification.alarm

import java.time.LocalTime

/**
 * Class with alarm data: the time when the alarm should be generated and the code of the pending
 * intent associated with the alarm
 * @see AlarmsAvailable
 * @see AlarmScheduler
 */
data class Alarm(val localTime: LocalTime, val code: Int)