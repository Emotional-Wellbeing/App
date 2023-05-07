package es.upm.bienestaremocional.app.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.domain.processing.aggregateEntriesPerDay
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.UCLARepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pssRepository: PSSRepository,
    private val phqRepository: PHQRepository,
    private val uclaRepository: UCLARepository,
    val appSettings: AppSettings
): ViewModel()
{

    val questionnaires : List<Questionnaire> = runBlocking {
        Questionnaire.getMandatory() + appSettings.getQuestionnairesSelected().first()
    }

    suspend fun getStressScore() : Int?
    {
        val scores = pssRepository.getAllFromYesterday()
        return if (scores.any { it.score != null })
            aggregateEntriesPerDay(scores)[0].second.toInt()
        else
            null
    }
    suspend fun getDepressionScore() : Int?
    {
        val scores = phqRepository.getAllFromYesterday()
        return if (scores.any { it.score != null })
            aggregateEntriesPerDay(scores)[0].second.toInt()
        else
            null
    }
    suspend fun getLonelinessScore() : Int?
    {
        val scores = uclaRepository.getAllFromYesterday()
        return if (scores.any { it.score != null })
            aggregateEntriesPerDay(scores)[0].second.toInt()
        else
            null
    }

}
