package es.upm.bienestaremocional.data.worker

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo

/**
 * Manages task scheduling.
 * @see WorkAdministratorImpl
 */
interface WorkAdministrator
{
    /**
     * Schedule upload worker
     */
    fun scheduleDailyMorningNotificationWorker()

    /**
     * Cancel upload worker
     */
    fun cancelDailyMorningNotificationWorker()

    /**
     * Schedule upload worker
     */
    fun scheduleDailyNightNotificationWorker()

    /**
     * Cancel upload worker
     */
    fun cancelDailyNightNotificationWorker()

    /**
     * Schedule upload worker
     */
    fun scheduleOneOffNotificationWorker()

    /**
     * Cancel upload worker
     */
    fun cancelOneOffNotificationWorker()

    /**
     * Schedule upload worker
     */
    fun scheduleUploadWorker()

    /**
     * Cancel upload worker
     */
    fun cancelUploadWorker()

    /**
     * Query workers status
     */
    fun queryWorkerStatus(): LiveData<List<WorkInfo>>
}