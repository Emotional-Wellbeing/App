package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Log
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.PSS
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
    override suspend fun insert(pss: PSS): Long
    {
        Log.d(logTag, "inserting new pss")
        return dao.insert(pss)
    }

    override suspend fun update(pss: PSS)
    {
        Log.d(logTag, "updating pss with id: ${pss.id}")
        pss.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(pss)
    }

    override suspend fun getAll(): List<PSS>
    {
        Log.d(logTag, "querying all pss")
        return dao.getAllPSS()
    }

    override suspend fun get(id: Long): PSS?
    {
        Log.d(logTag, "querying pss with id: $id")
        return dao.getPSS(id)
    }
}