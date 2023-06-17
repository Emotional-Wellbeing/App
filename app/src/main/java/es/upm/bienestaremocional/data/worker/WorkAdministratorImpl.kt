package es.upm.bienestaremocional.data.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkQuery
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
     * Schedules a repeatable request
     * @param workerClass Class associated with the worker to request
     * @param time Time to execute the request
     * @param tag Tag of the request
     * @param repeatInterval Duration that contains the interval between two requests
     * @param constraints Optional constraints that must be fulfilled to execute the request
     */
    private fun scheduleRequest(
        workerClass : Class<out ListenableWorker>,
        time: LocalDateTime,
        tag: String,
        repeatInterval: Duration,
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
                repeatInterval = repeatInterval)
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

    override fun scheduleDailyMorningNotificationWorker()
    {
        with(DailyMorningNotificationWorker)
        {
            scheduleRequest(
                workerClass = DailyMorningNotificationWorker::class.java,
                time = time,
                tag = tag,
                repeatInterval = repeatInterval
            )
        }
    }

    override fun cancelDailyMorningNotificationWorker() {
        cancelRequest(DailyMorningNotificationWorker.tag)
    }

    override fun scheduleDailyNightNotificationWorker() {
        with(DailyNightNotificationWorker)
        {
            scheduleRequest(
                workerClass = DailyNightNotificationWorker::class.java,
                time = time,
                tag = tag,
                repeatInterval = repeatInterval
            )
        }
    }

    override fun cancelDailyNightNotificationWorker() {
        cancelRequest(DailyNightNotificationWorker.tag)
    }

    override fun scheduleOneOffNotificationWorker()
    {
        with(OneOffNotificationWorker)
        {
            scheduleRequest(
                workerClass = OneOffNotificationWorker::class.java,
                time = time,
                tag = tag,
                repeatInterval = repeatInterval
            )
        }
    }

    override fun cancelOneOffNotificationWorker() {
        cancelRequest(OneOffNotificationWorker.tag)
    }

    override fun scheduleUploadWorker()
    {
        //Only execute upload job when battery is not low and network is available
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        with(UploadWorker)
        {
            scheduleRequest(workerClass = UploadWorker::class.java,
                time = time,
                tag = tag,
                repeatInterval = repeatInterval,
                constraints = constraints
            )
        }
    }


    override fun cancelUploadWorker() = cancelRequest(UploadWorker.tag)

    override fun queryWorkerStatus(): LiveData<List<WorkInfo>>
    {
        return workManager.getWorkInfosLiveData(
            WorkQuery.fromUniqueWorkNames(
                DailyMorningNotificationWorker.tag,
                DailyNightNotificationWorker.tag,
                OneOffNotificationWorker.tag,
                UploadWorker.tag
            )
        )
    }
}