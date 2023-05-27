package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.PHQ
import es.upm.bienestaremocional.data.database.entity.PSS
import es.upm.bienestaremocional.data.database.entity.QuestionnaireRound
import es.upm.bienestaremocional.data.database.entity.UCLA
import es.upm.bienestaremocional.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.domain.repository.questionnaire.PHQRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.PSSRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRoundRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.UCLARepository
import es.upm.bienestaremocional.ui.screens.destinations.QuestionnaireRoundScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.QuestionnaireScreenDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class QuestionnaireRoundViewModel @Inject constructor(
    private val questionnaireRoundRepository: QuestionnaireRoundRepository,
    private val pssRepository: PSSRepository,
    private val phqRepository: PHQRepository,
    private val uclaRepository: UCLARepository,
    savedStateHandle: SavedStateHandle,
    @Named("logTag") val logTag : String
) : ViewModel()
{
    //state
    private val _state = MutableStateFlow<QuestionnaireRoundState>(QuestionnaireRoundState.Init)
    val state: StateFlow<QuestionnaireRoundState> = _state.asStateFlow()

    //from screen
    private val questionnaireRoundReduced = QuestionnaireRoundScreenDestination.argsFrom(savedStateHandle).questionnaireRoundReduced

    //variables from round
    private var questionnaireRound : QuestionnaireRound
    var pss: PSS? = null
    var phq: PHQ? = null
    var ucla: UCLA? = null

    var questionnaires: List<Questionnaire>

    init {
        runBlocking {
            //we know that qrId is present in database due to we cannot delete questionnaire rounds
            questionnaireRound = questionnaireRoundRepository.get(questionnaireRoundReduced.qrId)!!
            pss = questionnaireRoundReduced.pssId?.let { pssRepository.get(it) }
            phq = questionnaireRoundReduced.phqId?.let { phqRepository.get(it) }
            ucla = questionnaireRoundReduced.uclaId?.let { uclaRepository.get(it) }
        }

        val questionnairesTemp = mutableListOf<Questionnaire>()
        pss?.let { questionnairesTemp.add(Questionnaire.PSS) }
        phq?.let { questionnairesTemp.add(Questionnaire.PHQ) }
        ucla?.let { questionnairesTemp.add(Questionnaire.UCLA) }
        questionnaires = questionnairesTemp.toList()
    }


    private var actualQuestionnaire = 0

    fun onInit()
    {
        _state.value = if (questionnaires.isNotEmpty())
            QuestionnaireRoundState.Show
        else
            QuestionnaireRoundState.Finished
    }

    fun onResumeRound()
    {
        _state.value = if (actualQuestionnaire + 1 < questionnaires.size) {
            actualQuestionnaire++
            QuestionnaireRoundState.Show
        }
        else
            QuestionnaireRoundState.Finishing
    }

    fun onShow(navigator: DestinationsNavigator)
    {
        val entityId = when (questionnaires[actualQuestionnaire])
        {
            Questionnaire.PSS -> questionnaireRoundReduced.pssId!!
            Questionnaire.PHQ -> questionnaireRoundReduced.phqId!!
            Questionnaire.UCLA -> questionnaireRoundReduced.uclaId!!
        }
        navigator.navigate(
            QuestionnaireScreenDestination(
                questionnaire = questionnaires[actualQuestionnaire],
                questionnaireIndex = actualQuestionnaire,
                questionnaireSize = questionnaires.size,
                entityId = entityId)
        )
        _state.value = QuestionnaireRoundState.PostShow
    }

    fun onFinishing()
    {
        runBlocking {
            updateRound()
        }
        _state.value = QuestionnaireRoundState.Finished
    }

    private suspend fun updateRound()
    {
        questionnaireRoundRepository.update(questionnaireRound)
    }
}