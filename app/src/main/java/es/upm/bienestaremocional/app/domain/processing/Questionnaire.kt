package es.upm.bienestaremocional.app.domain.processing

import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireEntity
import es.upm.bienestaremocional.app.data.questionnaire.Level
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.data.questionnaire.ScoreLevel
import es.upm.bienestaremocional.app.utils.TimeGranularity
import java.time.ZonedDateTime


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

fun reduceEntries(entries : List<PureChartRecord>): Float?
{
    return if (entries.isNotEmpty())
    {
        var result =  0f

        for (entry in entries)
            result += entry.score

        result / entries.size
    }
    else
        null
}

fun processRecords(records: List<QuestionnaireEntity>, timeGranularity: TimeGranularity) :
        List<PureChartRecord>
{
    val criteria = when(timeGranularity)
    {
        TimeGranularity.Day -> ::timestampToStartOfTheDay
        TimeGranularity.Week -> ::timestampToStartOfTheWeek
        TimeGranularity.Month -> ::timestampToStartOfTheMonth
    }

    return processRecords(records = records,
        aggregationCriteria = criteria,
        reduceCriteria = ::averageCriteria
    )
}

fun processRecordsMaintainingEmpty(records: List<QuestionnaireEntity>,
                                   dateRange: Pair<Long, Long>,
                                   timeGranularity: TimeGranularity
) : List<NullableChartRecord>
{
    val updateCriteria : (ZonedDateTime) -> ZonedDateTime = when(timeGranularity)
    {
        TimeGranularity.Day -> { zdt -> zdt.plusDays(1) }
        TimeGranularity.Week -> { zdt -> zdt.plusWeeks(1) }
        TimeGranularity.Month -> { zdt -> zdt.plusMonths(1) }
    }

    val aggregationCriteria = when(timeGranularity)
    {
        TimeGranularity.Day -> ::timestampToStartOfTheDay
        TimeGranularity.Week -> ::timestampToStartOfTheWeek
        TimeGranularity.Month -> ::timestampToStartOfTheMonth
    }

    return processRecordsMaintainingEmpty(records = records,
        dateRange = dateRange,
        updateCriteria = updateCriteria,
        aggregationCriteria = aggregationCriteria,
        reduceCriteria = ::averageCriteriaNullable
    )
}

// TODO implement simulated elements


private fun averageCriteria(list : List<Int>) : Float
{
    require(list.isNotEmpty()) {"List cannot be empty"}
    return list.sum().toFloat() / list.size
}

private fun averageCriteriaNullable(list : List<Int>) : Float?
{
    return if (list.isNotEmpty())
        list.sum().toFloat() / list.size
    else
        null
}

fun processRecords(records: List<QuestionnaireEntity>,
                   aggregationCriteria : (Long) -> ZonedDateTime,
                   reduceCriteria : (List<Int>) -> Float,
) : List<PureChartRecord>
{
    val buffer = mutableMapOf<ZonedDateTime,MutableList<Int>>()
    val result = mutableListOf<PureChartRecord>()
    // Iter over all records
    records.forEach { record ->
        // If this item have score, consider it
        record.score?.let { score ->
            val recordProcessed = aggregationCriteria(record.createdAt)

            if (buffer.contains(recordProcessed))
                buffer[recordProcessed]?.add(score)
            else
                buffer[recordProcessed] = listOf(score).toMutableList()
        }
    }

    // Reduce buffer
    buffer.forEach { entry ->
        result.add(PureChartRecord(entry.key, reduceCriteria(entry.value)))
    }

    // Sort by date
    result.sortBy { it.day.toEpochSecond() }

    return result
}

fun processRecordsMaintainingEmpty(records: List<QuestionnaireEntity>,
                                   dateRange : Pair<Long, Long>,
                                   updateCriteria : (ZonedDateTime) -> ZonedDateTime,
                                   aggregationCriteria : (Long) -> ZonedDateTime,
                                   reduceCriteria : (List<Int>) -> Float?,
) : List<NullableChartRecord>
{
    val buffer = mutableMapOf<ZonedDateTime,MutableList<Int>>()

    // Generate all possible entries
    val start = milliSecondToZonedDateTime(dateRange.first)
    val end = milliSecondToZonedDateTime(dateRange.second)

    var aux = start

    while (aux.isBefore(end))
    {
        buffer[aux] = mutableListOf()
        aux = updateCriteria(aux)
    }

    val result = mutableListOf<NullableChartRecord>()
    // Iter over all records
    records.forEach { record ->
        // If this item have score, consider it
        record.score?.let { score ->
            val recordProcessed = aggregationCriteria(record.createdAt)

            if (buffer.contains(recordProcessed))
                buffer[recordProcessed]?.add(score)
        }
    }

    // Reduce buffer
    buffer.forEach { entry ->
        result.add(NullableChartRecord(entry.key, reduceCriteria(entry.value)))
    }

    // Sort by date
    result.sortBy { it.day.toEpochSecond() }

    return result
}