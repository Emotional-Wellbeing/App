package es.upm.bienestaremocional.data.worker

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import es.upm.bienestaremocional.data.notification.NotificationItem

/**
 * Manages task scheduling.
 * @see WorkAdministratorImpl
 */
interface WorkAdministrator
{
    /**
     * Schedule notifications
     */
    fun schedule(notifications: List<NotificationItem>)

    /**
     * Cancel the display of notifications
     */
    fun cancel(notifications: List<NotificationItem>)

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