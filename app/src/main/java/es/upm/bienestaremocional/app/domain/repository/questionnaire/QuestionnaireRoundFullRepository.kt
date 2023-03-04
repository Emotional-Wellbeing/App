package es.upm.bienestaremocional.app.domain.repository.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundFull

/**
 * Repository to interact with [QuestionnaireRoundFull] (only queries)
 */
interface QuestionnaireRoundFullRepository
{
    suspend fun getAll(): List<QuestionnaireRoundFull>
    suspend fun get(id: Long): QuestionnaireRoundFull
    suspend fun getAllIncompleted(): List<QuestionnaireRoundFull>
}