package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Log
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundFull
import javax.inject.Inject

/**
 * Implementation of [QuestionnaireRoundFullRepository]
 * Logs all executions
 */
class QuestionnaireRoundFullRepositoryImpl @Inject constructor (
    private val dao: AppDAO,
    private val logTag: String
):
    QuestionnaireRoundFullRepository
{
    override suspend fun getAll(): List<QuestionnaireRoundFull>
    {
        Log.d(logTag, "querying all questionnaire rounds with questionnaires ")
        return dao.getAllQuestionnaireRoundFull()
    }

    override suspend fun get(id: Long): QuestionnaireRoundFull
    {
        Log.d(logTag, "querying questionnaire round with questionnaires with id: $id")
        return dao.getQuestionnaireRoundFull(id)
    }

    override suspend fun getAllUncompleted(): List<QuestionnaireRoundFull>
    {
        Log.d(logTag, "querying all uncompleted questionnaire rounds ")
        return dao.getAllQuestionnaireRoundUncompleted()
    }
}