package es.upm.bienestaremocional.app.domain.processing

import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireEntity
import java.time.Instant

/**
 * Transform a list of [QuestionnaireEntity] into another of type [Pair]<[Instant],[Int]>
 * Int data represent is the score of these day, computed as the average of the scores of the day
 * @param records list to transform
 * @return records transformed
 */
fun aggregateEntriesPerDay(records: List<QuestionnaireEntity>): List<Pair<Long,Int>> =
    aggregateEntriesPer(records) { truncateTimestampToDay(it) }

/**
 * Transform a list of [QuestionnaireEntity] into another of type [Pair]<[Instant],[Int]>
 * Int data represent is the score of these week, computed as the average of the scores of the week
 */
fun aggregateEntriesPerWeek(records: List<QuestionnaireEntity>): List<Pair<Long,Int>> =
    aggregateEntriesPer(records) { truncateTimestampToWeek(it) }

/**
 * Transform a list of [QuestionnaireEntity] into another of type [Pair]<[Instant],[Int]>
 * Int data represent is the score of these month, computed as the average of the scores of the month
 */
fun aggregateEntriesPerMonth(records: List<QuestionnaireEntity>): List<Pair<Long,Int>> =
    aggregateEntriesPer(records) { truncateTimestampToMonth(it) }

private fun aggregateEntriesPer(records: List<QuestionnaireEntity>, truncate : (Long) -> Long):
        List<Pair<Long,Int>>
{
    val result = mutableListOf<Pair<Long,Int>>()
    val buffer = mutableMapOf<Long,MutableList<Int>>()

    records.forEach { record ->
        record.score?.let { score ->
            val timestampTruncated = truncate(record.createdAt)

            if (buffer.contains(timestampTruncated))
                buffer[timestampTruncated]?.add(score)
            else
                buffer[timestampTruncated] = listOf(score).toMutableList()
        }
    }

    buffer.forEach { entry ->
        val score = entry.value.sum() / entry.value.size
        result.add(Pair(entry.key,score))
    }

    result.sortBy { it.first }

    return result
}