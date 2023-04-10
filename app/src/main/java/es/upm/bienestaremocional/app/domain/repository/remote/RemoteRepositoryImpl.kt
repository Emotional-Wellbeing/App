package es.upm.bienestaremocional.app.domain.repository.remote

import es.upm.bienestaremocional.app.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.app.data.remote.RemoteAPI

class RemoteRepositoryImpl(
    private val remoteAPI: RemoteAPI,
    private val sleep: Sleep,
): RemoteRepository
{
    override suspend fun getScore(): Int?
    {
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

    override suspend fun postUserData(): Boolean
    {
        val requestData = hashMapOf<String,Any>()

        if (sleep.readPermissionsCheck())
        {
            requestData["sleep"] = sleep.readSource().map { sample -> sleep.jsonify(sample) }
        }

        val response = remoteAPI.postUserData(requestData)
        return response.isSuccessful
    }
}
