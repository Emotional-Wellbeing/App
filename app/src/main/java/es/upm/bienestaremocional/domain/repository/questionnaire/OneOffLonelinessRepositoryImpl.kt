package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffLoneliness
import es.upm.bienestaremocional.domain.processing.getCurrentWeekMillisecondTimestamps
import es.upm.bienestaremocional.domain.processing.getLastSevenDaysMillisecondTimestamps
import es.upm.bienestaremocional.domain.processing.getStartAndEndOfYesterdayMillisecondTimestamps

import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * Implementation of [OneOffLonelinessRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class OneOffLonelinessRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
) : OneOffLonelinessRepository {
    override suspend fun insert(element: OneOffLoneliness): Long {
        Log.d(logTag, "inserting new OneOffLoneliness")
        return dao.insert(element)
    }

    override suspend fun update(element: OneOffLoneliness) {
        Log.d(logTag, "updating OneOffLoneliness with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun get(id: Long): OneOffLoneliness? {
        Log.d(logTag, "querying OneOffLoneliness with id: $id")
        return dao.getOneOffLoneliness(id)
    }

    override suspend fun getAll(): List<OneOffLoneliness> {
        Log.d(logTag, "querying all OneOffLoneliness")
        return dao.getAllOneOffLoneliness()
    }

    override suspend fun getAllFromCurrentWeek(): List<OneOffLoneliness> {
        Log.d(logTag, "querying all OneOffLoneliness from current week")
        val range = getCurrentWeekMillisecondTimestamps()
        return dao.getAllOneOffLonelinessFromRange(range.first, range.second)
    }

    override suspend fun getAllFromLastSevenDays(): List<OneOffLoneliness> {
        Log.d(logTag, "querying all OneOffLoneliness from last seven days")
        val range = getLastSevenDaysMillisecondTimestamps()
        return dao.getAllOneOffLonelinessFromRange(range.first, range.second)
    }

    override suspend fun getAllFromRange(
        range: Range<ZonedDateTime>,
        onlyCompleted: Boolean
    ): List<OneOffLoneliness> {
        Log.d(
            logTag, "querying all OneOffLoneliness between ${range.lower} " +
                    "and ${range.upper}; only completed: $onlyCompleted"
        )
        val start = range.lower.toEpochSecond() * 1000
        val end = range.upper.plusDays(1).toEpochSecond() * 1000
        return if (onlyCompleted)
            dao.getAllOneOffLonelinessCompletedFromRange(start, end)
        else
            dao.getAllOneOffLonelinessFromRange(start, end)
    }

    override suspend fun getAllFromYesterday(): List<OneOffLoneliness> {
        Log.d(logTag, "querying all OneOffLoneliness from yesterday")
        val range = getStartAndEndOfYesterdayMillisecondTimestamps()
        return dao.getAllOneOffLonelinessFromRange(range.first, range.second)
    }

    override suspend fun getLastElement(): OneOffLoneliness? {
        Log.d(logTag, "querying last OneOffLoneliness")
        return dao.getLastOneOffLoneliness()
    }
}