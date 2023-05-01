package es.upm.bienestaremocional.app.utils

import es.upm.bienestaremocional.core.extraction.healthconnect.data.dateTimeWithOffsetOrDefault
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import kotlin.random.Random

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
                   zoneOffset: ZoneOffset? = null): String
{
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

fun formatUnixTimeStamp(time: Long) : String  =
    formatDateTime(Instant.ofEpochMilli(time))

fun generateTime(origin: ZonedDateTime = ZonedDateTime.now(), offsetDays: Long = 0): ZonedDateTime =
    origin.minusDays(offsetDays)
        .withHour(Random.nextInt(0, 12))
        .withMinute(Random.nextInt(0, 60))
        .withSecond(Random.nextInt(0, 60))

fun generateInterval(origin: ZonedDateTime = ZonedDateTime.now(),
                     offsetDays: Long = 0,
                     lowerBound : Int = 0,
                     upperBound : Int = 23
):
        Pair<ZonedDateTime,ZonedDateTime>
{
    val init = origin.minusDays(offsetDays)
        .withHour(Random.nextInt(lowerBound, upperBound))
        .withMinute(Random.nextInt(0, 60))
        .withSecond(Random.nextInt(0, 60))
    val end = origin.minusDays(offsetDays)
        .withHour(Random.nextInt(init.hour + 1, upperBound + 1))
        .withMinute(Random.nextInt(0, 60))
        .withSecond(Random.nextInt(0, 60))
    return Pair(init,end)
}

fun obtainTimestamp(instant: Instant, zoneOffset: ZoneOffset?) : Long =
    ZonedDateTime.ofInstant(instant, zoneOffset ?: ZoneId.systemDefault()).toEpochSecond()

/**
 * Obtain the range of the last seven days in milliseconds
 * For example, if today is 8th of May, this fun return the millisecond of 1th May at 00:00:00:000
 * and 7th May at 23:59:59:999
 */
fun getLastSevenDays(): Pair<Long,Long>
{
    val now = ZonedDateTime.now()
    val start = now.minusDays(7).truncatedTo(ChronoUnit.DAYS).toEpochSecond() * 1000
    val end = (now.truncatedTo(ChronoUnit.DAYS).toEpochSecond() * 1000 ) - 1
    return Pair(start,end)
}