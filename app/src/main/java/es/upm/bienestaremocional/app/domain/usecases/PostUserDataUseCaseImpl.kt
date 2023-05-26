package es.upm.bienestaremocional.app.domain.usecases

import android.util.Log
import es.upm.bienestaremocional.app.data.database.entity.LastUpload
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
import es.upm.bienestaremocional.app.domain.repository.LastUploadRepository
import es.upm.bienestaremocional.app.domain.repository.remote.RemoteOperationResult
import es.upm.bienestaremocional.app.domain.repository.remote.RemoteRepository
import java.time.Instant
import java.time.ZonedDateTime

class PostUserDataUseCaseImpl(
    private val logTag: String,
    private val appInfo: AppInfo,
    private val remoteRepository: RemoteRepository,
    private val lastUploadRepository: LastUploadRepository,
    private val distance: Distance,
    private val elevationGained: ElevationGained,
    private val exerciseSession: ExerciseSession,
    private val floorsClimbed: FloorsClimbed,
    private val heartRate: HeartRate,
    private val sleep: Sleep,
    private val steps: Steps,
    private val totalCaloriesBurned: TotalCaloriesBurned,
    private val weight: Weight
) : PostUserDataUseCase
{
    override suspend fun shouldExecute(): Boolean
    {
        return distance.readPermissionsCheck() ||
                elevationGained.readPermissionsCheck() ||
                exerciseSession.readPermissionsCheck() ||
                floorsClimbed.readPermissionsCheck() ||
                heartRate.readPermissionsCheck() ||
                sleep.readPermissionsCheck() ||
                steps.readPermissionsCheck() ||
                totalCaloriesBurned.readPermissionsCheck() ||
                weight.readPermissionsCheck()
    }

    private suspend fun prepareUserData() : UserDataRequest
    {
        var distanceData: List<DistanceSender>? = null
        var elevationGainedData: List<ElevationGainedSender>? = null
        var exerciseSessionData: List<ExerciseSessionSender>? = null
        var floorsClimbedData: List<FloorsClimbedSender>? = null
        var heartRateData: List<HeartRateSender>? = null
        var sleepData: List<SleepSender>? = null
        var stepsData: List<StepsSender>? = null
        var totalCaloriesBurnedData: List<TotalCaloriesBurnedSender>? = null
        var weightData: List<WeightSender>? = null

        Log.d(logTag, "prepareUserData")

        // Read and convert data if we have permissions
        val now = ZonedDateTime.now().toInstant()

        if (distance.readPermissionsCheck())
        {
            val lastUpload = lastUploadRepository.get(LastUpload.Type.Distance)
            val from = lastUpload?.let { Instant.ofEpochSecond(it.timestamp + 1) }
            distanceData = (from?.let { distance.readSource(it, now) } ?: distance.readSource())
                .map { it.toSender() }
        }

        if (elevationGained.readPermissionsCheck())
        {
            val lastUpload = lastUploadRepository.get(LastUpload.Type.ElevationGained)
            val from = lastUpload?.let { Instant.ofEpochSecond(it.timestamp + 1) }
            elevationGainedData = (
                    from?.let { elevationGained.readSource(it, now) }
                        ?: elevationGained.readSource()
                    )
                .map { it.toSender() }
        }

        if (exerciseSession.readPermissionsCheck())
        {
            val lastUpload = lastUploadRepository.get(LastUpload.Type.ExerciseSession)
            val from = lastUpload?.let { Instant.ofEpochSecond(it.timestamp + 1) }
            exerciseSessionData = (
                    from?.let { exerciseSession.readSource(it, now) }
                        ?: exerciseSession.readSource()
                    )
                .map { it.toSender() }
        }

        if (floorsClimbed.readPermissionsCheck())
        {
            val lastUpload = lastUploadRepository.get(LastUpload.Type.FloorsClimbed)
            val from = lastUpload?.let { Instant.ofEpochSecond(it.timestamp + 1) }
            floorsClimbedData = (
                    from?.let { floorsClimbed.readSource(it, now) }
                        ?: floorsClimbed.readSource()
                    )
                .map { it.toSender() }
        }

        if (heartRate.readPermissionsCheck())
        {
            val lastUpload = lastUploadRepository.get(LastUpload.Type.HeartRate)
            val from = lastUpload?.let { Instant.ofEpochSecond(it.timestamp + 1) }
            heartRateData = (
                    from?.let { heartRate.readSource(it, now) }
                        ?: heartRate.readSource()
                    )
                .map { it.toSender() }
        }

        if (sleep.readPermissionsCheck())
        {
            val lastUpload = lastUploadRepository.get(LastUpload.Type.Sleep)
            val from = lastUpload?.let { Instant.ofEpochSecond(it.timestamp + 1) }
            sleepData = (from?.let { sleep.readSource(it, now) } ?: sleep.readSource())
                .map { it.toSender() }
        }

        if (steps.readPermissionsCheck())
        {
            val lastUpload = lastUploadRepository.get(LastUpload.Type.Steps)
            val from = lastUpload?.let { Instant.ofEpochSecond(it.timestamp + 1) }
            stepsData = (from?.let { steps.readSource(it, now) } ?: steps.readSource())
                .map { it.toSender() }
        }

        if (totalCaloriesBurned.readPermissionsCheck())
        {
            val lastUpload = lastUploadRepository.get(LastUpload.Type.TotalCaloriesBurned)
            val from = lastUpload?.let { Instant.ofEpochSecond(it.timestamp + 1) }
            totalCaloriesBurnedData = (
                    from?.let { totalCaloriesBurned.readSource(it, now) }
                        ?: totalCaloriesBurned.readSource()
                    )
                .map { it.toSender() }
        }

        if (weight.readPermissionsCheck())
        {
            val lastUpload = lastUploadRepository.get(LastUpload.Type.Weight)
            val from = lastUpload?.let { Instant.ofEpochSecond(it.timestamp + 1) }
            weightData = (from?.let { weight.readSource(it, now) } ?: weight.readSource())
                .map { it.toSender() }
        }

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

        return UserDataRequest(
            userId = appInfo.getUserID(),
            data = userData
        )
    }

    private suspend fun processSuccessfulRequest(response : UserDataResponse)
    {
        // If we have a valid response from the server, update timestamps on database
        response.timestamps?.let { timestamps ->
            Log.d(logTag, "updating timestamps")

            timestamps.distance?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.Distance,
                        timestamp = it
                    )
                )
            }

            timestamps.elevationGained?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.ElevationGained,
                        timestamp = it
                    )
                )
            }

            timestamps.exerciseSession?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.ExerciseSession,
                        timestamp = it
                    )
                )
            }

            timestamps.floorsClimbed?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.FloorsClimbed,
                        timestamp = it
                    )
                )
            }

            timestamps.heartRate?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.HeartRate,
                        timestamp = it
                    )
                )
            }

            timestamps.sleep?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.Sleep,
                        timestamp = it
                    )
                )
            }

            timestamps.steps?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.Steps,
                        timestamp = it
                    )
                )
            }

            timestamps.totalCaloriesBurned?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.TotalCaloriesBurned,
                        timestamp = it
                    )
                )
            }

            timestamps.weight?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.Weight,
                        timestamp = it
                    )
                )
            }
        }
    }

    override suspend fun execute() : RemoteOperationResult
    {
        val data = prepareUserData()
        val response = remoteRepository.postUserData(data)
        val result = when(response.code)
        {
            in 200..299 -> RemoteOperationResult.Success
            in 500..599 -> RemoteOperationResult.ServerFailure
            else -> RemoteOperationResult.Failure
        }
        if (result == RemoteOperationResult.Success)
            processSuccessfulRequest(response)
        return result
    }
}