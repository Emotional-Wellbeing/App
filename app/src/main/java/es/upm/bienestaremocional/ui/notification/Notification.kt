package es.upm.bienestaremocional.ui.notification

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import es.upm.bienestaremocional.data.database.entity.round.OneOffRound

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
     * Display one off round notification
     * @param oneOffRound: one off round to display when the user press the notification
     */
    fun showOneOffRoundNotification(oneOffRound: OneOffRound)

    /**
     * Display daily morning round notification
     * @param dailyRound: daily round to display when the user press the notification
     */
    fun showDailyMorningRoundNotification(dailyRound: DailyRound)

    /**
     * Display daily night round notification
     * @param dailyRound: daily round to display when the user press the notification
     */
    fun showDailyNightRoundNotification(dailyRound: DailyRound)

    /**
     * Display uploading data notification
     */
    fun showUploadNotification() : android.app.Notification
}