package es.upm.bienestaremocional.domain.repository.questionnaire

import es.upm.bienestaremocional.data.database.entity.round.DailyRoundFull

/**
 * Repository to interact with [DailyRoundFull] (only queries)
 */
interface DailyRoundFullRepository {
    suspend fun get(id: Long): DailyRoundFull?
    suspend fun getAll(): List<DailyRoundFull>
    suspend fun getAllUncompleted(): List<DailyRoundFull>
}