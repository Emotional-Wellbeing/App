package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.PSS
import es.upm.bienestaremocional.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.domain.processing.getLastSevenDays
import es.upm.bienestaremocional.domain.processing.getStartAndEndOfYesterday
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

/**
 * Implementation of [PSSRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class PSSRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
): PSSRepository
{
    override suspend fun insert(element: PSS): Long
    {
        Log.d(logTag, "inserting new pss")
        return dao.insert(element)
    }

    override suspend fun update(element: PSS)
    {
        Log.d(logTag, "updating pss with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun getAll(): List<PSS>
    {
        Log.d(logTag, "querying all pss")
        return dao.getAllPSS()
    }

    override suspend fun getAllFromLastSevenDays(): List<PSS> {
        Log.d(logTag, "querying all pss from last seven days")
        val range = getLastSevenDays()
        return dao.getAllPSSFromRange(range.first,range.second)
    }

    override suspend fun getAllFromCurrentWeek(): List<PSS> {
        Log.d(logTag, "querying all pss from current week")
        val range = getCurrentWeek()
        return dao.getAllPSSFromRange(range.first,range.second)
    }

    override suspend fun getAllFromYesterday(): List<PSS>
    {
        Log.d(logTag, "querying all pss from yesterday")
        val range = getStartAndEndOfYesterday()
        return dao.getAllPSSFromRange(range.first,range.second)
    }

    override suspend fun getAllFromRange(range: Range<LocalDate>): List<PSS>
    {
        Log.d(logTag, "querying all pss between ${range.lower} and ${range.upper}")
        val start = range.lower.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000
        val end = range.upper.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000
        return dao.getAllPSSFromRange(start,end)
    }

    override suspend fun getAllCompleted(): List<PSS>
    {
        Log.d(logTag, "querying all pss completed")
        return dao.getAllPSSCompleted()
    }

    override suspend fun getLastCompleted(): PSS?
    {
        Log.d(logTag, "querying last pss completed")
        return dao.getLastPSSCompleted()
    }

    override suspend fun get(id: Long): PSS?
    {
        Log.d(logTag, "querying pss with id: $id")
        return dao.getPSS(id)
    }
}