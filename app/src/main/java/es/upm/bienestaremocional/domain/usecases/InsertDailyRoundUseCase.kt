package es.upm.bienestaremocional.domain.usecases

import es.upm.bienestaremocional.data.database.entity.round.DailyRound

interface InsertDailyRoundUseCase {
    suspend fun insertDailyMorningRound(): DailyRound
    suspend fun insertDailyNightRound(): DailyRound
}