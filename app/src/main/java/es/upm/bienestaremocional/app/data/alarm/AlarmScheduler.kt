package es.upm.bienestaremocional.app.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

/**
 * Class that implement the functionality of schedule alarms
 */
class AlarmScheduler(private val context: Context, private val receiver: Class<*>?)
{
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun makePendingIntent(requestCode: Int): PendingIntent?
    {
        val alarmIntent = Intent(context, receiver)
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            alarmIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun setAlarm(alarm: Alarm)
    {
        //compute trigger for the next one. If the hour has passed, schedule it to the following day
        val calendar = Calendar.getInstance(Locale.getDefault())

        calendar.set(Calendar.HOUR_OF_DAY,alarm.localTime.hour)
        calendar.set(Calendar.MINUTE,alarm.localTime.minute)
        calendar.set(Calendar.SECOND,alarm.localTime.second)
        if (System.currentTimeMillis() >= calendar.timeInMillis)
            calendar.add(Calendar.DATE,1)
        val trigger = calendar.timeInMillis

        val pendingIntent = makePendingIntent(alarm.code)
        pendingIntent?.let {
            alarmManager.setRepeating(
                AlarmManager.RTC,
                trigger,
                (1000 * 60 * 60 * 24), //one day
                it
            )
        }
        Log.d("BienestarEmocionalApp", "The alarm shall be triggered for the first time at $trigger " +
                "and thereafter once a day")
    }

    private fun cancelAlarm(alarm: Alarm)
    {
        val pendingIntent = makePendingIntent(alarm.code)
        pendingIntent?.let {
            alarmManager.cancel(it)
            Log.d("BienestarEmocionalApp", "The alarm with code ${alarm.code} has been cancelled")
        }
    }

    fun setAlarms(alarms: List<Alarm>)
    {
        //cancel previous alarms
        Log.d("BienestarEmocionalApp","Canceling previous alarms")
        for (alarm in AlarmsAvailable.allAlarms)
            cancelAlarm(alarm)

        //set new alarms
        for (alarm in alarms)
        {
            Log.d("BienestarEmocionalApp","Setting alarm ${alarm.code}")
            setAlarm(alarm)
        }
    }
}