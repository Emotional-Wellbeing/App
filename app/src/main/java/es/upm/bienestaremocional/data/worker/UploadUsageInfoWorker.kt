package es.upm.bienestaremocional.data.worker

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.usage.Usage
import es.upm.bienestaremocional.di.AppModule
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepository
import javax.inject.Named

@HiltWorker
class UploadUsageInfoWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Named("logTag") private val logTag: String,
    private val remoteRepository: RemoteRepository
) : CoroutineWorker(appContext, workerParams) {
    companion object : OneTimeWorker {
        override val tag: String = "upload_usage_info_data"
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override suspend fun doWork(): Result {
        Log.d(logTag, "Executing Upload Usage Info Worker")

        lateinit var result: Result

        //Execute app usage
        val usage = Usage(logTag = logTag)
        val listApps = usage.getAppUsage(appContext)

        val appInfo: AppInfo = AppModule.provideAppInfo(appContext)

        val userId = appInfo.getUserID()
        val message = "{ \"userId\": \"$userId\", \"databg\": {\"UsageInfo\": {$listApps}}}"
        val success = remoteRepository.postBackgroundData(message)

        result = if (success) {
            Log.d(logTag, "Inserted usage info")
            Result.success()
        }
        else {
            Log.d(logTag, "Not inserted usage info")
            Result.failure()
        }
        return result
    }
}
