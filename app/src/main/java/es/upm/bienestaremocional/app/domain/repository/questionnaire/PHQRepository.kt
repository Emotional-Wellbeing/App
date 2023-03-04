package es.upm.bienestaremocional.app.domain.repository.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.PHQ

/**
 * Repository to interact with [PHQ] entity.
 * Delete operation is not present by design.
 */
interface PHQRepository 
{
    suspend fun insert(phq: PHQ) : Long
    suspend fun update(phq: PHQ)
    suspend fun getAll(): List<PHQ>
    suspend fun get(id: Long): PHQ?
}