package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffStress
import es.upm.bienestaremocional.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.domain.processing.getLastSevenDays
import es.upm.bienestaremocional.domain.processing.getStartAndEndOfYesterday

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
        val range = getCurrentWeek()
        return dao.getAllOneOffStressFromRange(range.first,range.second)
    }

    override suspend fun getAllFromLastSevenDays(): List<OneOffStress>
    {
        Log.d(logTag, "querying all OneOffStress from last seven days")
        val range = getLastSevenDays()
        return dao.getAllOneOffStressFromRange(range.first,range.second)
    }

    override suspend fun getAllFromRange(range: Range<ZonedDateTime>): List<OneOffStress>
    {
        Log.d(logTag, "querying all OneOffStress between ${range.lower} and ${range.upper}")
        val start = range.lower.toEpochSecond() * 1000
        val end = range.upper.plusDays(1).toEpochSecond() * 1000
        return dao.getAllOneOffStressFromRange(start,end)
    }

    override suspend fun getAllFromYesterday(): List<OneOffStress>
    {
        Log.d(logTag, "querying all OneOffStress from yesterday")
        val range = getStartAndEndOfYesterday()
        return dao.getAllOneOffStressFromRange(range.first,range.second)
    }

    override suspend fun getLastCompleted(): OneOffStress?
    {
        Log.d(logTag, "querying last OneOffStress completed")
        return dao.getLastOneOffStressCompleted()
    }

    /*override suspend fun getAllCompleted(): List<OneOffStress>
    {
        Log.d(logTag, "querying all OneOffStress completed")
        return dao.getAllOneOffStressCompleted()
    }

    override suspend fun getLast(): OneOffStress? {
        Log.d(logTag, "querying last OneOffStress")
        return dao.getLastOneOffStress()
    }*/
}