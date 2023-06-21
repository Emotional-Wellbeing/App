package es.upm.bienestaremocional.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

// The minimum android level that can use Health Connect
const val MIN_SUPPORTED_SDK = Build.VERSION_CODES.O_MR1

fun linspace(start: Long, stop: Long, num: Int) =
    (start..stop step (stop - start) / (num - 1)).toList()

fun android12OrAbove(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

/**
 * Shows details of a given throwable in the snackbar
 */
suspend fun showExceptionSnackbar(
    snackbarHostState: SnackbarHostState,
    throwable: Throwable?
) {
    snackbarHostState.showSnackbar(
        message = throwable?.localizedMessage ?: "Unknown exception",
        duration = SnackbarDuration.Short
    )
}

/**
 * Extracted from https://stackoverflow.com/a/74696154
 */
fun Context.getActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")

}

fun restartApp(activity: Activity) {
    activity.finish()
    activity.startActivity(activity.intent)
    activity.overridePendingTransition(0, 0)
}

/**
 * Open activity from other app
 */
fun openForeignActivity(context: Context, action: String) = context.startActivity(Intent(action))

fun openDial(context: Context, phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
    context.startActivity(intent)
}