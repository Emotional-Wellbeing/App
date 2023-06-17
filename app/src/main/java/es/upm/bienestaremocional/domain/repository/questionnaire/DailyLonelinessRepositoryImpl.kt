package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.daily.DailyLoneliness
import es.upm.bienestaremocional.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.domain.processing.getLastSevenDays
import es.upm.bienestaremocional.domain.processing.getStartAndEndOfYesterday

import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * Implementation of [DailyLonelinessRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class DailyLonelinessRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
): DailyLonelinessRepository
{
    override suspend fun insert(element: DailyLoneliness): Long
    {
        Log.d(logTag, "inserting new DailyLoneliness")
        return dao.insert(element)
    }

    override suspend fun update(element: DailyLoneliness)
    {
        Log.d(logTag, "updating DailyLoneliness with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun get(id: Long): DailyLoneliness?
    {
        Log.d(logTag, "querying DailyLoneliness with id: $id")
        return dao.getDailyLoneliness(id)
    }

    override suspend fun getAll(): List<DailyLoneliness>
    {
        Log.d(logTag, "querying all DailyLoneliness")
        return dao.getAllDailyLoneliness()
    }

    override suspend fun getAllFromCurrentWeek(): List<DailyLoneliness>
    {
        Log.d(logTag, "querying all DailyLoneliness from current week")
        val range = getCurrentWeek()
        return dao.getAllDailyLonelinessFromRange(range.first,range.second)
    }

    override suspend fun getAllFromLastSevenDays(): List<DailyLoneliness>
    {
        Log.d(logTag, "querying all DailyLoneliness from last seven days")
        val range = getLastSevenDays()
        return dao.getAllDailyLonelinessFromRange(range.first,range.second)
    }

    override suspend fun getAllFromRange(
        range: Range<ZonedDateTime>,
        onlyCompleted: Boolean
    ): List<DailyLoneliness>
    {
        Log.d(logTag, "querying all DailyLoneliness between ${range.lower} " +
                "and ${range.upper}; only completed: $onlyCompleted")
        val start = range.lower.toEpochSecond() * 1000
        val end = range.upper.plusDays(1).toEpochSecond() * 1000
        return if (onlyCompleted)
            dao.getAllDailyLonelinessCompletedFromRange(start,end)
        else
            dao.getAllDailyLonelinessFromRange(start,end)
    }

    override suspend fun getAllFromYesterday(): List<DailyLoneliness>
    {
        Log.d(logTag, "querying all DailyLoneliness from yesterday")
        val range = getStartAndEndOfYesterday()
        return dao.getAllDailyLonelinessFromRange(range.first,range.second)
    }

    override suspend fun getLastCompleted(): DailyLoneliness?
    {
        Log.d(logTag, "querying last DailyLoneliness completed")
        return dao.getLastDailyLonelinessCompleted()
    }

    /*override suspend fun getAllCompleted(): List<DailyLoneliness>
    {
        Log.d(logTag, "querying all DailyLoneliness completed")
        return dao.getAllDailyLonelinessCompleted()
    }

    override suspend fun getLast(): DailyLoneliness? {
        Log.d(logTag, "querying last DailyLoneliness")
        return dao.getLastDailyLoneliness()
    }*/
}