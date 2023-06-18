package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffStress
import es.upm.bienestaremocional.domain.processing.getCurrentWeekMillisecondTimestamps
import es.upm.bienestaremocional.domain.processing.getLastSevenDaysMillisecondTimestamps
import es.upm.bienestaremocional.domain.processing.getStartAndEndOfYesterdayMillisecondTimestamps

import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * Implementation of [OneOffStressRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class OneOffStressRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
): OneOffStressRepository
{
    override suspend fun insert(element: OneOffStress): Long
    {
        Log.d(logTag, "inserting new OneOffStress")
        return dao.insert(element)
    }

    override suspend fun update(element: OneOffStress)
    {
        Log.d(logTag, "updating OneOffStress with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun get(id: Long): OneOffStress?
    {
        Log.d(logTag, "querying OneOffStress with id: $id")
        return dao.getOneOffStress(id)
    }

    override suspend fun getAll(): List<OneOffStress>
    {
        Log.d(logTag, "querying all OneOffStress")
        return dao.getAllOneOffStress()
    }

    override suspend fun getAllFromCurrentWeek(): List<OneOffStress>
    {
        Log.d(logTag, "querying all OneOffStress from current week")
        val range = getCurrentWeekMillisecondTimestamps()
        return dao.getAllOneOffStressFromRange(range.first,range.second)
    }

    override suspend fun getAllFromLastSevenDays(): List<OneOffStress>
    {
        Log.d(logTag, "querying all OneOffStress from last seven days")
        val range = getLastSevenDaysMillisecondTimestamps()
        return dao.getAllOneOffStressFromRange(range.first,range.second)
    }

    override suspend fun getAllFromRange(
        range: Range<ZonedDateTime>,
        onlyCompleted: Boolean
    ): List<OneOffStress>
    {
        Log.d(logTag, "querying all OneOffStress between ${range.lower} " +
                "and ${range.upper}; only completed: $onlyCompleted")
        val start = range.lower.toEpochSecond() * 1000
        val end = range.upper.plusDays(1).toEpochSecond() * 1000
        return if (onlyCompleted)
            dao.getAllOneOffStressCompletedFromRange(start,end)
        else
            dao.getAllOneOffStressFromRange(start,end)
    }

    override suspend fun getAllFromYesterday(): List<OneOffStress>
    {
        Log.d(logTag, "querying all OneOffStress from yesterday")
        val range = getStartAndEndOfYesterdayMillisecondTimestamps()
        return dao.getAllOneOffStressFromRange(range.first,range.second)
    }

    override suspend fun getLastElement(): OneOffStress?
    {
        Log.d(logTag, "querying last OneOffStress")
        return dao.getLastOneOffStress()
    }
}