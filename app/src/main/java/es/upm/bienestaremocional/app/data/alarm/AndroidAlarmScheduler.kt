package es.upm.bienestaremocional.app.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Class that implement the functionality of schedule alarms
 */
class AndroidAlarmScheduler(private val context: Context, private val receiver: Class<*>?) : AlarmScheduler
{
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun makePendingIntent(alarmCode: Int): PendingIntent?
    {
        val alarmIntent = Intent(context, receiver)
        alarmIntent.putExtra("alarm_code",alarmCode)
        return PendingIntent.getBroadcast(
            context,
            alarmCode,
            alarmIntent,
             PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun schedule(alarm: AlarmItem)
    {
        Log.d("BienestarEmocionalApp","Setting alarm ${alarm.code}")
        //compute trigger for the next one. If the hour has passed, schedule it to the following day
        val now = ZonedDateTime.now()

        var alarmTime = alarm.time.atZone(ZoneId.systemDefault())

        if (alarmTime.isBefore(now))
            alarmTime = alarmTime.plusDays(1)

        val trigger = alarmTime.toEpochSecond() * 1000

        val pendingIntent = makePendingIntent(alarm.code)
        pendingIntent?.let {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, //AlarmManager.RTC
                trigger,
                it
            )
        }
        Log.d("BienestarEmocionalApp", "The alarm shall be triggered at $trigger ")
    }

    override fun schedule(alarms: List<AlarmItem>)
    {
        for (alarm in alarms)
        {
            schedule(alarm)
        }
    }

    override fun cancel(alarm: AlarmItem)
    {
        val pendingIntent = makePendingIntent(alarm.code)
        pendingIntent?.let {
            alarmManager.cancel(it)
            Log.d("BienestarEmocionalApp", "The alarm with code ${alarm.code} has been cancelled")
        }
    }

    override fun cancel(alarms: List<AlarmItem>)
    {
        for (alarm in alarms)
        {
            Log.d("BienestarEmocionalApp","Cancelling alarm ${alarm.code}")
            cancel(alarm)
        }
    }
}