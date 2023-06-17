package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.round.DailyRoundFull
import javax.inject.Inject

/**
 * Repository to interact with [DailyRoundFull] (only queries)
 */
class DailyRoundFullRepositoryImpl @Inject constructor (
    private val dao: AppDAO,
    private val logTag: String
): DailyRoundFullRepository
{
    override suspend fun get(id: Long): DailyRoundFull?
    {
        Log.d(logTag, "querying daily round full with id: $id")
        return dao.getDailyRoundFull(id)
    }

    override suspend fun getAll(): List<DailyRoundFull>
    {
        Log.d(logTag, "querying all daily rounds full")
        return dao.getAllDailyRoundFull()
    }

    override suspend fun getAllUncompleted(): List<DailyRoundFull>
    {
        Log.d(logTag, "querying daily rounds uncompleted")
        return dao.getAllDailyRoundFullUncompleted()
    }
}