package es.upm.bienestaremocional.domain.usecases

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.data.database.entity.LastUpload
import es.upm.bienestaremocional.data.database.entity.daily.DailyDepression
import es.upm.bienestaremocional.data.database.entity.daily.DailyLoneliness
import es.upm.bienestaremocional.data.database.entity.daily.DailyStress
import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import es.upm.bienestaremocional.data.database.entity.daily.DailySymptoms
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.remote.questionnaire.daily.DailyQuestionnairesRequest
import es.upm.bienestaremocional.data.remote.questionnaire.daily.DailyQuestionnairesResponse
import es.upm.bienestaremocional.domain.processing.secondToZonedDateTime
import es.upm.bienestaremocional.domain.repository.LastUploadRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyStressRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySuicideRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySymptomsRepository
import es.upm.bienestaremocional.domain.repository.remote.RemoteOperationResult
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepository
import java.time.ZonedDateTime

class PostDailyQuestionnairesUseCaseImpl(
    private val logTag: String,
    private val appInfo: AppInfo,
    private val remoteRepository: RemoteRepository,
    private val lastUploadRepository: LastUploadRepository,
    private val dailyStressRepository: DailyStressRepository,
    private val dailyDepressionRepository: DailyDepressionRepository,
    private val dailyLonelinessRepository: DailyLonelinessRepository,
    private val dailySuicideRepository: DailySuicideRepository,
    private val dailySymptomsRepository: DailySymptomsRepository,
) : PostDailyQuestionnairesUseCase {
    private suspend fun prepareDailyQuestionnairesData(): DailyQuestionnairesRequest {
        lateinit var stressData: List<DailyStress>
        lateinit var depressionData: List<DailyDepression>
        lateinit var lonelinessData: List<DailyLoneliness>
        lateinit var suicideData: List<DailySuicide>
        lateinit var symptomsData: List<DailySymptoms>

        Log.d(logTag, "prepareDailyQuestionnairesData")

        val end: ZonedDateTime = ZonedDateTime.now()
        val defaultStart: ZonedDateTime = end.minusDays(30)


        var lastUpload = lastUploadRepository.get(LastUpload.Type.DailyStress)
        var start: ZonedDateTime = lastUpload?.let {
            secondToZonedDateTime(it.timestamp + 1)
        } ?: defaultStart
        stressData = dailyStressRepository.getAllFromRange(
            range = Range(start, end),
            onlyCompleted = true
        )

        lastUpload = lastUploadRepository.get(LastUpload.Type.DailyDepression)
        start = lastUpload?.let {
            secondToZonedDateTime(it.timestamp + 1)
        } ?: defaultStart
        depressionData = dailyDepressionRepository.getAllFromRange(
            range = Range(start, end),
            onlyCompleted = true
        )

        lastUpload = lastUploadRepository.get(LastUpload.Type.DailyLoneliness)
        start = lastUpload?.let {
            secondToZonedDateTime(it.timestamp + 1)
        } ?: defaultStart
        lonelinessData = dailyLonelinessRepository.getAllFromRange(
            range = Range(start, end),
            onlyCompleted = true
        )

        lastUpload = lastUploadRepository.get(LastUpload.Type.DailySuicide)
        start = lastUpload?.let {
            secondToZonedDateTime(it.timestamp + 1)
        } ?: defaultStart
        suicideData = dailySuicideRepository.getAllFromRange(
            range = Range(start, end),
            onlyCompleted = true
        )

        lastUpload = lastUploadRepository.get(LastUpload.Type.DailySymptoms)
        start = lastUpload?.let {
            secondToZonedDateTime(it.timestamp + 1)
        } ?: defaultStart
        symptomsData = dailySymptomsRepository.getAllFromRange(
            range = Range(start, end),
            onlyCompleted = true
        )


        val userData = DailyQuestionnairesRequest.Data(
            stress = stressData,
            depression = depressionData,
            loneliness = lonelinessData,
            suicide = suicideData,
            symptoms = symptomsData,
        )

        return DailyQuestionnairesRequest(
            userId = appInfo.getUserID(),
            data = userData
        )
    }

    private suspend fun processSuccessfulRequest(response: DailyQuestionnairesResponse) {
        // If we have a valid response from the server, update timestamps on database
        response.timestamps?.let { timestamps ->
            Log.d(logTag, "updating timestamps")

            timestamps.stress?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.DailyStress,
                        timestamp = it
                    )
                )
            }

            timestamps.depression?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.DailyDepression,
                        timestamp = it
                    )
                )
            }

            timestamps.loneliness?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.DailyLoneliness,
                        timestamp = it
                    )
                )
            }

            timestamps.suicide?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.DailySuicide,
                        timestamp = it
                    )
                )
            }

            timestamps.symptoms?.let {
                lastUploadRepository.update(
                    LastUpload(
                        type = LastUpload.Type.DailySymptoms,
                        timestamp = it
                    )
                )
            }
        }
    }

    override suspend fun execute(): RemoteOperationResult {
        val data = prepareDailyQuestionnairesData()
        val response = remoteRepository.postDailyQuestionnaires(data)

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