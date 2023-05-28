package es.upm.bienestaremocional.app.data.remote

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
    suspend fun postUserData(@Body data: UserData): Response<Unit>

    @POST("/bg_data")
    suspend fun postBackgroundData(@Body message: String): Response<Unit>

}