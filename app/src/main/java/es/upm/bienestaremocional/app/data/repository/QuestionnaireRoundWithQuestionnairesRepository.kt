package es.upm.bienestaremocional.app.data.repository

import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundWithQuestionnaires

interface QuestionnaireRoundWithQuestionnairesRepository
{
    suspend fun getAll(): List<QuestionnaireRoundWithQuestionnaires>
    suspend fun get(id: Long): QuestionnaireRoundWithQuestionnaires
}