package es.upm.bienestaremocional.core.extraction.healthconnect.data

import android.os.Build
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

// The minimum android level that can use Health Connect
const val MIN_SUPPORTED_SDK = Build.VERSION_CODES.O_MR1

/**
 * Creates a [ZonedDateTime] either using the offset stored in Health Connect, or falling back on
 * the zone offset for the device, where Health Connect contains no zone offset data. This fallback
 * may be correct in a number of circumstances, but may also not apply in others, so is used here
 * just as an example.
 */
fun dateTimeWithOffsetOrDefault(time: Instant, offset: ZoneOffset?): ZonedDateTime =
    if (offset != null)
        ZonedDateTime.ofInstant(time, offset)
    else
        ZonedDateTime.ofInstant(time, ZoneId.systemDefault())

fun Duration.formatHoursMinutes() =
    String.format("%01dh%02dm", this.toHours() % 24, this.toMinutes() % 60)

fun formatDisplayTimeStartEnd(
    startTime: Instant,
    startZoneOffset: ZoneOffset?,
    endTime: Instant,
    endZoneOffset: ZoneOffset?
): String
{
    val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    val start = timeFormatter.format(dateTimeWithOffsetOrDefault(startTime, startZoneOffset))
    val end = timeFormatter.format(dateTimeWithOffsetOrDefault(endTime, endZoneOffset))
    return "$start - $end"
}

fun linspace(start: Long, stop: Long, num: Int) =
    (start..stop step (stop - start) / (num - 1)).toList()