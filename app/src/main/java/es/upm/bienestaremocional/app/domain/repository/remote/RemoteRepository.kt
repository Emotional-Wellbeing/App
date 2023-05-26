package es.upm.bienestaremocional.app.domain.repository.remote

import es.upm.bienestaremocional.app.data.remote.userdata.UserDataResponse

/**
 * Repository to interact with the Remote API
 */
interface RemoteRepository
{
    suspend fun permissionsForAnySource() : Boolean
    suspend fun getScore(): Int?

    suspend fun postUserData(): UserDataResponse
}