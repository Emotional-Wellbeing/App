package es.upm.bienestaremocional.app.domain.repository.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.PSS

/**
 * Repository to interact with [PSS] entity.
 * Delete operation is not present by design.
 */
interface PSSRepository : QuestionnaireRepository<PSS>
{
    override suspend fun insert(element: PSS) : Long
    override suspend fun update(element: PSS)
    override suspend fun getAll(): List<PSS>
    override suspend fun getAllFromLastSevenDays(): List<PSS>
    override suspend fun getAllCompleted(): List<PSS>
    override suspend fun get(id: Long): PSS?
}