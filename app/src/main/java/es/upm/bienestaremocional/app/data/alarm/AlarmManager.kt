package es.upm.bienestaremocional.app.data.alarm

/**
 * High-level interface to schedule and cancel alarms
 * @see AndroidAlarmManager
 */
interface AlarmManager
{
    /**
     * Cancel the alarms established by the user
     */
    fun cancelAlarms()

    /**
     * Reschedule the alarms established by the user
     */
    fun reScheduleAlarms()

    /**
     * Reschedule the alarm using the id present on the intent
     */
    fun decodeAndSchedule(id: Int)
}