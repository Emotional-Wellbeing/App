package es.upm.bienestaremocional.domain.repository.questionnaire

import es.upm.bienestaremocional.data.database.entity.round.DailyRound

/**
 * Repository to interact with [DailyRound] (only queries)
 */
interface DailyRoundRepository {
    suspend fun insert(dailyRound: DailyRound): Long
    suspend fun update(dailyRound: DailyRound)
    suspend fun get(id: Long): DailyRound?
    suspend fun getAll(): List<DailyRound>
}