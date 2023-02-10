package es.upm.bienestaremocional.app.data.alarm

import android.app.AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import es.upm.bienestaremocional.app.MainApplication

/**
 * This receiver is executed when an alarm is produced
 */
class AlarmReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent)
    {
        when (intent.action)
        {
            ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED ->
            {
                //permission granted, so cancel previous and re-schedule all alarms
                val alarms = MainApplication.appSettings.getAlarmFrequencyValue().alarmItems
                MainApplication.alarmScheduler.cancel(alarms)
                MainApplication.alarmScheduler.schedule(alarms)
            }
            "alarm" ->
            {
                Log.d("BienestarEmocionalApp","An alarm was triggered")
                intent.let { i ->
                    val alarmCode = i.extras?.getInt("alarm_code")
                    alarmCode?.let { ac ->
                        val alarmItem = AlarmsAvailable.decode(ac)
                        alarmItem?.let { ai ->
                            Log.d("BienestarEmocionalApp","Re-scheduling alarm ${ai.code}")
                            MainApplication.alarmScheduler.schedule(ai)
                        }
                    }
                }
                MainApplication.notificationSender.showQuestionnaireNotification()
            }
        }

    }
}