package es.upm.bienestaremocional.domain.usecases

import es.upm.bienestaremocional.domain.repository.remote.RemoteOperationResult


interface PostUserDataUseCase {
    suspend fun shouldExecute(): Boolean
    suspend fun execute(): RemoteOperationResult
}