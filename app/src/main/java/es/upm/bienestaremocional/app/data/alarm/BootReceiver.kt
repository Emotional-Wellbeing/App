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
        if (intent.action == "android.intent.action.BOOT_COMPLETED")
        {
            Log.d("BienestarEmocionalApp","Scheduling alarms after reboot")
            val alarms = MainApplication.appSettings.getAlarmFrequencyValue().alarms
            MainApplication.alarmScheduler.setAlarms(alarms)
        }
    }
}