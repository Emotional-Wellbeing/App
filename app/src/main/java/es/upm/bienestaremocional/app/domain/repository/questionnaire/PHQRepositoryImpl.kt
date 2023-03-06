package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Log
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.PHQ
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

    override suspend fun get(id: Long): PHQ?
    {
        Log.d(logTag, "querying phq with id: $id")
        return dao.getPHQ(id)
    }
}