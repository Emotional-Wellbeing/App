package es.upm.bienestaremocional.app.domain.repository.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundReduced

/**
 * Repository to interact with [QuestionnaireRoundReduced] (only inserts)
 */
interface QuestionnaireRoundReducedRepository
{
    suspend fun insert(): QuestionnaireRoundReduced
}