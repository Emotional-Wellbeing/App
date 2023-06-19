package es.upm.bienestaremocional.domain.processing

import android.util.Range
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

fun ZonedDateTime.toEpochMilliSecond(): Long = toEpochSecond() * 1000

fun milliSecondToZonedDateTime(timestamp: Long): ZonedDateTime =
    ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())

fun secondToZonedDateTime(timestamp: Long): ZonedDateTime =
    ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault())

fun ZonedDateTime.toStartOfTheDay(): ZonedDateTime = this.truncatedTo(ChronoUnit.DAYS)
fun ZonedDateTime.toEndOfTheDay(): ZonedDateTime = this.with(LocalTime.MAX)

fun ZonedDateTime.toStartOfTheWeek(): ZonedDateTime =
    this.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        .toStartOfTheDay()

fun ZonedDateTime.toStartOfTheMonth(): ZonedDateTime =
    this.with(TemporalAdjusters.firstDayOfMonth())
        .toStartOfTheDay()

/**
 * Extract the day of the [timestamp] and return it in [ZonedDateTime] format
 * @param timestamp to truncate in milliseconds
 * @return [ZonedDateTime] of these day at 00:00:00
 */
fun timestampToStartOfTheDay(timestamp: Long): ZonedDateTime {
    // Timestamp cannot be negative
    require(timestamp > 0) { "Timestamp cannot be negative" }

    //Truncate to Day
    return milliSecondToZonedDateTime(timestamp).toStartOfTheDay()
}

/**
 * Extract the week of the [timestamp] and return it in [ZonedDateTime] format
 * @param timestamp to truncate in milliseconds
 * @return [ZonedDateTime] of these start of the week (monday) at 00:00:00
 */
fun timestampToStartOfTheWeek(timestamp: Long): ZonedDateTime {
    // Timestamp cannot be negative
    require(timestamp > 0) { "Timestamp cannot be negative" }

    return milliSecondToZonedDateTime(timestamp).toStartOfTheWeek()
}

/**
 * Extract the month of the [timestamp] and return it in [ZonedDateTime] format
 * @param timestamp to truncate in milliseconds
 * @return [ZonedDateTime] of the first day of the month at 00:00:00
 */
fun timestampToStartOfTheMonth(timestamp: Long): ZonedDateTime {
    // Timestamp cannot be negative
    require(timestamp > 0) { "Timestamp cannot be negative" }

    return milliSecondToZonedDateTime(timestamp).toStartOfTheMonth()
}

/**
 * Obtain the range of the last seven days
 * For example, if today is 9th of May, this fun return the millisecond of 2th May at 00:00:00:000
 * and 8th May at 23:59:59:999
 */
fun getLastSevenDays(): Range<ZonedDateTime> {
    val now = ZonedDateTime.now()
    val start = now.minusDays(7).toStartOfTheDay()
    val end = now.minusDays(1).toEndOfTheDay()
    return Range(start, end)
}


/**
 * Obtain the range of the last seven days in milliseconds
 * For example, if today is 9th of May, this fun return the millisecond of 2th May at 00:00:00:000
 * and 8th May at 23:59:59:999
 */
fun getLastSevenDaysMillisecondTimestamps(): Pair<Long, Long> {
    val lastSevenDays = getLastSevenDays()
    return Pair(lastSevenDays.lower.toEpochMilliSecond(), lastSevenDays.upper.toEpochMilliSecond())
}

/**
 * Obtain the range of current week in milliseconds
 * For example, if today is Tuesday 9th of May, this fun return the millisecond of Monday 8th May
 * at 00:00:00:000 and Sunday 14th May at 23:59:59:999
 */
fun getCurrentWeek(): Range<ZonedDateTime> {
    val now = ZonedDateTime.now()

    // Go to Monday (or stay if current day is Monday) and get the start of the day
    val start = now
        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        .toStartOfTheDay()

    // Go to Sunday (or stay if current day is Sunday), and get the end of the day
    val end = now
        .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        .toEndOfTheDay()

    return Range(start, end)
}


/**
 * Obtain the range of current week in milliseconds
 * For example, if today is Tuesday 9th of May, this fun return the millisecond of Monday 8th May
 * at 00:00:00:000 and Sunday 14th May at 23:59:59:999
 */
fun getCurrentWeekMillisecondTimestamps(): Pair<Long, Long> {
    val currentWeek = getCurrentWeek()
    return Pair(currentWeek.lower.toEpochMilliSecond(), currentWeek.upper.toEpochMilliSecond())
}

/**
 * Obtain the range of the last seven days in milliseconds
 * For example, if today is 8th of May, this fun return the millisecond of 7th May at 00:00:00:000
 * and 7th May at 23:59:59:999
 */
fun getStartAndEndOfYesterday(): Range<ZonedDateTime> {
    val yesterday = ZonedDateTime
        .now()
        .minusDays(1)
    return Range(yesterday.toStartOfTheDay(), yesterday.toEndOfTheDay())
}

/**
 * Obtain the range of the last seven days in milliseconds
 * For example, if today is 8th of May, this fun return the millisecond of 7th May at 00:00:00:000
 * and 7th May at 23:59:59:999
 */
fun getStartAndEndOfYesterdayMillisecondTimestamps(): Pair<Long, Long> {
    val yesterday = getStartAndEndOfYesterday()
    return Pair(yesterday.lower.toEpochMilliSecond(), yesterday.upper.toEpochMilliSecond())
}

/**
 * Convert the range in [ZonedDateTime] to [Pair] containing the same information but in
 * millisecond timestamp format
 */
fun Range<ZonedDateTime>.toPairMillisecondTimestamps(): Pair<Long, Long> =
    Pair(lower.toEpochMilliSecond(), upper.toEpochMilliSecond())

fun Range<ZonedDateTime>.lowerStartDayUpperEndDay(): Range<ZonedDateTime> =
    Range(lower.toStartOfTheDay(), upper.toEndOfTheDay())