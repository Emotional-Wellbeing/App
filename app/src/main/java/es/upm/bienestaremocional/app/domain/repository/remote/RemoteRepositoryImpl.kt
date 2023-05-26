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
import es.upm.bienestaremocional.app.data.info.AppInfo
import es.upm.bienestaremocional.app.data.remote.RemoteAPI
import es.upm.bienestaremocional.app.data.remote.senders.DistanceSender
import es.upm.bienestaremocional.app.data.remote.senders.DistanceSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.senders.ElevationGainedSender
import es.upm.bienestaremocional.app.data.remote.senders.ElevationGainedSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.senders.ExerciseSessionSender
import es.upm.bienestaremocional.app.data.remote.senders.ExerciseSessionSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.senders.FloorsClimbedSender
import es.upm.bienestaremocional.app.data.remote.senders.FloorsClimbedSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.senders.HeartRateSender
import es.upm.bienestaremocional.app.data.remote.senders.HeartRateSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.senders.SleepSender
import es.upm.bienestaremocional.app.data.remote.senders.SleepSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.senders.StepsSender
import es.upm.bienestaremocional.app.data.remote.senders.StepsSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.senders.TotalCaloriesBurnedSender
import es.upm.bienestaremocional.app.data.remote.senders.TotalCaloriesBurnedSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.senders.WeightSender
import es.upm.bienestaremocional.app.data.remote.senders.WeightSender.Companion.toSender
import es.upm.bienestaremocional.app.data.remote.userdata.UserDataRequest
import es.upm.bienestaremocional.app.data.remote.userdata.UserDataResponse

class RemoteRepositoryImpl(
    private val remoteAPI: RemoteAPI,
    private val appInfo: AppInfo,
    private val distance: Distance,
    private val elevationGained: ElevationGained,
    private val exerciseSession: ExerciseSession,
    private val floorsClimbed: FloorsClimbed,
    private val heartRate: HeartRate,
    private val sleep: Sleep,
    private val steps: Steps,
    private val totalCaloriesBurned: TotalCaloriesBurned,
    private val weight: Weight
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

    override suspend fun postUserData(): UserDataResponse {
        var distanceData: List<DistanceSender>? = null
        var elevationGainedData: List<ElevationGainedSender>? = null
        var exerciseSessionData: List<ExerciseSessionSender>? = null
        var floorsClimbedData: List<FloorsClimbedSender>? = null
        var heartRateData: List<HeartRateSender>? = null
        var sleepData: List<SleepSender>? = null
        var stepsData: List<StepsSender>? = null
        var totalCaloriesBurnedData: List<TotalCaloriesBurnedSender>? = null
        var weightData: List<WeightSender>? = null

        // Read and convert data if we have permissions

        if (distance.readPermissionsCheck()) {
            distanceData = distance.readSource().map { it.toSender() }
        }

        if (elevationGained.readPermissionsCheck()) {
            elevationGainedData = elevationGained.readSource().map { it.toSender() }
        }

        if (exerciseSession.readPermissionsCheck()) {
            exerciseSessionData = exerciseSession.readSource().map { it.toSender() }
        }

        if (floorsClimbed.readPermissionsCheck()) {
            floorsClimbedData = floorsClimbed.readSource().map { it.toSender() }
        }

        if (heartRate.readPermissionsCheck()) {
            heartRateData = heartRate.readSource().map { it.toSender() }
        }

        if (sleep.readPermissionsCheck()) {
            sleepData = sleep.readSource().map { it.toSender() }
        }

        if (steps.readPermissionsCheck()) {
            stepsData = steps.readSource().map { it.toSender() }
        }

        if (totalCaloriesBurned.readPermissionsCheck()) {
            totalCaloriesBurnedData = totalCaloriesBurned.readSource().map { it.toSender() }
        }

        if (weight.readPermissionsCheck()) {
            weightData = weight.readSource().map { it.toSender() }
        }

        // Send data

        val userData = UserDataRequest.Data(
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

        val userDataRequest = UserDataRequest(
            userId = appInfo.getUserID(),
            data = userData
        )

        val rawResponse = remoteAPI.postUserData(userDataRequest)
        return UserDataResponse(
            code = rawResponse.code(),
            timestamps = rawResponse.body()
        )
    }
}
