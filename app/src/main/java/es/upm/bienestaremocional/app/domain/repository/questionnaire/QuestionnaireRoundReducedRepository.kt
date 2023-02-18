package es.upm.bienestaremocional.app.domain.repository.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundReduced

interface QuestionnaireRoundReducedRepository
{
    suspend fun insert(): QuestionnaireRoundReduced
}