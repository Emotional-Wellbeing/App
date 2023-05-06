package es.upm.bienestaremocional.app.domain.processing

import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

/**
 * truncateTimestampToWeek and truncateTimestampToMonth are different from truncateTimestampToDay
 * because we cannot use Instant.truncatedTo(ChronoUnit) with ChronoUnit.WEEKS and ChronoUnit.MONTHS
 * (java.time.temporal.UnsupportedTemporalTypeException: Unit is too large to be used for truncation)
 * https://stackoverflow.com/questions/30775521/is-it-possible-to-truncate-date-to-month-with-java-8
 */

/**
 * Extract the day of the [timestamp] and return it.
 * @param timestamp to truncate  in milliseconds
 * @return Epoch second of the timestamp truncated
 */
fun truncateTimestampToDay(timestamp: Long) : Long =
    Instant.ofEpochMilli(timestamp).truncatedTo(ChronoUnit.DAYS).epochSecond
/**
 * Extract the week of the [timestamp] and return it.
 * @param timestamp to truncate in milliseconds
 * @return Epoch second of the timestamp truncated
 */
fun truncateTimestampToWeek(timestamp: Long) : Long
{
    //instant from timestamp
    val instant = Instant.ofEpochMilli(timestamp)
    //since we cannot truncate with instant, we do a workaround using ZonedDateTime
    val zonedDateTime : ZonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    // go to first day of the week and truncate it hour offset
    val zonedDateTimeTruncated = zonedDateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).truncatedTo(
        ChronoUnit.DAYS)
    //return zonedDateTime epoch second
    return zonedDateTimeTruncated.toEpochSecond()
}

/**
 * Extract the month of the [timestamp] and return it.
 * @param timestamp to truncate  in milliseconds
 * @return Epoch second of the timestamp truncated
 */
fun truncateTimestampToMonth(timestamp: Long) : Long
{
    //instant from timestamp
    val instant = Instant.ofEpochMilli(timestamp)
    //since we cannot truncate with instant, we do a workaround using ZonedDateTime
    val zonedDateTime : ZonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    // go to first day of the week and truncate it hour offset
    val zonedDateTimeTruncated = zonedDateTime.with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(
        ChronoUnit.DAYS)
    //return zonedDateTime epoch second
    return zonedDateTimeTruncated.toEpochSecond()
}

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

/**
 * Obtain the range of the last seven days in milliseconds
 * For example, if today is 8th of May, this fun return the millisecond of 7th May at 00:00:00:000
 * and 7th May at 23:59:59:999
 */
fun getStartAndEndOfYesterday(): Pair<Long,Long>
{
    val now = ZonedDateTime.now()
    val start = now.minusDays(1).truncatedTo(ChronoUnit.DAYS).toEpochSecond() * 1000
    val end = (now.truncatedTo(ChronoUnit.DAYS).toEpochSecond() * 1000 ) - 1
    return Pair(start,end)
}