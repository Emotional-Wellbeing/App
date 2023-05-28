package es.upm.bienestaremocional.domain.repository.questionnaire

import es.upm.bienestaremocional.data.database.entity.QuestionnaireRoundReduced

/**
 * Repository to interact with [QuestionnaireRoundReduced] (only inserts)
 */
interface QuestionnaireRoundReducedRepository
{
    suspend fun insert(): QuestionnaireRoundReduced
}