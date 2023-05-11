package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.UCLA
import es.upm.bienestaremocional.app.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.app.domain.processing.getLastSevenDays
import es.upm.bienestaremocional.app.domain.processing.getStartAndEndOfYesterday
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

/**
 * Implementation of [UCLARepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class UCLARepositoryImpl @Inject constructor (
    private val dao: AppDAO,
    private val logTag: String
): UCLARepository
{
    override suspend fun insert(element: UCLA): Long
    {
        Log.d(logTag, "inserting new ucla")
        return dao.insert(element)
    }

    override suspend fun update(element: UCLA)
    {
        Log.d(logTag, "updating ucla with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun getAll(): List<UCLA>
    {
        Log.d(logTag, "querying all ucla")
        return dao.getAllUCLA()
    }

    override suspend fun getAllFromLastSevenDays(): List<UCLA> {
        Log.d(logTag, "querying all ucla from last seven days")
        val range = getLastSevenDays()
        return dao.getAllUCLAFromRange(range.first,range.second)
    }

    override suspend fun getAllFromCurrentWeek(): List<UCLA> {
        Log.d(logTag, "querying all ucla from current week")
        val range = getCurrentWeek()
        return dao.getAllUCLAFromRange(range.first,range.second)
    }

    override suspend fun getAllFromYesterday(): List<UCLA>
    {
        Log.d(logTag, "querying all ucla from yesterday")
        val range = getStartAndEndOfYesterday()
        return dao.getAllUCLAFromRange(range.first,range.second)
    }

    override suspend fun getAllFromRange(range: Range<LocalDate>): List<UCLA> {
        Log.d(logTag, "querying all ucla between ${range.lower} and ${range.upper}")
        val start = range.lower.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000
        val end = range.upper.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000
        return dao.getAllUCLAFromRange(start,end)
    }

    override suspend fun getAllCompleted(): List<UCLA>
    {
        Log.d(logTag, "querying all ucla completed")
        return dao.getAllUCLACompleted()
    }

    override suspend fun getLastCompleted(): UCLA?
    {
        Log.d(logTag, "querying last ucla completed")
        return dao.getLastUCLACompleted()
    }

    override suspend fun get(id: Long): UCLA?
    {
        Log.d(logTag, "querying ucla with id: $id")
        return dao.getUCLA(id)
    }
}