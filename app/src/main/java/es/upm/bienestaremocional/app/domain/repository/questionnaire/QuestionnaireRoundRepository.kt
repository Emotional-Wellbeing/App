package es.upm.bienestaremocional.app.domain.repository.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRound

/**
 * Repository to interact with [QuestionnaireRound] entity.
 * Delete operation is not present by design.
 */
interface QuestionnaireRoundRepository
{
    suspend fun insert(questionnaireRound: QuestionnaireRound) : Long
    suspend fun update(questionnaireRound: QuestionnaireRound)
    suspend fun getAll(): List<QuestionnaireRound>
    suspend fun get(id: Long): QuestionnaireRound?
}