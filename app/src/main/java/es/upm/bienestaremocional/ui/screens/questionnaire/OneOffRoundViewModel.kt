package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.round.OneOffRound
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffQuestionnaire
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffRoundRepository
import es.upm.bienestaremocional.ui.screens.destinations.OneOffDepressionScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.OneOffLonelinessScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.OneOffRoundScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.OneOffStressScreenDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class OneOffRoundViewModel @Inject constructor(
    private val oneOffRoundRepository: OneOffRoundRepository,
    savedStateHandle: SavedStateHandle,
    @Named("logTag") val logTag: String
) : ViewModel() {
    //state
    private val _state = MutableStateFlow<QuestionnaireRoundState>(QuestionnaireRoundState.Init)
    val state: StateFlow<QuestionnaireRoundState> = _state.asStateFlow()

    //from screen
    private val oneOffRound: OneOffRound = OneOffRoundScreenDestination
        .argsFrom(savedStateHandle)
        .oneOffRound


    var questionnaires: List<OneOffQuestionnaire>

    init {
        val questionnairesTemp = mutableListOf<OneOffQuestionnaire>()
        oneOffRound.stressId?.let { questionnairesTemp.add(OneOffQuestionnaire.Stress) }
        oneOffRound.depressionId?.let { questionnairesTemp.add(OneOffQuestionnaire.Depression) }
        oneOffRound.lonelinessId?.let { questionnairesTemp.add(OneOffQuestionnaire.Loneliness) }
        questionnaires = questionnairesTemp.toList()
    }


    private var actualQuestionnaire = 0

    fun onInit() {
        _state.value = if (questionnaires.isNotEmpty())
            QuestionnaireRoundState.Show
        else
            QuestionnaireRoundState.Finished
    }

    fun onResumeRound() {
        _state.value = if (actualQuestionnaire + 1 < questionnaires.size) {
            actualQuestionnaire++
            QuestionnaireRoundState.Show
        }
        else
            QuestionnaireRoundState.Finishing
    }

    fun onShow(navigator: DestinationsNavigator) {
        val direction = when (questionnaires[actualQuestionnaire]) {
            OneOffQuestionnaire.Stress ->
                OneOffStressScreenDestination(
                    entityId = oneOffRound.stressId!!,
                    questionnaireIndex = actualQuestionnaire,
                    questionnaireSize = questionnaires.size,
                )

            OneOffQuestionnaire.Depression ->
                OneOffDepressionScreenDestination(
                    entityId = oneOffRound.depressionId!!,
                    questionnaireIndex = actualQuestionnaire,
                    questionnaireSize = questionnaires.size,
                )

            OneOffQuestionnaire.Loneliness ->
                OneOffLonelinessScreenDestination(
                    entityId = oneOffRound.lonelinessId!!,
                    questionnaireIndex = actualQuestionnaire,
                    questionnaireSize = questionnaires.size,
                )
        }
        navigator.navigate(direction)
        _state.value = QuestionnaireRoundState.PostShow
    }

    fun onFinishing() {
        runBlocking {
            updateRound()
        }
        _state.value = QuestionnaireRoundState.Finished
    }

    private suspend fun updateRound() {
        oneOffRoundRepository.update(oneOffRound)
    }
}