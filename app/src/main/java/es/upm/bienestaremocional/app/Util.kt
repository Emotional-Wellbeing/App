
package es.upm.bienestaremocional.app

import android.app.Activity
import android.os.Build
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun dynamicColorsSupported() : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

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