package es.upm.bienestaremocional.data.worker

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
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
import es.upm.bienestaremocional.data.trafficstats.Traffic
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepository
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Named

@HiltWorker
class UploadTrafficDataWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Named("logTag") private val logTag: String,
    private val appInfo: AppInfo,
    private val remoteRepository: RemoteRepository
) : CoroutineWorker(appContext, workerParams) {
    companion object : Schedulable {
        //No offset
        override val initialTime: LocalDateTime? = null

        override val tag: String = "upload_traffic_data"

        override val repeatInterval: Duration = Duration.ofSeconds(3600)
    }

    override suspend fun doWork(): Result {
        Log.d(logTag, "Executing Upload Traffic Data Worker")

        val permissionCoarse = ContextCompat.checkSelfPermission(
            appContext,
            ACCESS_COARSE_LOCATION
        )
        val permissionFine = ContextCompat.checkSelfPermission(
            appContext,
            ACCESS_FINE_LOCATION
        )

        lateinit var result: Result

        // Indicate whether the work finished successfully with the Result
        if (permissionCoarse == PackageManager.PERMISSION_GRANTED && permissionFine == PackageManager.PERMISSION_GRANTED) {
            val traffic = Traffic()
            val trafficMessage = traffic.init()
            val userId = appInfo.getUserID()
            val currentTime = System.currentTimeMillis()/1000
            val message =
                "{ \"userId\": \"$userId\", \"timestamp\": \"$currentTime\", \"databg\": { \"InternetInfo\": $trafficMessage}}"
            val success = remoteRepository.postBackgroundData(message)

            result = if (success) {
                Log.d(logTag, "Inserted internet info")
                Result.success()
            }
            else {
                Log.d(logTag, "Not inserted internet info")
                Result.retry()
            }
        }
        else {
            Log.d(logTag, "Not enough permissions to post traffic data")
            result = Result.failure()
        }

        return result
    }
}
