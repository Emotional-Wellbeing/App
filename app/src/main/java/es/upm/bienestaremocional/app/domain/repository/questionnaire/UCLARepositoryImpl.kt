package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Log
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.UCLA
import javax.inject.Inject

class UCLARepositoryImpl @Inject constructor (
    private val dao: AppDAO,
    private val logTag: String
): UCLARepository
{
    override suspend fun insert(ucla: UCLA): Long
    {
        Log.d(logTag, "inserting new ucla")
        return dao.insert(ucla)
    }

    override suspend fun update(ucla: UCLA)
    {
        Log.d(logTag, "updating ucla with id: ${ucla.id}")
        ucla.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(ucla)
    }

    override suspend fun getAll(): List<UCLA>
    {
        Log.d(logTag, "querying all ucla")
        return dao.getAllUCLA()
    }

    override suspend fun get(id: Long): UCLA
    {
        Log.d(logTag, "querying ucla with id: $id")
        return dao.getUCLA(id)
    }
}