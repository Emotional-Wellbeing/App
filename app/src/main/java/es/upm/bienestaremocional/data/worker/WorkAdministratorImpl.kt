package es.upm.bienestaremocional.data.worker

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkQuery
import es.upm.bienestaremocional.data.RemoteConstants
import es.upm.bienestaremocional.utils.formatHoursMinutes
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Implementation of WorkAdministrator
 * @see WorkAdministrator
 */
class WorkAdministratorImpl(
    context: Context,
    private val logTag: String
) : WorkAdministrator {
    private val workManager = WorkManager.getInstance(context)

    /**
     * Schedules a repeatable request
     * @param workerClass Class associated with the worker to request
     * @param schedulable Info to schedule the request
     * @param constraints Optional constraints that must be fulfilled to execute the request
     */
    private fun scheduleRequest(
        workerClass: Class<out ListenableWorker>,
        schedulable: Schedulable,
        constraints: Constraints? = null,
    ) {
        Log.d(logTag, "Setting daily request with tag: ${schedulable.tag}")

        // Compute trigger for the next one. If the hour has passed, schedule it to the following day

        val now = ZonedDateTime.now()

        val offset: Duration = schedulable.initialTime?.let {

            var timeOfFirstExecution = it.atZone(ZoneId.systemDefault())

            if (timeOfFirstExecution.isBefore(now))
                timeOfFirstExecution = timeOfFirstExecution.plusDays(1)

            Duration.between(now, timeOfFirstExecution)
        } ?: Duration.between(now, now)


        // Build request. If we have received constants, set them

        val requestBuilder = PeriodicWorkRequest.Builder(
            workerClass = workerClass,
            repeatInterval = schedulable.repeatInterval
        )
            .addTag(schedulable.tag)
            .setInitialDelay(offset)
            .setBackoffCriteria(
                backoffPolicy = RemoteConstants.BACKOFF_CRITERIA,
                duration = Duration.ofMillis(RemoteConstants.BACKOFF_INITIAL_DELAY)
            )

        constraints?.let {
            requestBuilder.setConstraints(it)
        }

        workManager.enqueueUniquePeriodicWork(
            schedulable.tag,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            requestBuilder.build()
        )

        Log.d(
            logTag,
            "The request shall be triggered in ${offset.formatHoursMinutes()} for first time"
        )
    }

    private fun cancelRequest(tag: String) {
        workManager.cancelUniqueWork(tag)
        Log.d(logTag, "The request with tag $tag has been cancelled")
    }

    /**
     * Schedules an one time request
     * @param workerClass Class associated with the worker to request
     * @param oneTimeWorker Info to schedule the one time request
     * @param constraints Optional constraints that must be fulfilled to execute the request
     */
    private fun oneTimeRequest(
        workerClass: Class<out ListenableWorker>,
        oneTimeWorker: OneTimeWorker,
        constraints: Constraints? = null,
    ) {
        Log.d(logTag, "Setting one time request with tag: ${oneTimeWorker.tag}")

        // Build request. If we have received constants, set them

        val requestBuilder = OneTimeWorkRequest.Builder(workerClass)

        constraints?.let {
            requestBuilder.setConstraints(it)
        }

        workManager.enqueueUniqueWork(
            oneTimeWorker.tag,
            ExistingWorkPolicy.KEEP,
            requestBuilder.build()
        )

        Log.d(logTag, "The request shall be triggered")
    }

    override fun scheduleDailyMorningNotificationWorker() {
        with(DailyMorningNotificationWorker)
        {
            scheduleRequest(
                workerClass = DailyMorningNotificationWorker::class.java,
                schedulable = this
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
                schedulable = this
            )
        }
    }

    override fun cancelDailyNightNotificationWorker() {
        cancelRequest(DailyNightNotificationWorker.tag)
    }

    override fun scheduleOneOffNotificationWorker() {
        with(OneOffNotificationWorker)
        {
            scheduleRequest(
                workerClass = OneOffNotificationWorker::class.java,
                schedulable = this
            )
        }
    }

    override fun cancelOneOffNotificationWorker() {
        cancelRequest(OneOffNotificationWorker.tag)
    }

    override fun scheduleUploadWorker() {
        with(UploadWorker)
        {
            scheduleRequest(
                workerClass = UploadWorker::class.java,
                schedulable = this,
            )
        }
    }


    override fun cancelUploadWorker() = cancelRequest(UploadWorker.tag)

    /**
     * Schedule [UploadPhoneDataWorker]
     */
    override fun scheduleUploadPhoneDataWorker() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        with(UploadPhoneDataWorker)
        {
            scheduleRequest(
                workerClass = UploadPhoneDataWorker::class.java,
                schedulable = this,
                constraints = constraints
            )
        }
    }

    /**
     * Cancel [UploadPhoneDataWorker]
     */
    override fun cancelUploadPhoneDataWorker() {
        cancelRequest(UploadPhoneDataWorker.tag)
    }

    /**
     * Schedule [UploadTrafficDataWorker]
     */
    override fun scheduleUploadTrafficDataWorker() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        with(UploadTrafficDataWorker)
        {
            scheduleRequest(
                workerClass = UploadTrafficDataWorker::class.java,
                schedulable = this,
                constraints = constraints
            )
        }
    }

    /**
     * Cancel [UploadTrafficDataWorker]
     */
    override fun cancelUploadTrafficDataWorker() {
        cancelRequest(UploadTrafficDataWorker.tag)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun scheduleUploadUsageInfoWorker() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        with(UploadUsageInfoWorker)
        {
            oneTimeRequest(
                workerClass = UploadUsageInfoWorker::class.java,
                oneTimeWorker = this,
                constraints = constraints
            )
        }
    }

    override fun cancelUploadUsageInfoWorker() {
        cancelRequest(UploadUsageInfoWorker.tag)
    }

    override fun queryWorkerStatus(): LiveData<List<WorkInfo>> {
        return workManager.getWorkInfosLiveData(
            WorkQuery.fromUniqueWorkNames(
                DailyMorningNotificationWorker.tag,
                DailyNightNotificationWorker.tag,
                OneOffNotificationWorker.tag,
                UploadWorker.tag,
                UploadPhoneDataWorker.tag,
                UploadTrafficDataWorker.tag,
                UploadUsageInfoWorker.tag
            )
        )
    }
}