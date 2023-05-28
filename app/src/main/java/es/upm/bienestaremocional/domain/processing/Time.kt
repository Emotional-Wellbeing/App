package es.upm.bienestaremocional.domain.processing

import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

fun ZonedDateTime.toEpochMilliSecond() : Long = toEpochSecond() * 1000

fun milliSecondToZonedDateTime(timestamp: Long) : ZonedDateTime =
    ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())


/**
 * Extract the day of the [timestamp] and return it in [ZonedDateTime] format
 * @param timestamp to truncate in milliseconds
 * @return [ZonedDateTime] of these day at 00:00:00
 */
fun timestampToStartOfTheDay(timestamp: Long) : ZonedDateTime
{
    // Timestamp cannot be negative
    require(timestamp > 0) {"Timestamp cannot be negative"}

    val zonedDateTime = milliSecondToZonedDateTime(timestamp)
    //Truncate to Day
    return zonedDateTime.truncatedTo(ChronoUnit.DAYS)
}

/**
 * Extract the week of the [timestamp] and return it in [ZonedDateTime] format
 * @param timestamp to truncate in milliseconds
 * @return [ZonedDateTime] of these start of the week (monday) at 00:00:00
 */
fun timestampToStartOfTheWeek(timestamp: Long) : ZonedDateTime
{
    // Timestamp cannot be negative
    require(timestamp > 0) {"Timestamp cannot be negative"}

    val zonedDateTime = milliSecondToZonedDateTime(timestamp)
    //Truncate to the start of the week
    return zonedDateTime
        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        .truncatedTo(ChronoUnit.DAYS)
}

/**
 * Extract the month of the [timestamp] and return it in [ZonedDateTime] format
 * @param timestamp to truncate in milliseconds
 * @return [ZonedDateTime] of the first day of the month at 00:00:00
 */
fun timestampToStartOfTheMonth(timestamp: Long) : ZonedDateTime
{
    // Timestamp cannot be negative
    require(timestamp > 0) {"Timestamp cannot be negative"}

    val zonedDateTime = milliSecondToZonedDateTime(timestamp)
    //Truncate to the start of the month
    return zonedDateTime
        .with(TemporalAdjusters.firstDayOfMonth())
        .truncatedTo(ChronoUnit.DAYS)
}

/**
 * Obtain the range of the last seven days in milliseconds
 * For example, if today is 9th of May, this fun return the millisecond of 2th May at 00:00:00:000
 * and 8th May at 23:59:59:999
 */
fun getLastSevenDays(): Pair<Long,Long>
{
    val now = ZonedDateTime.now()
    val start = now.minusDays(7).truncatedTo(ChronoUnit.DAYS).toEpochMilliSecond()
    val end = (now.truncatedTo(ChronoUnit.DAYS).toEpochSecond() * 1000 ) - 1
    return Pair(start,end)
}

/**
 * Obtain the range of current week in milliseconds
 * For example, if today is Tuesday 9th of May, this fun return the millisecond of Monday 8th May
 * at 00:00:00:000 and Sunday 14th May at 23:59:59:999
 */
fun getCurrentWeek(): Pair<Long,Long>
{
    val now = ZonedDateTime.now()

    // Go to Monday (or stay if current day is Monday), truncate it and pass to milli
    val start = now
        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        .truncatedTo(ChronoUnit.DAYS)
        .toEpochMilliSecond()

    // Go to next Monday (if current day is Monday go to next Monday anyway), truncate it,
    // pass to milli and reduces 1 milli to go to previous Sunday at 23:59:59:999
    val end = (now
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        .truncatedTo(ChronoUnit.DAYS)
        .toEpochMilliSecond()) - 1

    return Pair(start,end)
}

/**
 * Obtain the range of the last seven days in milliseconds
 * For example, if today is 8th of May, this fun return the millisecond of 7th May at 00:00:00:000
 * and 7th May at 23:59:59:999
 */
fun getStartAndEndOfYesterday(): Pair<Long,Long>
{
    val now = ZonedDateTime.now()

    val start = now
        .minusDays(1)
        .truncatedTo(ChronoUnit.DAYS)
        .toEpochMilliSecond()

    val end = (now
        .truncatedTo(ChronoUnit.DAYS)
        .toEpochMilliSecond()) - 1

    return Pair(start,end)
}