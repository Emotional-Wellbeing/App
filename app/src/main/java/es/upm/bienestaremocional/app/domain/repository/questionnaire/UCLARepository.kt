package es.upm.bienestaremocional.app.domain.repository.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.UCLA

/**
 * Repository to interact with [UCLA] entity.
 * Delete operation is not present by design.
 */
interface UCLARepository: QuestionnaireRepository<UCLA>
{
    override suspend fun insert(element: UCLA) : Long
    override suspend fun update(element: UCLA)
    override suspend fun getAll(): List<UCLA>
    override suspend fun get(id: Long): UCLA?
}