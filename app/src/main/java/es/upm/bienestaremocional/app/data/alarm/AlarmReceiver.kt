package es.upm.bienestaremocional.app.data.alarm

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
    override fun onReceive(context: Context?, intent: Intent?)
    {
        Log.d("BienestarEmocionalApp","An alarm was triggered")
        intent?.let { i ->
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