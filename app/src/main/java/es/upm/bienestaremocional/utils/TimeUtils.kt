package es.upm.bienestaremocional.utils

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.random.Random

/**
 * Creates a [ZonedDateTime] either using the offset stored in Health Connect, or falling back on
 * the zone offset for the device, where Health Connect contains no zone offset data. This fallback
 * may be correct in a number of circumstances, but may also not apply in others, so is used here
 * just as an example.
 */
fun dateTimeWithOffsetOrDefault(time: Instant, offset: ZoneOffset? = null): ZonedDateTime =
    offset?.let { ZonedDateTime.ofInstant(time, it) }
        ?: run { ZonedDateTime.ofInstant(time, ZoneId.systemDefault()) }

fun Duration.formatHoursMinutes() =
    String.format("%01dh%02dm", this.toHours() % 24, this.toMinutes() % 60)

fun formatDisplayTimeStartEnd(
    startTime: Instant,
    startZoneOffset: ZoneOffset?,
    endTime: Instant,
    endZoneOffset: ZoneOffset?
): String {
    val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    val start = timeFormatter.format(dateTimeWithOffsetOrDefault(startTime, startZoneOffset))
    val end = timeFormatter.format(dateTimeWithOffsetOrDefault(endTime, endZoneOffset))
    return "$start - $end"
}

fun formatDateTime(
    start: Instant,
    startZoneOffset: ZoneOffset?,
    end: Instant,
    endZoneOffset: ZoneOffset?
): String {
    val startTime = dateTimeWithOffsetOrDefault(start, startZoneOffset)
    val endTime = dateTimeWithOffsetOrDefault(end, endZoneOffset)
    val dateLabel = formatDate(startTime)
    val startLabel = formatTime(startTime)
    val endLabel = formatTime(endTime)
    return "$dateLabel: $startLabel - $endLabel"
}

fun formatDateTime(
    time: Instant,
    zoneOffset: ZoneOffset? = null
): String {
    val startTime = dateTimeWithOffsetOrDefault(time, zoneOffset)
    val dateLabel = formatDate(startTime)
    val timeLabel = formatTime(startTime)
    return "$dateLabel: $timeLabel"
}

fun formatDate(date: LocalDate): String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date)

fun formatDate(date: ZonedDateTime): String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date)

fun formatTime(time: ZonedDateTime): String =
    DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(time)

fun formatUnixTimeStamp(time: Long): String =
    formatDateTime(Instant.ofEpochMilli(time))

fun generateTime(origin: ZonedDateTime = ZonedDateTime.now(), offsetDays: Long = 0): ZonedDateTime =
    origin.minusDays(offsetDays)
        .withHour(Random.nextInt(0, 12))
        .withMinute(Random.nextInt(0, 60))
        .withSecond(Random.nextInt(0, 60))

fun generateInterval(
    origin: ZonedDateTime = ZonedDateTime.now(),
    offsetDays: Long = 0,
    lowerBound: Int = 0,
    upperBound: Int = 23
):
        Pair<ZonedDateTime, ZonedDateTime> {
    val init = origin.minusDays(offsetDays)
        .withHour(Random.nextInt(lowerBound, upperBound))
        .withMinute(Random.nextInt(0, 60))
        .withSecond(Random.nextInt(0, 60))
    val end = origin.minusDays(offsetDays)
        .withHour(Random.nextInt(init.hour + 1, upperBound + 1))
        .withMinute(Random.nextInt(0, 60))
        .withSecond(Random.nextInt(0, 60))
    return Pair(init, end)
}

fun obtainTimestamp(instant: Instant, zoneOffset: ZoneOffset?): Long =
    ZonedDateTime.ofInstant(instant, zoneOffset ?: ZoneId.systemDefault()).toEpochSecond()
