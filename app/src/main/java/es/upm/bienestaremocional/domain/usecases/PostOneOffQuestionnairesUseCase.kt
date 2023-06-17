package es.upm.bienestaremocional.domain.usecases

import es.upm.bienestaremocional.domain.repository.remote.RemoteOperationResult


interface PostOneOffQuestionnairesUseCase {
    suspend fun execute() : RemoteOperationResult
}