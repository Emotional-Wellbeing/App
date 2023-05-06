package es.upm.bienestaremocional.app.domain.processing

import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireEntity
import es.upm.bienestaremocional.app.data.questionnaire.Level
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.data.questionnaire.ScoreLevel

/**
 * Transform a list of [QuestionnaireEntity] into another of type [Pair]<[Long],[Float]>
 * Float data represent is the score of these day, computed as the average of the scores of the day
 * @param records list to transform
 * @return records transformed
 */
fun aggregateEntriesPerDay(records: List<QuestionnaireEntity>): List<Pair<Long,Float>> =
    aggregateEntriesPer(records) { truncateTimestampToDay(it) }

/**
 * Transform a list of [QuestionnaireEntity] into another of type [Pair]<[Long],[Float]>
 * Float data represent is the score of these week, computed as the average of the scores of the week
 */
fun aggregateEntriesPerWeek(records: List<QuestionnaireEntity>): List<Pair<Long,Float>> =
    aggregateEntriesPer(records) { truncateTimestampToWeek(it) }

/**
 * Transform a list of [QuestionnaireEntity] into another of type [Pair]<[Long],[Float]>
 * Float data represent is the score of these month, computed as the average of the scores of the month
 */
fun aggregateEntriesPerMonth(records: List<QuestionnaireEntity>): List<Pair<Long,Float>> =
    aggregateEntriesPer(records) { truncateTimestampToMonth(it) }

private fun aggregateEntriesPer(records: List<QuestionnaireEntity>, truncate : (Long) -> Long):
        List<Pair<Long,Float>>
{
    val result = mutableListOf<Pair<Long,Float>>()
    val buffer = mutableMapOf<Long,MutableList<Int>>()

    // Truncate timestamp and add their scores
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
        val score = entry.value.sum().toFloat() / entry.value.size
        result.add(Pair(entry.key,score))
    }

    result.sortBy { it.first }

    return result
}

fun scoreToLevel(score: Int, questionnaire: Questionnaire): Level?
{
    var scoreLevel: ScoreLevel? = null
    for(level in questionnaire.levels)
    {
        if (score in level.min .. level.max)
        {
            scoreLevel = level
            break
        }
    }
    return scoreLevel?.level
}