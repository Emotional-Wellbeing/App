package es.upm.bienestaremocional.data.remote

import es.upm.bienestaremocional.data.remote.userdata.DailyQuestionnairesRequest
import es.upm.bienestaremocional.data.remote.userdata.DailyQuestionnairesResponse
import es.upm.bienestaremocional.data.remote.userdata.OneOffQuestionnairesRequest
import es.upm.bienestaremocional.data.remote.userdata.OneOffQuestionnairesResponse
import es.upm.bienestaremocional.data.remote.userdata.UserDataRequest
import es.upm.bienestaremocional.data.remote.userdata.UserDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Operations established on the API
 */
interface RemoteAPI
{
    @GET("/score")
    suspend fun getScore() : Response<Int>
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