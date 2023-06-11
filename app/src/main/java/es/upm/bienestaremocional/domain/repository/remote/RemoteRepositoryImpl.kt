package es.upm.bienestaremocional.domain.repository.remote

import android.util.Log
import es.upm.bienestaremocional.data.remote.RemoteAPI
import es.upm.bienestaremocional.data.remote.userdata.UserDataRequest
import es.upm.bienestaremocional.data.remote.userdata.UserDataResponse

class RemoteRepositoryImpl(
    private val logTag: String,
    private val remoteAPI: RemoteAPI
): RemoteRepository
{
    override suspend fun getScore(): Int?
    {
        Log.d(logTag, "calling getScore")

        return try {
            val response = remoteAPI.getScore()
            if (response.isSuccessful)
                response.body()
            else
                null
        } catch (e:Exception) {
            null
        }
    }

    override suspend fun postUserData(userDataRequest: UserDataRequest): UserDataResponse?
    {
        Log.d(logTag, "posting data")

        var response : UserDataResponse? = null

        try {
            val rawResponse = remoteAPI.postUserData(userDataRequest)
            Log.d(logTag, "response received with " +
                    "code ${rawResponse.code()} and values ${rawResponse.body()}")
            response = UserDataResponse(
                code = rawResponse.code(),
                timestamps = rawResponse.body()
            )
        }
        catch (e: Exception)
        {
            Log.e(logTag, "response failed with exception $e")
        }
        return response
    }
}
