package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.database.entity.PHQ
import es.upm.bienestaremocional.app.data.database.entity.PSS
import es.upm.bienestaremocional.app.data.database.entity.UCLA
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.UCLARepository
import es.upm.bienestaremocional.app.ui.screen.destinations.QuestionnaireRoundScreenDestination
import es.upm.bienestaremocional.app.ui.state.QuestionnaireRoundState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class QuestionnaireRoundViewModel @Inject constructor(
    private val pssRepository: PSSRepository,
    private val phqRepository: PHQRepository,
    private val uclaRepository: UCLARepository,
    savedStateHandle: SavedStateHandle,
    @Named("logTag") val logTag : String
) : ViewModel()
{
    private val _state = MutableStateFlow<QuestionnaireRoundState>(QuestionnaireRoundState.Init)
    val state: StateFlow<QuestionnaireRoundState> = _state.asStateFlow()

    private val questionnaireRoundReduced = QuestionnaireRoundScreenDestination.argsFrom(savedStateHandle).questionnaireRoundReduced

    var pss: PSS
    var phq: PHQ? = null
    var ucla: UCLA? = null

    var questionnaires: List<Questionnaire>
    private var questionnairesData : List<QuestionnaireData>

    init {
        runBlocking {
            pss = pssRepository.get(questionnaireRoundReduced.pssId)
            phq = questionnaireRoundReduced.phqId?.let { phqRepository.get(it) }
            ucla = questionnaireRoundReduced.uclaId?.let { uclaRepository.get(it) }
        }

        val questionnairesTemp = mutableListOf(Questionnaire.PSS)
        phq?.let { questionnairesTemp.add(Questionnaire.PHQ) }
        ucla?.let { questionnairesTemp.add(Questionnaire.UCLA) }
        questionnaires = questionnairesTemp.toList()

        questionnairesData = questionnaires.mapIndexed {
                index, questionnaire -> QuestionnaireData(questionnaire,"${index+1}/${questionnaires.size}: ") }
    }


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

    fun initAction()
    {
        updateState(QuestionnaireRoundState.Show)
    }

    fun updateState(newState: QuestionnaireRoundState)
    {
        _state.value = newState
    }
}