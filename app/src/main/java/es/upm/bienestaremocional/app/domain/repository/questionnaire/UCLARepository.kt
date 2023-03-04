package es.upm.bienestaremocional.app.domain.repository.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.UCLA

/**
 * Repository to interact with [UCLA] entity.
 * Delete operation is not present by design.
 */
interface UCLARepository
{
    suspend fun insert(ucla: UCLA) : Long
    suspend fun update(ucla: UCLA)
    suspend fun getAll(): List<UCLA>
    suspend fun get(id: Long): UCLA?
}