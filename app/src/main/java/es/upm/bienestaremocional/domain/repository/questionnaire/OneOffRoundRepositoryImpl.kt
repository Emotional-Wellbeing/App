package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.round.OneOffRound
import javax.inject.Inject

/**
 * Repository to interact with [OneOffRound] (only queries)
 */
class OneOffRoundRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
) : OneOffRoundRepository {
    override suspend fun insert(oneOffRound: OneOffRound): Long {
        Log.d(logTag, "inserting new one off round")
        return dao.insert(oneOffRound)
    }

    override suspend fun update(oneOffRound: OneOffRound) {
        Log.d(logTag, "updating one off round with id: ${oneOffRound.id}")
        oneOffRound.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(oneOffRound)
    }

    override suspend fun get(id: Long): OneOffRound? {
        Log.d(logTag, "querying one off round with id: $id")
        return dao.getOneOffRound(id)
    }

    override suspend fun getAll(): List<OneOffRound> {
        Log.d(logTag, "querying all oneOff rounds")
        return dao.getAllOneOffRound()
    }

}