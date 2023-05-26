package es.upm.bienestaremocional.app.data.remote.userdata

import es.upm.bienestaremocional.app.data.remote.senders.DistanceSender
import es.upm.bienestaremocional.app.data.remote.senders.ElevationGainedSender
import es.upm.bienestaremocional.app.data.remote.senders.ExerciseSessionSender
import es.upm.bienestaremocional.app.data.remote.senders.FloorsClimbedSender
import es.upm.bienestaremocional.app.data.remote.senders.HeartRateSender
import es.upm.bienestaremocional.app.data.remote.senders.SleepSender
import es.upm.bienestaremocional.app.data.remote.senders.StepsSender
import es.upm.bienestaremocional.app.data.remote.senders.TotalCaloriesBurnedSender
import es.upm.bienestaremocional.app.data.remote.senders.WeightSender

data class UserDataRequest(
    val userId : String,
    val data: Data
)
{
    data class Data(
        val distance : List<DistanceSender>?,
        val elevationGained : List<ElevationGainedSender>?,
        val exerciseSession : List<ExerciseSessionSender>?,
        val floorsClimbed : List<FloorsClimbedSender>?,
        val heartRate : List<HeartRateSender>?,
        val sleep : List<SleepSender>?,
        val steps : List<StepsSender>?,
        val totalCaloriesBurned : List<TotalCaloriesBurnedSender>?,
        val weight: List<WeightSender>?,
    )

}