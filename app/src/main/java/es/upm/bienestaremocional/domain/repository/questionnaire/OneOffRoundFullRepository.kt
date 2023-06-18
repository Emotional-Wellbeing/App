package es.upm.bienestaremocional.domain.repository.questionnaire

import es.upm.bienestaremocional.data.database.entity.round.OneOffRoundFull

/**
 * Repository to interact with [OneOffRoundFull] (only queries)
 */
interface OneOffRoundFullRepository {
    suspend fun get(id: Long): OneOffRoundFull?
    suspend fun getAll(): List<OneOffRoundFull>
    suspend fun getAllUncompleted(): List<OneOffRoundFull>
}