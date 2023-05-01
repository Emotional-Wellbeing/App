package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.PHQ
import es.upm.bienestaremocional.app.domain.processing.getLastSevenDays
import es.upm.bienestaremocional.app.domain.processing.getStartAndEndOfYesterday
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

/**
 * Implementation of [PHQRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class PHQRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
): PHQRepository
{
    override suspend fun insert(element: PHQ): Long
    {
        Log.d(logTag, "inserting new phq")
        return dao.insert(element)
    }

    override suspend fun update(element: PHQ)
    {
        Log.d(logTag, "updating phq with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun getAll(): List<PHQ>
    {
        Log.d(logTag, "querying all phq")
        return dao.getAllPHQ()
    }

    override suspend fun getAllFromLastSevenDays(): List<PHQ> {
        Log.d(logTag, "querying all phq from last seven days")
        val range = getLastSevenDays()
        return dao.getAllPHQFromRange(range.first,range.second)
    }

    override suspend fun getAllFromYesterday(): List<PHQ>
    {
        Log.d(logTag, "querying all phq from yesterday")
        val range = getStartAndEndOfYesterday()
        return dao.getAllPHQFromRange(range.first,range.second)
    }

    override suspend fun getAllFromRange(range: Range<LocalDate>): List<PHQ> {
        Log.d(logTag, "querying all phq between ${range.lower} and ${range.upper}")
        val start = range.lower.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000
        val end = range.upper.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000
        return dao.getAllPHQFromRange(start,end)
    }

    override suspend fun getAllCompleted(): List<PHQ>
    {
        Log.d(logTag, "querying all completed phq")
        return dao.getAllCompletedPHQ()
    }

    override suspend fun get(id: Long): PHQ?
    {
        Log.d(logTag, "querying phq with id: $id")
        return dao.getPHQ(id)
    }
}