package es.upm.bienestaremocional.domain.processing

import android.util.Range
import es.upm.bienestaremocional.data.Advice
import es.upm.bienestaremocional.data.Measure
import es.upm.bienestaremocional.data.database.entity.ScoredEntity
import es.upm.bienestaremocional.data.questionnaire.Level
import es.upm.bienestaremocional.data.questionnaire.QuestionnaireScored
import es.upm.bienestaremocional.data.questionnaire.ScoreLevel
import es.upm.bienestaremocional.utils.TimeGranularity
import java.time.ZonedDateTime

fun scoreToLevel(score: Int, questionnaire: QuestionnaireScored): Level? {
    var scoreLevel: ScoreLevel? = null
    for (level in questionnaire.levels) {
        if (score in level.min..level.max) {
            scoreLevel = level
            break
        }
    }
    return scoreLevel?.level
}

fun levelToAdvice(level: Level, measure: Measure): Advice? {
    return measure.advices?.let {
        val advices = it[level]
        advices?.let {
            val index = (advices.indices).random()
            advices[index]
        }
    }
}

fun reduceEntries(entries: List<PureChartRecord>): Float? {
    return if (entries.isNotEmpty()) {
        var result = 0f

        for (entry in entries)
            result += entry.score

        result / entries.size
    }
    else
        null
}

fun processRecords(
    records: List<ScoredEntity>,
    timeGranularity: TimeGranularity
): List<PureChartRecord> {
    return processRecords(
        records = records,
        timeGranularity = timeGranularity,
        reduceCriteria = ::averageCriteria
    )
}

fun processRecords(
    records: List<ScoredEntity>,
    timeGranularity: TimeGranularity,
    reduceCriteria: (List<Int>) -> Float,
): List<PureChartRecord> {
    val buffer = mutableMapOf<ZonedDateTime, MutableList<Int>>()
    val result = mutableListOf<PureChartRecord>()
    // Iter over all records
    records.forEach { record ->
        // If this item have score, consider it
        record.score?.let { score ->
            val recordProcessed = when (timeGranularity) {
                TimeGranularity.Day -> timestampToStartOfTheDay(record.createdAt)
                TimeGranularity.Week -> timestampToStartOfTheWeek(record.createdAt)
                TimeGranularity.Month -> timestampToStartOfTheMonth(record.createdAt)
            }

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

fun processRecordsMaintainingEmpty(
    records: List<ScoredEntity>,
    dateRange: Range<ZonedDateTime>,
    timeGranularity: TimeGranularity
): List<NullableChartRecord> {
    return processRecordsMaintainingEmpty(
        records = records,
        dateRange = dateRange,
        timeGranularity = timeGranularity,
        reduceCriteria = ::averageCriteriaNullable
    )
}

fun processRecordsMaintainingEmpty(
    records: List<ScoredEntity>,
    dateRange: Range<ZonedDateTime>,
    timeGranularity: TimeGranularity,
    reduceCriteria: (List<Int>) -> Float?,
): List<NullableChartRecord> {
    val buffer = mutableMapOf<ZonedDateTime, MutableList<Int>>()

    // Ensure that the lower of dateRange is on the start of the day and the upper is on the end of the day
    val dateRangeVerified = dateRange.lowerStartDayUpperEndDay()

    val start = dateRangeVerified.lower
    val end = dateRangeVerified.upper

    var aux = when (timeGranularity) {
        TimeGranularity.Day -> start
        TimeGranularity.Week -> start.toStartOfTheWeek()
        TimeGranularity.Month -> start.toStartOfTheMonth()
    }

    while (aux.isBefore(end)) {
        buffer[aux] = mutableListOf()
        aux = when (timeGranularity) {
            TimeGranularity.Day -> aux.plusDays(1)
            TimeGranularity.Week -> aux.plusWeeks(1)
            TimeGranularity.Month -> aux.plusMonths(1)
        }
    }

    val result = mutableListOf<NullableChartRecord>()
    // Iter over all records
    records.forEach { record ->
        // If this item have score, consider it
        record.score?.let { score ->
            val recordProcessed = when (timeGranularity) {
                TimeGranularity.Day -> timestampToStartOfTheDay(record.createdAt)
                TimeGranularity.Week -> timestampToStartOfTheWeek(record.createdAt)
                TimeGranularity.Month -> timestampToStartOfTheMonth(record.createdAt)
            }

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

fun processRecordsSimulatingEmpty(
    records: List<ScoredEntity>,
    dateRange: Range<ZonedDateTime>,
    timeGranularity: TimeGranularity,
): List<HybridChartRecord> {
    return processRecordsSimulatingEmpty(
        records = records,
        dateRange = dateRange,
        timeGranularity = timeGranularity,
        reduceCriteria = ::averageCriteriaNullable,
        onNullScore = ::imputationMethod
    )
}

fun processRecordsSimulatingEmpty(
    records: List<ScoredEntity>,
    dateRange: Range<ZonedDateTime>,
    timeGranularity: TimeGranularity,
    reduceCriteria: (List<Int>) -> Float?,
    onNullScore: (Int, List<NullableChartRecord>) -> Float
): List<HybridChartRecord> {
    val rawData = processRecordsMaintainingEmpty(
        records = records,
        dateRange = dateRange,
        timeGranularity = timeGranularity,
        reduceCriteria = reduceCriteria,
    )

    return rawData.mapIndexed { index, element ->
        val score = element.score ?: onNullScore(index, rawData)
        val simulated = element.score == null
        HybridChartRecord(
            day = element.day,
            score = score,
            simulated = simulated
        )
    }
}

private fun averageCriteria(list: List<Int>): Float {
    require(list.isNotEmpty()) { "List cannot be empty" }
    return list.sum().toFloat() / list.size
}

private fun averageCriteriaNullable(list: List<Int>): Float? {
    return if (list.isNotEmpty())
        list.sum().toFloat() / list.size
    else
        null
}

private fun imputationMethod(
    index: Int,
    data: List<NullableChartRecord>,
    numberOfPreviousRecords: Int = 3,
): Float {
    require(data.isNotEmpty()) { "List cannot be empty" }
    require(index >= 0) { "Index cannot be negative" }
    require(index < data.size) { "Index cannot upper than list's last index" }

    val buffer = mutableListOf<Float>()

    //Read last 3 elements (if possible)
    for (offset in 1..numberOfPreviousRecords) {
        data.getOrNull(index - offset)?.let { record ->
            record.score?.let { buffer.add(it) }
        }
    }

    // If buffer is empty, no data can be used, so place average of all list
    // If buffer is not empty, compute score of buffer entries using average by default

    var result = 0f
    if (buffer.isEmpty()) {
        val dataScores = data.mapNotNull { it.score }
        if (dataScores.isNotEmpty())
            result = dataScores.average().toFloat()
    }
    else
        result = buffer.average().toFloat()

    return result
}