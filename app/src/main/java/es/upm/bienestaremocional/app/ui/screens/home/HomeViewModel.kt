package es.upm.bienestaremocional.app.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.domain.processing.aggregateEntriesPerDay
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.UCLARepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pssRepository: PSSRepository,
    private val phqRepository: PHQRepository,
    private val uclaRepository: UCLARepository,
    val appSettings: AppSettings
): ViewModel()
{
    val questionnaires = Questionnaire.getMandatory().toSet() + appSettings.getQuestionnairesSelectedValue()

    suspend fun getStressScore() : Float?
    {
        val scores = pssRepository.getAllFromYesterday()
        return if (scores.any { it.score != null })
            aggregateEntriesPerDay(scores)[0].second
        else
            null
    }
    suspend fun getDepressionScore() : Float?
    {
        val scores = phqRepository.getAllFromYesterday()
        return if (scores.any { it.score != null })
            aggregateEntriesPerDay(scores)[0].second
        else
            null
    }
    suspend fun getLonelinessScore() : Float?
    {
        val scores = uclaRepository.getAllFromYesterday()
        return if (scores.any { it.score != null })
            aggregateEntriesPerDay(scores)[0].second
        else
            null
    }

}
