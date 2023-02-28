package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Log
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.PHQ
import javax.inject.Inject

class PHQRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
): PHQRepository
{
    override suspend fun insert(phq: PHQ): Long
    {
        Log.d(logTag, "inserting new phq")
        return dao.insert(phq)
    }

    override suspend fun update(phq: PHQ)
    {
        Log.d(logTag, "updating phq with id: ${phq.id}")
        phq.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(phq)
    }

    override suspend fun getAll(): List<PHQ>
    {
        Log.d(logTag, "querying all phq")
        return dao.getAllPHQ()
    }

    override suspend fun get(id: Long): PHQ
    {
        Log.d(logTag, "querying phq with id: $id")
        return dao.getPHQ(id)
    }
}