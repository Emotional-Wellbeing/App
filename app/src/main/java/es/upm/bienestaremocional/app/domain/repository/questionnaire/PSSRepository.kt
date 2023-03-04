package es.upm.bienestaremocional.app.domain.repository.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.PSS

/**
 * Repository to interact with [PSS] entity.
 * Delete operation is not present by design.
 */
interface PSSRepository
{
    suspend fun insert(pss: PSS) : Long
    suspend fun update(pss: PSS)
    suspend fun getAll(): List<PSS>
    suspend fun get(id: Long): PSS?
}