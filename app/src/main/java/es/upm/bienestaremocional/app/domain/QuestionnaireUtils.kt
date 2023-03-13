package es.upm.bienestaremocional.app.domain

import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireEntity
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * Extract the day of the timestamp and return it.
 * For example, if a timestamp represents 2023/03/10 15:57:23 truncates it into the timestamp of
 * 2023/03/10 00:00:00
 * @param timestamp to truncate
 * @return timestamp truncated in Instant object
 */
fun truncateTimestampIntoDay(timestamp: Long) : Instant =
    Instant.ofEpochMilli(timestamp).truncatedTo(ChronoUnit.DAYS)

/**
 * Transform a list of [QuestionnaireEntity] into another of type [Pair]<[Instant],[Int]>
 * Int data represent is the score of these day, computed as the average of the scores of the day
 */
fun processSameDayEntries(records: List<QuestionnaireEntity>): List<Pair<Instant,Int>>
{
    val result = mutableListOf<Pair<Instant,Int>>()
    val buffer = mutableMapOf<Instant,MutableList<Int>>()

    records.forEach { record ->
        record.score?.let { score ->
            val timestampOfTheDay = truncateTimestampIntoDay(record.createdAt)

            if (buffer.contains(timestampOfTheDay))
                buffer[timestampOfTheDay]?.add(score)
            else
                buffer[timestampOfTheDay] = listOf(score).toMutableList()
        }
    }

    buffer.forEach { entry ->
        val score = entry.value.sum() / entry.value.size
        result.add(Pair(entry.key,score))
    }

    result.sortBy { it.first }

    return result
}
