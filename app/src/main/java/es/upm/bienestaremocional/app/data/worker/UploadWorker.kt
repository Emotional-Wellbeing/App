package es.upm.bienestaremocional.app.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import es.upm.bienestaremocional.app.domain.repository.remote.RemoteOperationResult
import es.upm.bienestaremocional.app.domain.usecases.PostUserDataUseCase
import es.upm.bienestaremocional.app.ui.notification.Notification
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import javax.inject.Named
import kotlin.random.Random

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Named("logTag") private val logTag: String,
    private val notification: Notification,
    private val postUserDataUseCase: PostUserDataUseCase
): CoroutineWorker(appContext, workerParams)
{
    override suspend fun doWork(): Result
    {
        Log.d(logTag,"Executing Upload Worker")

        var result : Result
        try
        {
            setForeground(getForegroundInfo())

            if (postUserDataUseCase.shouldExecute())
            {
                Log.d(logTag,"We can read data so upload it")

                //TODO remove this in production
                delay(5000L)

                val response = postUserDataUseCase.execute()

                // Indicate whether the work finished successfully with the Result
                result = when(response)
                {
                    RemoteOperationResult.Success -> Result.success()
                    RemoteOperationResult.ServerFailure -> Result.retry()
                    else -> Result.failure()
                }
            }
            else
            {
                Log.d(logTag,"We cannot read any data so don't upload")
                result = Result.success()
            }
        } catch (e: IllegalStateException)
        {
            Log.d(logTag, "IllegalStateException at calling setForeground")
            result = Result.failure()
        }

        return result

    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            Random.nextInt(),
            notification.showUploadNotification()
        )
    }

    companion object
    {
        val time: LocalDateTime = LocalDateTime.now()
            .withHour(3)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)
        const val tag = "upload"
    }
}
