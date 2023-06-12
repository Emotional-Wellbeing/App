package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.daily.DailyStress
import es.upm.bienestaremocional.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.domain.processing.getLastSevenDays
import es.upm.bienestaremocional.domain.processing.getStartAndEndOfYesterday

import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * Implementation of [DailyStressRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class DailyStressRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
): DailyStressRepository
{
    override suspend fun insert(element: DailyStress): Long
    {
        Log.d(logTag, "inserting new DailyStress")
        return dao.insert(element)
    }

    override suspend fun update(element: DailyStress)
    {
        Log.d(logTag, "updating DailyStress with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun get(id: Long): DailyStress?
    {
        Log.d(logTag, "querying DailyStress with id: $id")
        return dao.getDailyStress(id)
    }

    override suspend fun getAll(): List<DailyStress>
    {
        Log.d(logTag, "querying all DailyStress")
        return dao.getAllDailyStress()
    }

    override suspend fun getAllFromCurrentWeek(): List<DailyStress> 
    {
        Log.d(logTag, "querying all DailyStress from current week")
        val range = getCurrentWeek()
        return dao.getAllDailyStressFromRange(range.first,range.second)
    }

    override suspend fun getAllFromLastSevenDays(): List<DailyStress> 
    {
        Log.d(logTag, "querying all DailyStress from last seven days")
        val range = getLastSevenDays()
        return dao.getAllDailyStressFromRange(range.first,range.second)
    }

    override suspend fun getAllFromRange(
        range: Range<ZonedDateTime>,
        onlyCompleted: Boolean
    ): List<DailyStress>
    {
        Log.d(logTag, "querying all DailyStress between ${range.lower} " +
                "and ${range.upper}; only completed: $onlyCompleted")
        val start = range.lower.toEpochSecond() * 1000
        val end = range.upper.plusDays(1).toEpochSecond() * 1000
        return if (onlyCompleted)
            dao.getAllDailyStressCompletedFromRange(start,end)
        else
            dao.getAllDailyStressFromRange(start,end)
    }

    override suspend fun getAllFromYesterday(): List<DailyStress>
    {
        Log.d(logTag, "querying all DailyStress from yesterday")
        val range = getStartAndEndOfYesterday()
        return dao.getAllDailyStressFromRange(range.first,range.second)
    }

    override suspend fun getLastCompleted(): DailyStress?
    {
        Log.d(logTag, "querying last DailyStress completed")
        return dao.getLastDailyStressCompleted()
    }

    /*override suspend fun getAllCompleted(): List<DailyStress>
    {
        Log.d(logTag, "querying all DailyStress completed")
        return dao.getAllDailyStressCompleted()
    }

    override suspend fun getLast(): DailyStress? {
        Log.d(logTag, "querying last DailyStress")
        return dao.getLastDailyStress()
    }*/
}