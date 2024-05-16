package es.upm.bienestaremocional.domain.repository.remote

import es.upm.bienestaremocional.data.remote.community.CommunityResponse
import es.upm.bienestaremocional.data.remote.questionnaire.daily.DailyQuestionnairesRequest
import es.upm.bienestaremocional.data.remote.questionnaire.daily.DailyQuestionnairesResponse
import es.upm.bienestaremocional.data.remote.questionnaire.oneoff.OneOffQuestionnairesRequest
import es.upm.bienestaremocional.data.remote.questionnaire.oneoff.OneOffQuestionnairesResponse
import es.upm.bienestaremocional.data.remote.userdata.UserDataRequest
import es.upm.bienestaremocional.data.remote.userdata.UserDataResponse

/**
 * Repository to interact with the Remote API
 */
interface RemoteRepository {
    suspend fun getCommunity(): CommunityResponse?
    suspend fun postUserData(userDataRequest: UserDataRequest): UserDataResponse?
    suspend fun postDailyQuestionnaires(dailyQuestionnairesRequest: DailyQuestionnairesRequest):
            DailyQuestionnairesResponse?

    suspend fun postOneOffQuestionnaires(oneOffQuestionnairesRequest: OneOffQuestionnairesRequest):
            OneOffQuestionnairesResponse?

    suspend fun postBackgroundData(message: String): Boolean
}