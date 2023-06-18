package es.upm.bienestaremocional.domain.usecases

import es.upm.bienestaremocional.domain.repository.remote.RemoteOperationResult


interface PostDailyQuestionnairesUseCase {
    suspend fun execute(): RemoteOperationResult
}