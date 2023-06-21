package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import es.upm.bienestaremocional.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.domain.processing.getLastSevenDays
import es.upm.bienestaremocional.domain.processing.getStartAndEndOfYesterday

import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * Implementation of [DailySuicideRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class DailySuicideRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
) : DailySuicideRepository {
    override suspend fun insert(element: DailySuicide): Long {
        Log.d(logTag, "inserting new DailySuicide")
        return dao.insert(element)
    }

    override suspend fun update(element: DailySuicide) {
        Log.d(logTag, "updating DailySuicide with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun get(id: Long): DailySuicide? {
        Log.d(logTag, "querying DailySuicide with id: $id")
        return dao.getDailySuicide(id)
    }

    override suspend fun getAll(): List<DailySuicide> {
        Log.d(logTag, "querying all DailySuicide")
        return dao.getAllDailySuicide()
    }

    override suspend fun getAllFromCurrentWeek(): List<DailySuicide> {
        Log.d(logTag, "querying all DailySuicide from current week")
        val range = getCurrentWeek()
        return dao.getAllDailySuicideFromRange(range.first, range.second)
    }

    override suspend fun getAllFromLastSevenDays(): List<DailySuicide> {
        Log.d(logTag, "querying all DailySuicide from last seven days")
        val range = getLastSevenDays()
        return dao.getAllDailySuicideFromRange(range.first, range.second)
    }

    override suspend fun getAllFromRange(
        range: Range<ZonedDateTime>,
        onlyCompleted: Boolean
    ): List<DailySuicide> {
        Log.d(
            logTag, "querying all DailySuicide between ${range.lower} " +
                    "and ${range.upper}; only completed: $onlyCompleted"
        )
        val start = range.lower.toEpochSecond() * 1000
        val end = range.upper.plusDays(1).toEpochSecond() * 1000
        return if (onlyCompleted)
            dao.getAllDailySuicideCompletedFromRange(start, end)
        else
            dao.getAllDailySuicideFromRange(start, end)
    }

    override suspend fun getAllFromYesterday(): List<DailySuicide> {
        Log.d(logTag, "querying all DailySuicide from yesterday")
        val range = getStartAndEndOfYesterday()
        return dao.getAllDailySuicideFromRange(range.first, range.second)
    }

    override suspend fun getLastCompleted(): DailySuicide? {
        Log.d(logTag, "querying last DailySuicide completed")
        return dao.getLastDailySuicideCompleted()
    }

    /*override suspend fun getAllCompleted(): List<DailySuicide>
    {
        Log.d(logTag, "querying all DailySuicide completed")
        return dao.getAllDailySuicideCompleted()
    }

    override suspend fun getLast(): DailySuicide? {
        Log.d(logTag, "querying last DailySuicide")
        return dao.getLastDailySuicide()
    }*/
}