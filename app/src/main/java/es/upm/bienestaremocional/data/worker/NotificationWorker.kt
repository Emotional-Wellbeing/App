package es.upm.bienestaremocional.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRoundReducedRepository
import es.upm.bienestaremocional.ui.notification.Notification
import javax.inject.Named

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Named("logTag") private val logTag: String,
    private val notification: Notification,
    private val questionnaireRoundReducedRepository: QuestionnaireRoundReducedRepository,
): CoroutineWorker(appContext, workerParams)
{
    override suspend fun doWork(): Result
    {
        Log.d(logTag,"Executing Notification Worker")

        val questionnaireRoundReduced = questionnaireRoundReducedRepository.insert()
        notification.showQuestionnaireNotification(questionnaireRoundReduced)

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}
