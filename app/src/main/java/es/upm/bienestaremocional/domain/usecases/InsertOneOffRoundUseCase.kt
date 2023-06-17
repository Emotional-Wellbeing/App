package es.upm.bienestaremocional.domain.usecases

import es.upm.bienestaremocional.data.database.entity.round.OneOffRound

interface InsertOneOffRoundUseCase
{
    suspend fun insertOneOffRound(): OneOffRound
}