package es.upm.bienestaremocional.app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun android12OrAbove() : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

/**
 * Shows details of a given throwable in the snackbar
 */
fun showExceptionSnackbar(
    scope: CoroutineScope,
    snackbarHostState : SnackbarHostState,
    throwable: Throwable?
) {
    scope.launch {
        snackbarHostState.showSnackbar(
            message = throwable?.localizedMessage ?: "Unknown exception",
            duration = SnackbarDuration.Short
        )
    }
}

fun restartApp(activity: Activity)
{
    activity.finish()
    activity.startActivity(activity.intent)
    activity.overridePendingTransition(0,0)
}

/**
 * Open activity from other app
 */
fun openForeignActivity(context: Context, action: String) = context.startActivity(Intent(action))

fun openSettingsNotifications(context: Context)
{
    val intent = Intent().apply {
        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    }
    context.startActivity(intent)
}

fun openSettingsExactNotifications(context: Context)
{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
    {
        val intent = Intent().apply {
            action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }
        context.startActivity(intent)
    }
}