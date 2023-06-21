package es.upm.bienestaremocional.data.worker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.work.WorkInfo

/**
 * Manages task scheduling.
 * @see WorkAdministratorImpl
 */
interface WorkAdministrator
{
    /**
     * Schedule [DailyMorningNotificationWorker]
     */
    fun scheduleDailyMorningNotificationWorker()

    /**
     * Cancel [DailyMorningNotificationWorker]
     */
    fun cancelDailyMorningNotificationWorker()

    /**
     * Schedule [DailyNightNotificationWorker]
     */
    fun scheduleDailyNightNotificationWorker()

    /**
     * Cancel [DailyNightNotificationWorker]
     */
    fun cancelDailyNightNotificationWorker()

    /**
     * Schedule [OneOffNotificationWorker]
     */
    fun scheduleOneOffNotificationWorker()

    /**
     * Cancel [OneOffNotificationWorker]
     */
    fun cancelOneOffNotificationWorker()

    /**
     * Schedule [UploadWorker]
     */
    fun scheduleUploadWorker()

    /**
     * Cancel [UploadWorker]
     */
    fun cancelUploadWorker()

    /**
     * Schedule [UploadPhoneDataWorker]
     */
    fun scheduleUploadPhoneDataWorker()

    /**
     * Cancel [UploadPhoneDataWorker]
     */
    fun cancelUploadPhoneDataWorker()

    /**
     * Schedule [UploadTrafficDataWorker]
     */
    fun scheduleUploadTrafficDataWorker()

    /**
     * Cancel [UploadTrafficDataWorker]
     */
    fun cancelUploadTrafficDataWorker()

    /**
     * Schedule [UploadUsageInfoWorker]
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun scheduleUploadUsageInfoWorker()

    /**
     * Cancel [UploadUsageInfoWorker]
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun cancelUploadUsageInfoWorker()

    /**
     * Query workers status
     */
    fun queryWorkerStatus(): LiveData<List<WorkInfo>>
}