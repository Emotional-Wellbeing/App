package es.upm.bienestaremocional.app.data.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.*
import es.upm.bienestaremocional.app.data.notification.NotificationItem
import es.upm.bienestaremocional.app.data.notification.NotificationsAvailable
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Implementation of WorkAdministrator
 * @see WorkAdministrator
 */
class WorkAdministratorImpl(
    context: Context,
    private val logTag: String
) : WorkAdministrator
{
    private val workManager = WorkManager.getInstance(context)

    /**
     * Schedules a daily request
     * @param workerClass Class associated with the worker to request
     * @param time Time to execute the request
     * @param tag Tag of the request
     * @param constraints Optional constraints that must be fulfilled to execute the request
     */
    private fun scheduleDailyRequest(workerClass : Class<out ListenableWorker>,
                                     time: LocalDateTime,
                                     tag: String,
                                     constraints: Constraints? = null,
    )
    {
        Log.d(logTag,"Setting daily request with tag: $tag")

        // Compute trigger for the next one. If the hour has passed, schedule it to the following day
        val now = ZonedDateTime.now()

        var timeOfFirstExecution = time.atZone(ZoneId.systemDefault())

        if (timeOfFirstExecution.isBefore(now))
            timeOfFirstExecution = timeOfFirstExecution.plusDays(1)

        val offset = Duration.between(now,timeOfFirstExecution)

        // Build request. If we have received constants, set them
        val requestBuilder = PeriodicWorkRequest.Builder(
                workerClass = workerClass,
                repeatInterval = Duration.ofHours(24))
            .addTag(tag)
            .setInitialDelay(offset)

        constraints?.let {
            requestBuilder.setConstraints(it)
        }

        workManager.enqueueUniquePeriodicWork(
            tag,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            requestBuilder.build()
        )

        Log.d(logTag, "The daily request shall be triggered at $timeOfFirstExecution for first time")
    }

    private fun cancelRequest(tag: String)
    {
        workManager.cancelUniqueWork(tag)
        Log.d(logTag, "The daily request with tag $tag has been cancelled")
    }

    override fun schedule(notifications: List<NotificationItem>) =
        notifications.forEach {
            scheduleDailyRequest(
                workerClass = NotificationWorker::class.java,
                time = it.time,
                tag = it.tag
            )
        }

    override fun cancel(notifications: List<NotificationItem>) =
        notifications.forEach { cancelRequest(it.tag) }

    override fun scheduleUploadWorker()
    {
        //Only execute upload job when battery is not low and network is available
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        scheduleDailyRequest(workerClass = UploadWorker::class.java,
            time = UploadWorker.time,
            tag = UploadWorker.tag,
            constraints = constraints
        )
    }


    override fun cancelUploadWorker() = cancelRequest(UploadWorker.tag)

    override fun queryWorkerStatus(): LiveData<List<WorkInfo>>
    {
        return workManager.getWorkInfosLiveData(WorkQuery.fromUniqueWorkNames(
            NotificationsAvailable.allNotifications.map { it.tag } + UploadWorker.tag
        ))
    }
}