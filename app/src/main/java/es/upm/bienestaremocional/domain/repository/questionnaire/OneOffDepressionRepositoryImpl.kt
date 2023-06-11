package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffDepression
import es.upm.bienestaremocional.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.domain.processing.getLastSevenDays
import es.upm.bienestaremocional.domain.processing.getStartAndEndOfYesterday

import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * Implementation of [OneOffDepressionRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class OneOffDepressionRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
): OneOffDepressionRepository
{
    override suspend fun insert(element: OneOffDepression): Long
    {
        Log.d(logTag, "inserting new OneOffDepression")
        return dao.insert(element)
    }

    override suspend fun update(element: OneOffDepression)
    {
        Log.d(logTag, "updating OneOffDepression with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun get(id: Long): OneOffDepression?
    {
        Log.d(logTag, "querying OneOffDepression with id: $id")
        return dao.getOneOffDepression(id)
    }

    override suspend fun getAll(): List<OneOffDepression>
    {
        Log.d(logTag, "querying all OneOffDepression")
        return dao.getAllOneOffDepression()
    }

    override suspend fun getAllFromCurrentWeek(): List<OneOffDepression> 
    {
        Log.d(logTag, "querying all OneOffDepression from current week")
        val range = getCurrentWeek()
        return dao.getAllOneOffDepressionFromRange(range.first,range.second)
    }

    override suspend fun getAllFromLastSevenDays(): List<OneOffDepression> 
    {
        Log.d(logTag, "querying all OneOffDepression from last seven days")
        val range = getLastSevenDays()
        return dao.getAllOneOffDepressionFromRange(range.first,range.second)
    }

    override suspend fun getAllFromRange(range: Range<ZonedDateTime>): List<OneOffDepression>
    {
        Log.d(logTag, "querying all OneOffDepression between ${range.lower} and ${range.upper}")
        val start = range.lower.toEpochSecond() * 1000
        val end = range.upper.plusDays(1).toEpochSecond() * 1000
        return dao.getAllOneOffDepressionFromRange(start,end)
    }

    override suspend fun getAllFromYesterday(): List<OneOffDepression>
    {
        Log.d(logTag, "querying all OneOffDepression from yesterday")
        val range = getStartAndEndOfYesterday()
        return dao.getAllOneOffDepressionFromRange(range.first,range.second)
    }

    override suspend fun getLastCompleted(): OneOffDepression?
    {
        Log.d(logTag, "querying last OneOffDepression completed")
        return dao.getLastOneOffDepressionCompleted()
    }

    /*override suspend fun getAllCompleted(): List<OneOffDepression>
    {
        Log.d(logTag, "querying all OneOffDepression completed")
        return dao.getAllOneOffDepressionCompleted()
    }

    override suspend fun getLast(): OneOffDepression? {
        Log.d(logTag, "querying last OneOffDepression")
        return dao.getLastOneOffDepression()
    }*/
}