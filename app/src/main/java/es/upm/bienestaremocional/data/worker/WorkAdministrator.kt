package es.upm.bienestaremocional.data.worker

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo

/**
 * Manages task scheduling.
 * @see WorkAdministratorImpl
 */
interface WorkAdministrator {
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
     * Query workers status
     */
    fun queryWorkerStatus(): LiveData<List<WorkInfo>>
}