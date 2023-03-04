package es.upm.bienestaremocional.app.data.alarm

import android.util.Log
import es.upm.bienestaremocional.app.data.settings.AppSettings

/**
 * Implementation of AlarmManager
 * @see AlarmManager
 */
class AndroidAlarmManager(
    private val scheduler: AlarmScheduler,
    private val appSettings: AppSettings,
    private val logTag : String
): AlarmManager
{
    override fun cancelAlarms() {
        val alarms = appSettings.getAlarmFrequencyValue().alarmItems
        scheduler.cancel(alarms)
    }

    override fun reScheduleAlarms() {
        val alarms = appSettings.getAlarmFrequencyValue().alarmItems
        scheduler.schedule(alarms)
    }

    override fun decodeAndSchedule(id: Int)
    {
        val alarmItem = AlarmsAvailable.decode(id)
        alarmItem?.let { ai ->
            Log.d(logTag,"Re-scheduling alarm ${ai.code}")
            scheduler.schedule(ai)
        }
    }

}