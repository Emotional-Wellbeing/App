package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import javax.inject.Inject

/**
 * Repository to interact with [DailyRound] (only queries)
 */
class DailyRoundRepositoryImpl @Inject constructor (
    private val dao: AppDAO,
    private val logTag: String
): DailyRoundRepository
{
    override suspend fun insert(dailyRound: DailyRound): Long
    {
        Log.d(logTag, "inserting new daily round")
        return dao.insert(dailyRound)
    }

    override suspend fun update(dailyRound: DailyRound)
    {
        Log.d(logTag, "updating daily round with id: ${dailyRound.id}")
        dailyRound.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(dailyRound)
    }

    override suspend fun get(id: Long): DailyRound?
    {
        Log.d(logTag, "querying daily round with id: $id")
        return dao.getDailyRound(id)
    }

    override suspend fun getAll(): List<DailyRound>
    {
        Log.d(logTag, "querying all daily rounds")
        return dao.getAllDailyRound()
    }

}