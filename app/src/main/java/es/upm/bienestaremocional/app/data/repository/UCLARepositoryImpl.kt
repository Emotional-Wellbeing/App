package es.upm.bienestaremocional.app.data.repository

import android.util.Log
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.UCLA

class UCLARepositoryImpl(private val dao: AppDAO): UCLARepository
{
    override suspend fun insert(ucla: UCLA): Long
    {
        Log.d(MainApplication.logTag, "inserting new ucla")
        return dao.insert(ucla)
    }

    override suspend fun update(ucla: UCLA)
    {
        Log.d(MainApplication.logTag, "updating ucla with id: ${ucla.id}")
        ucla.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(ucla)
    }

    override suspend fun getAll(): List<UCLA>
    {
        Log.d(MainApplication.logTag, "querying all ucla")
        return dao.getAllUCLA()
    }

    override suspend fun get(id: Long): UCLA
    {
        Log.d(MainApplication.logTag, "querying ucla with id: $id")
        return dao.getUCLA(id)
    }
}