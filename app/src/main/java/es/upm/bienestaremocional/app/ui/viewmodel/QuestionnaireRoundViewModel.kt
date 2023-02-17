package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.database.entity.PHQ
import es.upm.bienestaremocional.app.data.database.entity.PSS
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRound
import es.upm.bienestaremocional.app.data.database.entity.UCLA
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRoundRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.UCLARepository
import es.upm.bienestaremocional.app.ui.state.QuestionnaireRoundState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class QuestionnaireRoundViewModel @Inject constructor(
    private val questionnaireRoundRepository: QuestionnaireRoundRepository,
    private val pssRepository: PSSRepository,
    private val phqRepository: PHQRepository,
    private val uclaRepository: UCLARepository,
    appSettings: AppSettingsInterface,
    @Named("logTag") val logTag : String
) : ViewModel()
{
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