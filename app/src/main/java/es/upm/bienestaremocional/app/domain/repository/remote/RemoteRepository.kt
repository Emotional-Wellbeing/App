package es.upm.bienestaremocional.app.domain.repository.remote

/**
 * Repository to interact with the Remote API
 */
interface RemoteRepository
{
    suspend fun getScore(): Int?

    suspend fun postUserData(): Boolean
}