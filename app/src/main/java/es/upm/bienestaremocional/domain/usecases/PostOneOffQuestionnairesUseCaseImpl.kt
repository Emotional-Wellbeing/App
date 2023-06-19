package es.upm.bienestaremocional.domain.usecases

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.entity.LastUpload
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffDepression
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffLoneliness
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffStress
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.remote.questionnaire.oneoff.OneOffQuestionnairesRequest
import es.upm.bienestaremocional.data.remote.questionnaire.oneoff.OneOffQuestionnairesResponse
import es.upm.bienestaremocional.domain.processing.secondToZonedDateTime
import es.upm.bienestaremocional.domain.repository.LastUploadRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffStressRepository
import es.upm.bienestaremocional.domain.repository.remote.RemoteOperationResult
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepository
import java.time.ZonedDateTime

class PostOneOffQuestionnairesUseCaseImpl(
    private val logTag: String,
    private val appInfo: AppInfo,
    private val remoteRepository: RemoteRepository,
    private val lastUploadRepository: LastUploadRepository,
    private val oneOffStressRepository: OneOffStressRepository,
    private val oneOffDepressionRepository: OneOffDepressionRepository,
    private val oneOffLonelinessRepository: OneOffLonelinessRepository,
) : PostOneOffQuestionnairesUseCase {
    private suspend fun prepareOneOffQuestionnairesData(): OneOffQuestionnairesRequest {
        lateinit var stressData: List<OneOffStress>
        lateinit var depressionData: List<OneOffDepression>
        lateinit var lonelinessData: List<OneOffLoneliness>

        Log.d(logTag, "prepareOneOffQuestionnairesData")

        val end: ZonedDateTime = ZonedDateTime.now()
        val defaultStart: ZonedDateTime = end.minusDays(30)

        var lastUpload = lastUploadRepository.get(LastUpload.Type.OneOffStress)
        var start: ZonedDateTime = lastUpload?.let {
            secondToZonedDateTime(it.timestamp + 1)
        } ?: defaultStart
        stressData = oneOffStressRepository.getAllFromRange(
            range = Range(start, end),
            onlyCompleted = true
        )

        lastUpload = lastUploadRepository.get(LastUpload.Type.OneOffDepression)
        start = lastUpload?.let {
            secondToZonedDateTime(it.timestamp + 1)
        } ?: defaultStart
        depressionData = oneOffDepressionRepository.getAllFromRange(
            range = Range(start, end),
            onlyCompleted = true
        )

        lastUpload = lastUploadRepository.get(LastUpload.Type.OneOffLoneliness)
        start = lastUpload?.let {
            secondToZonedDateTime(it.timestamp + 1)
        } ?: defaultStart
        lonelinessData = oneOffLonelinessRepository.getAllFromRange(
            range = Range(start, end),
            onlyCompleted = true
        )


        val userData = OneOffQuestionnairesRequest.Data(
            stress = stressData,
            depression = depressionData,
            loneliness = lonelinessData,
        )

        return OneOffQuestionnairesRequest(
            userId = appInfo.getUserID(),
            data = userData
        )
    }

    private suspend fun processSuccessfulRequest(response: OneOffQuestionnairesResponse) {
        // If we have a valid response from the server, update timestamps on database
        response.timestamps?.let { timestamps ->
            Log.d(logTag, "updating timestamps")

            timestamps.stress?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.OneOffStress,
                        timestamp = it
                    )
                )
            }

            timestamps.depression?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.OneOffDepression,
                        timestamp = it
                    )
                )
            }

            timestamps.loneliness?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.OneOffLoneliness,
                        timestamp = it
                    )
                )
            }
        }
    }

    override suspend fun execute(): RemoteOperationResult {
        val data = prepareOneOffQuestionnairesData()
        val response = remoteRepository.postOneOffQuestionnaires(data)

        // If response is null, the result is failure
        var result = RemoteOperationResult.Failure

        response?.let {
            result = when (response.code) {
                in 200..299 -> RemoteOperationResult.Success
                in 500..599 -> RemoteOperationResult.ServerFailure
                else -> RemoteOperationResult.Failure
            }
            if (result == RemoteOperationResult.Success)
                processSuccessfulRequest(response)
        }

        return result
    }
}