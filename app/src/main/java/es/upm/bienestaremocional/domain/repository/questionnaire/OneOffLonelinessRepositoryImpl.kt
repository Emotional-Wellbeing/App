package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffLoneliness
import es.upm.bienestaremocional.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.domain.processing.getLastSevenDays
import es.upm.bienestaremocional.domain.processing.getStartAndEndOfYesterday

import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * Implementation of [OneOffLonelinessRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class OneOffLonelinessRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
): OneOffLonelinessRepository
{
    override suspend fun insert(element: OneOffLoneliness): Long
    {
        Log.d(logTag, "inserting new OneOffLoneliness")
        return dao.insert(element)
    }

    override suspend fun update(element: OneOffLoneliness)
    {
        Log.d(logTag, "updating OneOffLoneliness with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun get(id: Long): OneOffLoneliness?
    {
        Log.d(logTag, "querying OneOffLoneliness with id: $id")
        return dao.getOneOffLoneliness(id)
    }

    override suspend fun getAll(): List<OneOffLoneliness>
    {
        Log.d(logTag, "querying all OneOffLoneliness")
        return dao.getAllOneOffLoneliness()
    }

    override suspend fun getAllFromCurrentWeek(): List<OneOffLoneliness> 
    {
        Log.d(logTag, "querying all OneOffLoneliness from current week")
        val range = getCurrentWeek()
        return dao.getAllOneOffLonelinessFromRange(range.first,range.second)
    }

    override suspend fun getAllFromLastSevenDays(): List<OneOffLoneliness> 
    {
        Log.d(logTag, "querying all OneOffLoneliness from last seven days")
        val range = getLastSevenDays()
        return dao.getAllOneOffLonelinessFromRange(range.first,range.second)
    }

    override suspend fun getAllFromRange(range: Range<ZonedDateTime>): List<OneOffLoneliness>
    {
        Log.d(logTag, "querying all OneOffLoneliness between ${range.lower} and ${range.upper}")
        val start = range.lower.toEpochSecond() * 1000
        val end = range.upper.plusDays(1).toEpochSecond() * 1000
        return dao.getAllOneOffLonelinessFromRange(start,end)
    }

    override suspend fun getAllFromYesterday(): List<OneOffLoneliness>
    {
        Log.d(logTag, "querying all OneOffLoneliness from yesterday")
        val range = getStartAndEndOfYesterday()
        return dao.getAllOneOffLonelinessFromRange(range.first,range.second)
    }

    override suspend fun getLastCompleted(): OneOffLoneliness?
    {
        Log.d(logTag, "querying last OneOffLoneliness completed")
        return dao.getLastOneOffLonelinessCompleted()
    }

    /*override suspend fun getAllCompleted(): List<OneOffLoneliness>
    {
        Log.d(logTag, "querying all OneOffLoneliness completed")
        return dao.getAllOneOffLonelinessCompleted()
    }

    override suspend fun getLast(): OneOffLoneliness? {
        Log.d(logTag, "querying last OneOffLoneliness")
        return dao.getLastOneOffLoneliness()
    }*/
}