package es.upm.bienestaremocional.app.data.alarm

import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Low-level interface to manage alarm scheduling. You should use AlarmManager instead of this class
 * @see AlarmManager
 * @see AndroidAlarmScheduler
 */
interface AlarmScheduler {

    /**
     * Schedule a single alarm in Android system
     */
    fun schedule(alarm: AlarmItem)

    /**
     * Schedule a list of alarms in Android system
     */
    fun schedule(alarms: List<AlarmItem>)

    /**
     * Cancel a single alarm in Android system
     */
    fun cancel(alarm: AlarmItem)

    /**
     * Cancel a list of alarms in Android system
     */
    fun cancel(alarms: List<AlarmItem>)

    /**
     * Checks if exact alarms can be scheduled
     * @return The result of the query
     */
    fun canScheduleExactly(): Boolean

    /**
     * Open the dialog of Android system to request the permission of exact alarms to the user,
     * only needed for Android 12 or newer
     */
    @RequiresApi(Build.VERSION_CODES.S)
    fun requestPermissions()
}