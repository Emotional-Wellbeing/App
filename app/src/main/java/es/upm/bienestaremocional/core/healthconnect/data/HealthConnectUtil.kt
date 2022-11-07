package es.upm.bienestaremocional.core.healthconnect.data

import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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

fun Duration.formatTime() =
    String.format("%02d:%02d:%02d", this.toHours() % 24, this.toMinutes() % 60, this.seconds % 60)

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
