package es.upm.bienestaremocional.app.data.repository

import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRound

interface QuestionnaireRoundRepository
{
    suspend fun insert(questionnaireRound: QuestionnaireRound) : Long
    suspend fun update(questionnaireRound: QuestionnaireRound)
    suspend fun getAll(): List<QuestionnaireRound>
    suspend fun get(id: Long): QuestionnaireRound
}