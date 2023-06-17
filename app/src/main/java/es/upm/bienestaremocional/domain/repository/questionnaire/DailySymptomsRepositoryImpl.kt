package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.daily.DailySymptoms
import es.upm.bienestaremocional.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.domain.processing.getLastSevenDays
import es.upm.bienestaremocional.domain.processing.getStartAndEndOfYesterday

import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * Implementation of [DailySymptomsRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class DailySymptomsRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
): DailySymptomsRepository
{
    override suspend fun insert(element: DailySymptoms): Long
    {
        Log.d(logTag, "inserting new DailySymptoms")
        return dao.insert(element)
    }

    override suspend fun update(element: DailySymptoms)
    {
        Log.d(logTag, "updating DailySymptoms with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun get(id: Long): DailySymptoms?
    {
        Log.d(logTag, "querying DailySymptoms with id: $id")
        return dao.getDailySymptoms(id)
    }

    override suspend fun getAll(): List<DailySymptoms>
    {
        Log.d(logTag, "querying all DailySymptoms")
        return dao.getAllDailySymptoms()
    }

    override suspend fun getAllFromCurrentWeek(): List<DailySymptoms>
    {
        Log.d(logTag, "querying all DailySymptoms from current week")
        val range = getCurrentWeek()
        return dao.getAllDailySymptomsFromRange(range.first,range.second)
    }

    override suspend fun getAllFromLastSevenDays(): List<DailySymptoms>
    {
        Log.d(logTag, "querying all DailySymptoms from last seven days")
        val range = getLastSevenDays()
        return dao.getAllDailySymptomsFromRange(range.first,range.second)
    }

    override suspend fun getAllFromRange(
        range: Range<ZonedDateTime>,
        onlyCompleted: Boolean
    ): List<DailySymptoms>
    {
        Log.d(logTag, "querying all DailySymptoms between ${range.lower} " +
                "and ${range.upper}; only completed: $onlyCompleted")
        val start = range.lower.toEpochSecond() * 1000
        val end = range.upper.plusDays(1).toEpochSecond() * 1000
        return if (onlyCompleted)
            dao.getAllDailySymptomsCompletedFromRange(start,end)
        else
            dao.getAllDailySymptomsFromRange(start,end)
    }

    override suspend fun getAllFromYesterday(): List<DailySymptoms>
    {
        Log.d(logTag, "querying all DailySymptoms from yesterday")
        val range = getStartAndEndOfYesterday()
        return dao.getAllDailySymptomsFromRange(range.first,range.second)
    }

    override suspend fun getLastElement(): DailySymptoms?
    {
        Log.d(logTag, "querying last DailySymptoms")
        return dao.getLastDailySymptoms()
    }
}