package es.upm.bienestaremocional.data.remote

import es.upm.bienestaremocional.data.remote.community.CommunityResponse
import es.upm.bienestaremocional.data.remote.questionnaire.daily.DailyQuestionnairesRequest
import es.upm.bienestaremocional.data.remote.questionnaire.daily.DailyQuestionnairesResponse
import es.upm.bienestaremocional.data.remote.questionnaire.oneoff.OneOffQuestionnairesRequest
import es.upm.bienestaremocional.data.remote.questionnaire.oneoff.OneOffQuestionnairesResponse
import es.upm.bienestaremocional.data.remote.userdata.UserDataRequest
import es.upm.bienestaremocional.data.remote.userdata.UserDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Operations established on the API
 */
interface RemoteAPI {
    @GET("/community")
    suspend fun getCommunityData(): Response<CommunityResponse.Data>

    @POST("/user_data")
    suspend fun postUserData(@Body data: UserDataRequest): Response<UserDataResponse.Timestamps>

    @POST("/daily_questionnaires")
    suspend fun postDailyQuestionnaires(@Body data: DailyQuestionnairesRequest):
            Response<DailyQuestionnairesResponse.Timestamps>

    @POST("/one_off_questionnaires")
    suspend fun postOneOffQuestionnaires(@Body data: OneOffQuestionnairesRequest):
            Response<OneOffQuestionnairesResponse.Timestamps>
    @POST("/bg_data")
    suspend fun postBackgroundData(@Body message: String): Response<Unit>
}