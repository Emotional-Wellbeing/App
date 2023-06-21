package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import es.upm.bienestaremocional.domain.processing.getCurrentWeekMillisecondTimestamps
import es.upm.bienestaremocional.domain.processing.getLastSevenDaysMillisecondTimestamps
import es.upm.bienestaremocional.domain.processing.getStartAndEndOfYesterdayMillisecondTimestamps

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
        val range = getCurrentWeekMillisecondTimestamps()
        return dao.getAllDailySuicideFromRange(range.first, range.second)
    }

    override suspend fun getAllFromLastSevenDays(): List<DailySuicide> {
        Log.d(logTag, "querying all DailySuicide from last seven days")
        val range = getLastSevenDaysMillisecondTimestamps()
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
        val range = getStartAndEndOfYesterdayMillisecondTimestamps()
        return dao.getAllDailySuicideFromRange(range.first, range.second)
    }

    override suspend fun getLastElement(): DailySuicide? {
        Log.d(logTag, "querying last DailySuicide")
        return dao.getLastDailySuicide()
    }

}