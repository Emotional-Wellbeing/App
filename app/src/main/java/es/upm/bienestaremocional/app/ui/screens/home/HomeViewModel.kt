package es.upm.bienestaremocional.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.domain.processing.processRecords
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRoundFullRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.UCLARepository
import es.upm.bienestaremocional.app.utils.TimeGranularity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pssRepository: PSSRepository,
    private val phqRepository: PHQRepository,
    private val uclaRepository: UCLARepository,
    private val questionnaireRoundFullRepository: QuestionnaireRoundFullRepository,
    val appSettings: AppSettings
): ViewModel()
{

    private val _uncompletedQuestionnaires : MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val uncompletedQuestionnaires : StateFlow<Boolean> = _uncompletedQuestionnaires.asStateFlow()

    val questionnaires : List<Questionnaire> = runBlocking {
        Questionnaire.getMandatory() + appSettings.getQuestionnairesSelected().first()
    }

    init {
        viewModelScope.launch {
            _uncompletedQuestionnaires.value = questionnaireRoundFullRepository
                .getAllUncompleted()
                .isNotEmpty()
        }
    }

    suspend fun getStressScore() : Int?
    {
        val scores = pssRepository.getAllFromYesterday()
        return if (scores.any { it.score != null })
            processRecords(scores, TimeGranularity.Day)[0].score.toInt()
        else
            null
    }
    suspend fun getDepressionScore() : Int?
    {
        val scores = phqRepository.getAllFromYesterday()
        return if (scores.any { it.score != null })
            processRecords(scores, TimeGranularity.Day)[0].score.toInt()
        else
            null
    }
    suspend fun getLonelinessScore() : Int?
    {
        val scores = uclaRepository.getAllFromYesterday()
        return if (scores.any { it.score != null })
            processRecords(scores, TimeGranularity.Day)[0].score.toInt()
        else
            null
    }

}
