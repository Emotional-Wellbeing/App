package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.database.entity.PHQ
import es.upm.bienestaremocional.app.data.database.entity.PSS
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRound
import es.upm.bienestaremocional.app.data.database.entity.UCLA
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.data.repository.PHQRepository
import es.upm.bienestaremocional.app.data.repository.PSSRepository
import es.upm.bienestaremocional.app.data.repository.QuestionnaireRoundRepository
import es.upm.bienestaremocional.app.data.repository.UCLARepository
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.ui.state.QuestionnaireRoundState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuestionnaireRoundViewModel(private val questionnaireRoundRepository: QuestionnaireRoundRepository,
                                  private val pssRepository: PSSRepository,
                                  private val phqRepository: PHQRepository,
                                  private val uclaRepository: UCLARepository,
                                  private val appSettings: AppSettingsInterface) : ViewModel()
{
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                QuestionnaireRoundViewModel(
                    questionnaireRoundRepository = MainApplication.questionnaireRoundRepository,
                    pssRepository = MainApplication.pssRepository,
                    phqRepository = MainApplication.phqRepository,
                    uclaRepository = MainApplication.uclaRepository,
                    appSettings = MainApplication.appSettings)
            }
        }
    }

    private val _state = MutableStateFlow<QuestionnaireRoundState>(QuestionnaireRoundState.Init)
    val state: StateFlow<QuestionnaireRoundState> = _state.asStateFlow()

    private val questionnaires = Questionnaire.getMandatory() + appSettings.getQuestionnairesSelectedValue().toList()

    private val questionnairesData = questionnaires.mapIndexed {
            index, questionnaire -> QuestionnaireData(questionnaire,"${index+1}/${questionnaires.size}: ") }
    private var actualQuestionnaire = 0

    fun getQuestionnaireData(): QuestionnaireData = questionnairesData[actualQuestionnaire]

    fun onFinish()
    {
        _state.value = if (actualQuestionnaire + 1 < questionnaires.size) {
            actualQuestionnaire++
            QuestionnaireRoundState.PreShow
        }
        else
            QuestionnaireRoundState.Finishing
    }

    fun onSkip()
    {
        _state.value = if (actualQuestionnaire + 1 < questionnaires.size) {
            actualQuestionnaire++
            QuestionnaireRoundState.PreShow
        }
        else
            QuestionnaireRoundState.Finishing
    }

    suspend fun initAction()
    {
        val pssId : Long = pssRepository.insert(PSS())
        val questionnaireRound = QuestionnaireRound(pss = pssId)

        var phqId : Long? = null
        var uclaId : Long? = null

        questionnaires.forEach {
            when(it.id)
            {
                "pss" -> {} //added before
                "phq" -> {phqId = phqRepository.insert(PHQ())}
                "ucla" -> {uclaId = uclaRepository.insert(UCLA())}
            }
        }
        questionnaireRound.apply {
            phq = phqId
            ucla = uclaId
        }
        questionnaireRoundRepository.insert(questionnaireRound)
        updateState(QuestionnaireRoundState.Show)
    }

    fun updateState(newState: QuestionnaireRoundState)
    {
        _state.value = newState
    }
}