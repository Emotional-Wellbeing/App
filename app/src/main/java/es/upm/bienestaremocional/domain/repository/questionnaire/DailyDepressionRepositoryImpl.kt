package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.daily.DailyDepression
import es.upm.bienestaremocional.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.domain.processing.getLastSevenDays
import es.upm.bienestaremocional.domain.processing.getStartAndEndOfYesterday

import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * Implementation of [DailyDepressionRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class DailyDepressionRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
): DailyDepressionRepository
{
    override suspend fun insert(element: DailyDepression): Long
    {
        Log.d(logTag, "inserting new DailyDepression")
        return dao.insert(element)
    }

    override suspend fun update(element: DailyDepression)
    {
        Log.d(logTag, "updating DailyDepression with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun get(id: Long): DailyDepression?
    {
        Log.d(logTag, "querying DailyDepression with id: $id")
        return dao.getDailyDepression(id)
    }

    override suspend fun getAll(): List<DailyDepression>
    {
        Log.d(logTag, "querying all DailyDepression")
        return dao.getAllDailyDepression()
    }

    override suspend fun getAllFromCurrentWeek(): List<DailyDepression>
    {
        Log.d(logTag, "querying all DailyDepression from current week")
        val range = getCurrentWeek()
        return dao.getAllDailyDepressionFromRange(range.first,range.second)
    }

    override suspend fun getAllFromLastSevenDays(): List<DailyDepression>
    {
        Log.d(logTag, "querying all DailyDepression from last seven days")
        val range = getLastSevenDays()
        return dao.getAllDailyDepressionFromRange(range.first,range.second)
    }

    override suspend fun getAllFromRange(
        range: Range<ZonedDateTime>,
        onlyCompleted: Boolean
    ): List<DailyDepression>
    {
        Log.d(logTag, "querying all DailyDepression between ${range.lower} " +
                "and ${range.upper}; only completed: $onlyCompleted")
        val start = range.lower.toEpochSecond() * 1000
        val end = range.upper.plusDays(1).toEpochSecond() * 1000
        return if (onlyCompleted)
            dao.getAllDailyDepressionCompletedFromRange(start,end)
        else
            dao.getAllDailyDepressionFromRange(start,end)
    }

    override suspend fun getAllFromYesterday(): List<DailyDepression>
    {
        Log.d(logTag, "querying all DailyDepression from yesterday")
        val range = getStartAndEndOfYesterday()
        return dao.getAllDailyDepressionFromRange(range.first,range.second)
    }

    override suspend fun getLastElement(): DailyDepression?
    {
        Log.d(logTag, "querying last DailyDepression")
        return dao.getLastDailyDepression()
    }
}