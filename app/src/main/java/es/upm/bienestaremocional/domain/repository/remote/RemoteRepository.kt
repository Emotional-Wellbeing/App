package es.upm.bienestaremocional.domain.repository.remote

import es.upm.bienestaremocional.data.remote.userdata.DailyQuestionnairesRequest
import es.upm.bienestaremocional.data.remote.userdata.DailyQuestionnairesResponse
import es.upm.bienestaremocional.data.remote.userdata.OneOffQuestionnairesRequest
import es.upm.bienestaremocional.data.remote.userdata.OneOffQuestionnairesResponse
import es.upm.bienestaremocional.data.remote.userdata.UserDataRequest
import es.upm.bienestaremocional.data.remote.userdata.UserDataResponse

/**
 * Repository to interact with the Remote API
 */
interface RemoteRepository
{
    suspend fun getScore(): Int?
    suspend fun postUserData(userDataRequest: UserDataRequest): UserDataResponse?
    suspend fun postDailyQuestionnaires(dailyQuestionnairesRequest: DailyQuestionnairesRequest):
            DailyQuestionnairesResponse?
    suspend fun postOneOffQuestionnaires(oneOffQuestionnairesRequest: OneOffQuestionnairesRequest):
            OneOffQuestionnairesResponse?
    suspend fun postBackgroundData(message: String): Boolean?
}