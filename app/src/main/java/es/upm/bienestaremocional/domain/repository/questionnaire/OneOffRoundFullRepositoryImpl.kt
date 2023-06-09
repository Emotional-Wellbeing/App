package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.round.OneOffRoundFull
import javax.inject.Inject

/**
 * Repository to interact with [OneOffRoundFull] (only queries)
 */
class OneOffRoundFullRepositoryImpl @Inject constructor (
    private val dao: AppDAO,
    private val logTag: String
): OneOffRoundFullRepository
{
    override suspend fun get(id: Long): OneOffRoundFull?
    {
        Log.d(logTag, "querying one off round full with id: $id")
        return dao.getOneOffRoundFull(id)
    }

    override suspend fun getAll(): List<OneOffRoundFull>
    {
        Log.d(logTag, "querying one off daily rounds full")
        return dao.getAllOneOffRoundFull()
    }

    override suspend fun getAllUncompleted(): List<OneOffRoundFull>
    {
        Log.d(logTag, "querying one off rounds uncompleted")
        return dao.getAllOneOffRoundFullUncompleted()
    }
}