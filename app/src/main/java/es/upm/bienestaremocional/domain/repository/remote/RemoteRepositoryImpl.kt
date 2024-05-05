package es.upm.bienestaremocional.domain.repository.remote

import android.util.Log
import es.upm.bienestaremocional.data.remote.RemoteAPI
import es.upm.bienestaremocional.data.remote.community.CommunityResponse
import es.upm.bienestaremocional.data.remote.questionnaire.daily.DailyQuestionnairesRequest
import es.upm.bienestaremocional.data.remote.questionnaire.daily.DailyQuestionnairesResponse
import es.upm.bienestaremocional.data.remote.questionnaire.oneoff.OneOffQuestionnairesRequest
import es.upm.bienestaremocional.data.remote.questionnaire.oneoff.OneOffQuestionnairesResponse
import es.upm.bienestaremocional.data.remote.userdata.UserDataRequest
import es.upm.bienestaremocional.data.remote.userdata.UserDataResponse

class RemoteRepositoryImpl(
    private val logTag: String,
    private val remoteAPI: RemoteAPI
) : RemoteRepository {
    override suspend fun getCommunity(): CommunityResponse? {
        Log.d(logTag, "get community")

        var response: CommunityResponse? = null

        try {
            val rawResponse = remoteAPI.getCommunityData()
            Log.d(
                logTag, "response received with " +
                        "code ${rawResponse.code()} and values ${rawResponse.body()}"
            )
            response = CommunityResponse(
                code = rawResponse.code(),
                data = rawResponse.body()
            )
        }
        catch (e: Exception) {
            Log.e(logTag, "response failed with exception $e")
        }
        return response
    }

    override suspend fun postUserData(userDataRequest: UserDataRequest): UserDataResponse? {
        Log.d(logTag, "posting user data")

        var response: UserDataResponse? = null

        try {
            val rawResponse = remoteAPI.postUserData(userDataRequest)
            Log.d(
                logTag, "response received with " +
                        "code ${rawResponse.code()} and values ${rawResponse.body()}"
            )
            response = UserDataResponse(
                code = rawResponse.code(),
                timestamps = rawResponse.body()
            )
        }
        catch (e: Exception) {
            Log.e(logTag, "response failed with exception ${Log.getStackTraceString(e)}")
        }
        return response
    }

    override suspend fun postDailyQuestionnaires(
        dailyQuestionnairesRequest: DailyQuestionnairesRequest
    ): DailyQuestionnairesResponse? {
        Log.d(logTag, "posting daily questionnaires data")

        var response: DailyQuestionnairesResponse? = null

        try {
            val rawResponse = remoteAPI.postDailyQuestionnaires(dailyQuestionnairesRequest)
            Log.d(
                logTag, "response received with " +
                        "code ${rawResponse.code()} and values ${rawResponse.body()}"
            )
            response = DailyQuestionnairesResponse(
                code = rawResponse.code(),
                timestamps = rawResponse.body()
            )
        }
        catch (e: Exception) {
            Log.e(logTag, "response failed with exception ${Log.getStackTraceString(e)}")
        }
        return response
    }

    override suspend fun postOneOffQuestionnaires(
        oneOffQuestionnairesRequest: OneOffQuestionnairesRequest
    ): OneOffQuestionnairesResponse? {
        Log.d(logTag, "posting one off questionnaires data")

        var response: OneOffQuestionnairesResponse? = null

        try {
            val rawResponse = remoteAPI.postOneOffQuestionnaires(oneOffQuestionnairesRequest)
            Log.d(
                logTag, "response received with " +
                        "code ${rawResponse.code()} and values ${rawResponse.body()}"
            )
            response = OneOffQuestionnairesResponse(
                code = rawResponse.code(),
                timestamps = rawResponse.body()
            )
        }
        catch (e: Exception) {
            Log.e(logTag, "response failed with exception  ${Log.getStackTraceString(e)}")
        }
        return response
    }

    override suspend fun postBackgroundData(message: String): Boolean {
        try {
            remoteAPI.postBackgroundData(message)

        }
        catch (e: Exception) {
            Log.e(logTag, "response failed with exception $e")
            return false
        }
        return true
    }
}
