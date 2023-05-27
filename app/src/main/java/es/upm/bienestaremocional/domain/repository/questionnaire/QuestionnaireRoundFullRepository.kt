package es.upm.bienestaremocional.domain.repository.questionnaire

import es.upm.bienestaremocional.data.database.entity.QuestionnaireRoundFull

/**
 * Repository to interact with [QuestionnaireRoundFull] (only queries)
 */
interface QuestionnaireRoundFullRepository
{
    suspend fun getAll(): List<QuestionnaireRoundFull>
    suspend fun get(id: Long): QuestionnaireRoundFull
    suspend fun getAllUncompleted(): List<QuestionnaireRoundFull>
}