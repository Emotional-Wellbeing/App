package es.upm.bienestaremocional.data.remote.community

import com.google.gson.annotations.SerializedName


data class CommunityResponse(
    val code : Int,
    val data : Data?
)
{
    data class Data(
        @SerializedName("current_week") val currentWeek : List<Row>,
        @SerializedName("last_seven_days") val lastSevenDays : Row,
        val yesterday : Row
    )
    {
        data class Row(
            val stress : Double?,
            val depression : Double?,
            val loneliness : Double?,
        )
    }
}