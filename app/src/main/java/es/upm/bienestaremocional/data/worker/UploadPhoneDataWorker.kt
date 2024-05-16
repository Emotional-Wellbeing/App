package es.upm.bienestaremocional.data.worker

import android.Manifest.permission.READ_CALL_LOG
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.phonecalls.PhoneInfo
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepository
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Named

@HiltWorker
class UploadPhoneDataWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Named("logTag") private val logTag: String,
    private val appInfo: AppInfo,
    private val remoteRepository: RemoteRepository
) : CoroutineWorker(appContext, workerParams) {
    companion object : Schedulable {
        //No offset
        override val initialTime: LocalDateTime? = null

        override val tag: String = "upload_phone_data"

        override val repeatInterval: Duration = Duration.ofSeconds(7200)
    }

    override suspend fun doWork(): Result {
        Log.d(logTag, "Executing Upload Phone Data Worker")

        val permissionCall = ContextCompat.checkSelfPermission(
            appContext,
            READ_CALL_LOG
        )

        lateinit var result: Result

        // Indicate whether the work finished successfully with the Result
        if (permissionCall == PackageManager.PERMISSION_GRANTED) {
            val phone = PhoneInfo()
            val listCalls = phone.getCallLogs(appContext)
            val userId = appInfo.getUserID()
            val currentTime = System.currentTimeMillis()/1000

            val message = "{ \"userId\": \"$userId\", \"timestamp\": \"$currentTime\", \"databg\": { \"PhoneInfo\":$listCalls}}"
            val success = remoteRepository.postBackgroundData(message)

            result = if (success) {
                Log.d(logTag, "Inserted phone info")
                Result.success()
            }
            else {
                Log.d(logTag, "Not inserted phone info")
                Result.retry()
            }
        }
        else {
            Log.d("hola", "permissionCall $permissionCall")
            Log.d(logTag, "Not enough permissions to post phone data")
            result = Result.failure()
        }

        return result
    }
}
