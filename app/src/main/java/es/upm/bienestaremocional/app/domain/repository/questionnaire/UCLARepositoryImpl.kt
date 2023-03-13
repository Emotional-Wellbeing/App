package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Log
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.UCLA
import javax.inject.Inject

/**
 * Implementation of [UCLARepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class UCLARepositoryImpl @Inject constructor (
    private val dao: AppDAO,
    private val logTag: String
): UCLARepository
{
    override suspend fun insert(element: UCLA): Long
    {
        Log.d(logTag, "inserting new ucla")
        return dao.insert(element)
    }

    override suspend fun update(element: UCLA)
    {
        Log.d(logTag, "updating ucla with id: ${element.id}")
        element.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(element)
    }

    override suspend fun getAll(): List<UCLA>
    {
        Log.d(logTag, "querying all ucla")
        return dao.getAllUCLA()
    }

    override suspend fun getAllFromLastSevenDays(): List<UCLA> {
        Log.d(logTag, "querying all ucla from last seven days")
        return dao.getAllUCLAFromLastSevenDays()
    }

    override suspend fun getAllCompleted(): List<UCLA>
    {
        Log.d(logTag, "querying all completed ucla")
        return dao.getAllCompletedUCLA()
    }

    override suspend fun get(id: Long): UCLA?
    {
        Log.d(logTag, "querying ucla with id: $id")
        return dao.getUCLA(id)
    }
}