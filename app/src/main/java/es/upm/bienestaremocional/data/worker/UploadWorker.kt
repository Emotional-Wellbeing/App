package es.upm.bienestaremocional.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import es.upm.bienestaremocional.domain.repository.remote.RemoteOperationResult
import es.upm.bienestaremocional.domain.usecases.PostDailyQuestionnairesUseCase
import es.upm.bienestaremocional.domain.usecases.PostOneOffQuestionnairesUseCase
import es.upm.bienestaremocional.domain.usecases.PostUserDataUseCase
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Named

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Named("logTag") private val logTag: String,
    private val postUserDataUseCase: PostUserDataUseCase,
    private val postDailyQuestionnairesUseCase: PostDailyQuestionnairesUseCase,
    private val postOneOffQuestionnairesUseCase: PostOneOffQuestionnairesUseCase,
) : CoroutineWorker(appContext, workerParams) {
    companion object : Schedulable {
        override val initialTime: LocalDateTime? = null
        override val tag = "upload"
        override val repeatInterval: Duration = Duration.ofHours(8)
    }

    override suspend fun doWork(): Result {
        Log.d(logTag, "Executing Upload Worker")

        lateinit var result: Result
        lateinit var response: RemoteOperationResult
        try {
            if (postUserDataUseCase.shouldExecute()) {
                Log.d(logTag, "We can read user data so upload it")

                response = postUserDataUseCase.execute()

                // Indicate whether the work finished successfully with the Result
                result = when (response) {
                    RemoteOperationResult.Success -> Result.success()
                    RemoteOperationResult.ServerFailure -> Result.retry()
                    else -> Result.failure()
                }
            }
            else {
                Log.d(logTag, "We cannot read any data so don't upload")
                result = Result.success()
            }

            if (result == Result.success()) {
                response = postDailyQuestionnairesUseCase.execute()

                // Indicate whether the work finished successfully with the Result
                result = when (response) {
                    RemoteOperationResult.Success -> Result.success()
                    RemoteOperationResult.ServerFailure -> Result.retry()
                    else -> Result.failure()
                }
            }

            if (result == Result.success()) {
                response = postOneOffQuestionnairesUseCase.execute()

                // Indicate whether the work finished successfully with the Result
                result = when (response) {
                    RemoteOperationResult.Success -> Result.success()
                    RemoteOperationResult.ServerFailure -> Result.retry()
                    else -> Result.failure()
                }
            }


        }
        catch (e: IllegalStateException) {
            Log.d(logTag, "IllegalStateException at calling setForeground")
            result = Result.failure()
        }

        return result

    }
}
