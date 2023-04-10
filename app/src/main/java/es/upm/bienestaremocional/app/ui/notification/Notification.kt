package es.upm.bienestaremocional.app.ui.notification

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundReduced

interface Notification
{
    /**
     * Check if notification permission has been granted
     */
    fun hasNotificationPermission(): Boolean

    /**
     * Request notification permission
     * @param onResult : callback to execute when the request ends
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun RequestNotificationPermission(onResult : (Boolean) -> Unit)

    /**
     * Display questionnaire notification
     * @param questionnaireRoundReduced: questionnaire round to display when the user press
     * the notification
     */
    fun showQuestionnaireNotification(questionnaireRoundReduced: QuestionnaireRoundReduced)

    /**
     * Display uploading data notification
     */
    fun showUploadNotification() : android.app.Notification
}