
package es.upm.bienestaremocional.app

import android.app.Activity
import android.os.Build
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import es.upm.bienestaremocional.core.extraction.healthconnect.data.dateTimeWithOffsetOrDefault
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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


fun formatDateTime(start: Instant,
                   startZoneOffset: ZoneOffset?,
                   end: Instant,
                   endZoneOffset: ZoneOffset?): String
{
    val startTime = dateTimeWithOffsetOrDefault(start, startZoneOffset)
    val endTime = dateTimeWithOffsetOrDefault(end, endZoneOffset)
    val dateLabel = formatDate(startTime)
    val startLabel = formatTime(startTime)
    val endLabel = formatTime(endTime)
    return "$dateLabel: $startLabel - $endLabel"
}

fun formatDateTime(time: Instant,
                   zoneOffset: ZoneOffset?): String
{
    val startTime = dateTimeWithOffsetOrDefault(time, zoneOffset)
    val dateLabel = formatDate(startTime)
    val timeLabel = formatTime(startTime)
    return "$dateLabel: $timeLabel"
}

fun formatDate(date: ZonedDateTime): String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date)

fun formatTime(time: ZonedDateTime): String =
    DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(time)