package es.upm.bienestaremocional.app.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import es.upm.bienestaremocional.app.data.alarm.AlarmManager
import javax.inject.Inject
import javax.inject.Named

/**
 * This receiver is executed when the device boots again to schedule alarms (they disappear on shutdown)
 */
@AndroidEntryPoint
class BootReceiver : BroadcastReceiver()
{
    @Inject
    lateinit var alarmManager: AlarmManager

    @Inject
    @Named("logTag")
    lateinit var logTag: String

    /**
     * restart reminders alarms when user's device reboots
     */
    override fun onReceive(context: Context, intent: Intent)
    {
        if(intent.action == Intent.ACTION_BOOT_COMPLETED)
        {
            Log.d(logTag, "Scheduling alarms after reboot")
            alarmManager.reScheduleAlarms()
        }
    }
}