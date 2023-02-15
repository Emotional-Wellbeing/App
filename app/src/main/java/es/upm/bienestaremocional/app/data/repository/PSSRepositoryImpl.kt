package es.upm.bienestaremocional.app.data.repository

import android.util.Log
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.PSS

class PSSRepositoryImpl(private val dao: AppDAO): PSSRepository
{
    override suspend fun insert(pss: PSS): Long
    {
        Log.d(MainApplication.logTag, "inserting new pss")
        return dao.insert(pss)
    }

    override suspend fun update(pss: PSS)
    {
        Log.d(MainApplication.logTag, "updating pss with id: ${pss.id}")
        pss.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(pss)
    }

    override suspend fun getAll(): List<PSS>
    {
        Log.d(MainApplication.logTag, "querying all pss")
        return dao.getAllPSS()
    }

    override suspend fun get(id: Long): PSS
    {
        Log.d(MainApplication.logTag, "querying pss with id: $id")
        return dao.getPSS(id)
    }
}