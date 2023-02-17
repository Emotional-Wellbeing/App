package es.upm.bienestaremocional.app.data.alarm

import android.app.AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.ui.notification.Notification
import javax.inject.Inject
import javax.inject.Named

/**
 * This receiver is executed when an alarm is produced
 */
@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver()
{
    @Inject
    lateinit var appSettings: AppSettingsInterface

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    @Inject
    lateinit var notification: Notification

    @Inject
    @Named("logTag")
    lateinit var logTag: String


    override fun onReceive(context: Context, intent: Intent)
    {
        when (intent.action)
        {
            ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED ->
            {
                //permission granted, so cancel previous and re-schedule all alarms
                val alarms = appSettings.getAlarmFrequencyValue().alarmItems
                alarmScheduler.cancel(alarms)
                alarmScheduler.schedule(alarms)
            }
            "alarm" ->
            {
                Log.d(logTag,"An alarm was triggered")
                val alarmCode = intent.extras?.getInt("alarm_code")
                alarmCode?.let { ac ->
                    val alarmItem = AlarmsAvailable.decode(ac)
                    alarmItem?.let { ai ->
                        Log.d(logTag,"Re-scheduling alarm ${ai.code}")
                        alarmScheduler.schedule(ai)
                    }
                }
                notification.showQuestionnaireNotification()
            }
        }
    }
}