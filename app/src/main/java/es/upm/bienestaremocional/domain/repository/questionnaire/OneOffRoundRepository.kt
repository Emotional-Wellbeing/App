package es.upm.bienestaremocional.domain.repository.questionnaire

import es.upm.bienestaremocional.data.database.entity.round.OneOffRound

/**
 * Repository to interact with [OneOffRound] entity.
 * Delete operation is not present by design.
 */
interface OneOffRoundRepository
{
    suspend fun insert(oneOffRound: OneOffRound) : Long
    suspend fun update(oneOffRound: OneOffRound)
    suspend fun getAll(): List<OneOffRound>
    suspend fun get(id: Long): OneOffRound?
}