package es.upm.bienestaremocional.app.data.remote


data class UserData(
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
