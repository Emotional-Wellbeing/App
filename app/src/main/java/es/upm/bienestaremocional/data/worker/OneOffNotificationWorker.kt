package es.upm.bienestaremocional.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import es.upm.bienestaremocional.domain.usecases.InsertOneOffRoundUseCase
import es.upm.bienestaremocional.ui.notification.Notification
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Named

@HiltWorker
class OneOffNotificationWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Named("logTag") private val logTag: String,
    private val notification: Notification,
    private val insertOneOffRoundUseCase: InsertOneOffRoundUseCase
) : CoroutineWorker(appContext, workerParams) {
    companion object : Schedulable {
        override val initialTime: LocalDateTime = LocalDateTime.now()
            .withHour(15)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)

        override val tag: String = "one_off_notification"

        override val repeatInterval: Duration = Duration.ofDays(14)
    }

    override suspend fun doWork(): Result {
        Log.d(logTag, "Executing One Off Notification Worker")

        val oneOffRound = insertOneOffRoundUseCase.insertOneOffRound()

        notification.showOneOffRoundNotification(oneOffRound)

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}
