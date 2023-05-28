package es.upm.bienestaremocional.app.domain.repository.remote

import es.upm.bienestaremocional.app.data.healthconnect.sources.Distance
import es.upm.bienestaremocional.app.data.healthconnect.sources.ElevationGained
import es.upm.bienestaremocional.app.data.healthconnect.sources.ExerciseSession
import es.upm.bienestaremocional.app.data.healthconnect.sources.FloorsClimbed
import es.upm.bienestaremocional.app.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.app.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.app.data.healthconnect.sources.Steps
import es.upm.bienestaremocional.app.data.healthconnect.sources.TotalCaloriesBurned
import es.upm.bienestaremocional.app.data.healthconnect.sources.Weight
import es.upm.bienestaremocional.app.data.remote.DistanceSender
import es.upm.bienestaremocional.app.data.remote.DistanceSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.ElevationGainedSender
import es.upm.bienestaremocional.app.data.remote.ElevationGainedSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.ExerciseSessionSender
import es.upm.bienestaremocional.app.data.remote.ExerciseSessionSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.FloorsClimbedSender
import es.upm.bienestaremocional.app.data.remote.FloorsClimbedSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.HeartRateSender
import es.upm.bienestaremocional.app.data.remote.HeartRateSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.RemoteAPI
import es.upm.bienestaremocional.app.data.remote.SleepSender
import es.upm.bienestaremocional.app.data.remote.SleepSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.StepsSender
import es.upm.bienestaremocional.app.data.remote.StepsSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.TotalCaloriesBurnedSender
import es.upm.bienestaremocional.app.data.remote.TotalCaloriesBurnedSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.UserData
import es.upm.bienestaremocional.app.data.remote.WeightSender
import es.upm.bienestaremocional.app.data.remote.WeightSender.Companion.toSender

class RemoteRepositoryImpl(
    private val remoteAPI: RemoteAPI,
    private val distance: Distance?,
    private val elevationGained: ElevationGained?,
    private val exerciseSession: ExerciseSession?,
    private val floorsClimbed: FloorsClimbed?,
    private val heartRate: HeartRate?,
    private val sleep: Sleep?,
    private val steps: Steps?,
    private val totalCaloriesBurned: TotalCaloriesBurned?,
    private val weight: Weight?
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
        var distanceData : List<DistanceSender>? = null
        var elevationGainedData : List<ElevationGainedSender>? = null
        var exerciseSessionData : List<ExerciseSessionSender>? = null
        var floorsClimbedData : List<FloorsClimbedSender>? = null
        var heartRateData : List<HeartRateSender>? = null
        var sleepData : List<SleepSender>? = null
        var stepsData : List<StepsSender>? = null
        var totalCaloriesBurnedData : List<TotalCaloriesBurnedSender>? = null
        var weightData : List<WeightSender>? = null


        if (distance?.readPermissionsCheck() == true)
        {
            distanceData = distance.readSource().map { it.toSender() }
        }

        if (elevationGained?.readPermissionsCheck() == true)
        {
            elevationGainedData = elevationGained.readSource().map { it.toSender() }
        }

        if (exerciseSession?.readPermissionsCheck() == true)
        {
            exerciseSessionData = exerciseSession.readSource().map { it.toSender() }
        }

        if (floorsClimbed?.readPermissionsCheck() == true)
        {
            floorsClimbedData = floorsClimbed.readSource().map { it.toSender() }
        }

        if (heartRate?.readPermissionsCheck() == true)
        {
            heartRateData = heartRate.readSource().map { it.toSender() }
        }

        if (sleep?.readPermissionsCheck() == true)
        {
            sleepData = sleep.readSource().map { it.toSender() }
        }

        if (steps?.readPermissionsCheck() == true)
        {
            stepsData = steps.readSource().map { it.toSender() }
        }

        if (totalCaloriesBurned?.readPermissionsCheck() == true)
        {
            totalCaloriesBurnedData = totalCaloriesBurned.readSource().map { it.toSender() }
        }

        if (weight?.readPermissionsCheck() == true)
        {
            weightData = weight.readSource().map { it.toSender() }
        }

        val userData = UserData(
            distance = distanceData,
            elevationGained = elevationGainedData,
            exerciseSession = exerciseSessionData,
            floorsClimbed = floorsClimbedData,
            heartRate = heartRateData,
            sleep = sleepData,
            steps = stepsData,
            totalCaloriesBurned = totalCaloriesBurnedData,
            weight = weightData,
        )

        val response = remoteAPI.postUserData(userData)
        return response.isSuccessful
    }

     override suspend fun postBackgroundData(message:String): Boolean {
         val response = remoteAPI.postBackgroundData(message)
         return response.isSuccessful
    }

}
