package es.upm.bienestaremocional.app.data.remote.userdata

data class UserDataResponse(
    val code : Int,
    val timestamps : Timestamps?
)
{
    data class Timestamps(
        val distance : Long?,
        val elevationGained : Long?,
        val exerciseSession : Long?,
        val floorsClimbed : Long?,
        val heartRate : Long?,
        val sleep : Long?,
        val steps : Long?,
        val totalCaloriesBurned : Long?,
        val weight: Long?
    )
}
