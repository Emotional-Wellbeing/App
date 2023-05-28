package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Log
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.QuestionnaireRound
import javax.inject.Inject

/**
 * Implementation of [QuestionnaireRoundRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class QuestionnaireRoundRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
): QuestionnaireRoundRepository
{
    override suspend fun insert(questionnaireRound: QuestionnaireRound): Long
    {
        Log.d(logTag, "inserting new questionnaire round")
        return dao.insert(questionnaireRound)
    }

    override suspend fun update(questionnaireRound: QuestionnaireRound)
    {
        Log.d(logTag, "updating questionnaire round with id: ${questionnaireRound.id}")
        questionnaireRound.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(questionnaireRound)
    }

    override suspend fun getAll(): List<QuestionnaireRound>
    {
        Log.d(logTag, "querying all questionnaire rounds")
        return dao.getAllQuestionnaireRound()
    }

    override suspend fun get(id: Long): QuestionnaireRound?
    {
        Log.d(logTag, "querying questionnaire round with id: $id")
        return dao.getQuestionnaireRound(id)
    }
}