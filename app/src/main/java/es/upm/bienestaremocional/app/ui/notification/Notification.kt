package es.upm.bienestaremocional.app.ui.notification

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundReduced

interface Notification
{
    fun hasNotificationPermission(): Boolean

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun RequestNotificationPermission(onResult : (Boolean) -> Unit)

    fun showQuestionnaireNotification(questionnaireRoundReduced: QuestionnaireRoundReduced)
}