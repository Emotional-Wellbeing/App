package es.upm.bienestaremocional.domain.repository.remote

import es.upm.bienestaremocional.data.remote.userdata.UserDataRequest
import es.upm.bienestaremocional.data.remote.userdata.UserDataResponse

/**
 * Repository to interact with the Remote API
 */
interface RemoteRepository
{
    suspend fun getScore(): Int?
    suspend fun postUserData(userDataRequest: UserDataRequest): UserDataResponse
}