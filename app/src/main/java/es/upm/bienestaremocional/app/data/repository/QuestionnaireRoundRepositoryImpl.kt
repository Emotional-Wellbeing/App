package es.upm.bienestaremocional.app.data.repository

import android.util.Log
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRound

class QuestionnaireRoundRepositoryImpl(private val dao: AppDAO): QuestionnaireRoundRepository
{
    override suspend fun insert(questionnaireRound: QuestionnaireRound): Long
    {
        Log.d(MainApplication.logTag, "inserting new questionnaire round")
        return dao.insert(questionnaireRound)
    }

    override suspend fun update(questionnaireRound: QuestionnaireRound)
    {
        Log.d(MainApplication.logTag, "updating questionnaire round with id: ${questionnaireRound.id}")
        questionnaireRound.apply { modifiedAt = System.currentTimeMillis() }
        return dao.update(questionnaireRound)
    }

    override suspend fun getAll(): List<QuestionnaireRound>
    {
        Log.d(MainApplication.logTag, "querying all questionnaire rounds")
        return dao.getAllQuestionnaireRound()
    }

    override suspend fun get(id: Long): QuestionnaireRound
    {
        Log.d(MainApplication.logTag, "querying questionnaire round with id: $id")
        return dao.getQuestionnaireRound(id)
    }
}