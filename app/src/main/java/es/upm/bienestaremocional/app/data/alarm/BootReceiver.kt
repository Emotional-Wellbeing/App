package es.upm.bienestaremocional.app.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import es.upm.bienestaremocional.app.MainApplication

/**
 * This receiver is executed when the device boots again to schedule alarms (they disappear on shutdown)
 */
class BootReceiver : BroadcastReceiver()
{
    /*
    * restart reminders alarms when user's device reboots
    * */
    override fun onReceive(context: Context, intent: Intent)
    {
        when(intent.action) {
            "android.intent.action.BOOT_COMPLETED" -> {
                Log.d(MainApplication.logTag, "Scheduling alarms after reboot")
                val alarmScheduler = MainApplication.alarmScheduler
                if (alarmScheduler.canScheduleExactly())
                {
                    val alarms = MainApplication.appSettings.getAlarmFrequencyValue().alarmItems
                    alarmScheduler.schedule(alarms)
                }

            }
        }
    }
}