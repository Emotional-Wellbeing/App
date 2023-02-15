package es.upm.bienestaremocional.app.data.repository

import android.util.Log
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.PHQ

class PHQRepositoryImpl(private val dao: AppDAO): PHQRepository
{
    override suspend fun insert(phq: PHQ): Long
    {
        Log.d(MainApplication.logTag, "inserting new phq")
        return dao.insert(phq)
    }

    override suspend fun update(phq: PHQ)
    {
        Log.d(MainApplication.logTag, "updating phq with id: ${phq.id}")
        phq.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(phq)
    }

    override suspend fun getAll(): List<PHQ>
    {
        Log.d(MainApplication.logTag, "querying all phq")
        return dao.getAllPHQ()
    }

    override suspend fun get(id: Long): PHQ
    {
        Log.d(MainApplication.logTag, "querying phq with id: $id")
        return dao.getPHQ(id)
    }
}