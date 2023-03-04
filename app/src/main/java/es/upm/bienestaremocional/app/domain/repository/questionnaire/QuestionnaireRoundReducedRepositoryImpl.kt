package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Log
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.*
import es.upm.bienestaremocional.app.data.settings.AppSettings
import javax.inject.Inject

/**
 * Implementation of [QuestionnaireRoundReducedRepository].
 * Logs all executions.
 */
class QuestionnaireRoundReducedRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val appSettings: AppSettings,
    private val logTag: String
): QuestionnaireRoundReducedRepository
{
    override suspend fun insert(): QuestionnaireRoundReduced
    {
        Log.d(logTag, "inserting new questionnaire round")
        val optionalQuestionnaires = appSettings.getQuestionnairesSelectedValue().toList()
        val pssId : Long = dao.insert(PSS())
        var phqId : Long? = null
        var uclaId : Long? = null

        optionalQuestionnaires.forEach {
            when(it.id)
            {
                "phq" -> {phqId = dao.insert(PHQ())}
                "ucla" -> {uclaId = dao.insert(UCLA())}
            }
        }

        val qrId = dao.insert(QuestionnaireRound(pss = pssId, phq = phqId, ucla  = uclaId))
        return QuestionnaireRoundReduced(qrId,pssId,phqId,uclaId)
    }
}