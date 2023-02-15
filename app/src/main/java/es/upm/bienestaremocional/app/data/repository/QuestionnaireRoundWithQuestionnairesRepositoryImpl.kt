package es.upm.bienestaremocional.app.data.repository

import android.util.Log
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundWithQuestionnaires

class QuestionnaireRoundWithQuestionnairesRepositoryImpl(private val dao: AppDAO): QuestionnaireRoundWithQuestionnairesRepository
{
    override suspend fun getAll(): List<QuestionnaireRoundWithQuestionnaires>
    {
        Log.d(MainApplication.logTag, "querying all questionnaire rounds with questionnaires ")
        return dao.getAllQuestionnaireRoundWithQuestionnaires()
    }

    override suspend fun get(id: Long): QuestionnaireRoundWithQuestionnaires
    {
        Log.d(MainApplication.logTag, "querying questionnaire round with questionnaires with id: $id")
        return dao.getQuestionnaireRoundWithQuestionnaires(id)
    }
}