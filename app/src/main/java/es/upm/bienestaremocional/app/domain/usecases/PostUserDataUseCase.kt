package es.upm.bienestaremocional.app.domain.usecases

import es.upm.bienestaremocional.app.domain.repository.remote.RemoteOperationResult


interface PostUserDataUseCase {
    suspend fun shouldExecute(): Boolean
    suspend fun execute() : RemoteOperationResult
}