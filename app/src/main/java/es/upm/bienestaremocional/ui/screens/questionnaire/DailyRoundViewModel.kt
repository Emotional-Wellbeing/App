package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import es.upm.bienestaremocional.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.data.questionnaire.daily.DailyNotScoredQuestionnaireDrawable
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaireDrawable
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyRoundRepository
import es.upm.bienestaremocional.destinations.DailyDepressionScreenDestination
import es.upm.bienestaremocional.destinations.DailyLonelinessScreenDestination
import es.upm.bienestaremocional.destinations.DailyRoundScreenDestination
import es.upm.bienestaremocional.destinations.DailyStressScreenDestination
import es.upm.bienestaremocional.destinations.DailySuicideScreenDestination
import es.upm.bienestaremocional.destinations.DailySymptomsScreenDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DailyRoundViewModel @Inject constructor(
    private val dailyRoundRepository: DailyRoundRepository,
    savedStateHandle: SavedStateHandle,
    @Named("logTag") val logTag: String
) : ViewModel() {
    //state
    private val _state = MutableStateFlow<QuestionnaireRoundState>(QuestionnaireRoundState.Init)
    val state: StateFlow<QuestionnaireRoundState> = _state.asStateFlow()

    //from screen
    private val dailyRound: DailyRound = DailyRoundScreenDestination
        .argsFrom(savedStateHandle)
        .dailyRound

    private val questionnaires = mutableListOf<Questionnaire>()

    private var totalSize = 0

    init {
        viewModelScope.launch {
            dailyRound.stressId?.let {
                if (dailyRound.moment == DailyRound.Moment.Morning)
                    questionnaires.add(
                        DailyScoredQuestionnaireDrawable.MorningStress
                    )
                else
                    questionnaires.add(
                        DailyScoredQuestionnaireDrawable.NightStress
                    )
            }
            dailyRound.depressionId?.let {
                if (dailyRound.moment == DailyRound.Moment.Morning)
                    questionnaires.add(
                        DailyScoredQuestionnaireDrawable.MorningDepression
                    )
                else
                    questionnaires.add(
                        DailyScoredQuestionnaireDrawable.NightDepression
                    )
            }
            //Suicide needs to be present after depression
            dailyRound.suicideId?.let {
                if (dailyRound.moment == DailyRound.Moment.Morning)
                    questionnaires.add(
                        DailyNotScoredQuestionnaireDrawable.MorningSuicide
                    )
                else
                    questionnaires.add(
                        DailyNotScoredQuestionnaireDrawable.NightSuicide
                    )
            }
            dailyRound.lonelinessId?.let {
                if (dailyRound.moment == DailyRound.Moment.Morning)
                    questionnaires.add(
                        DailyScoredQuestionnaireDrawable.MorningLoneliness
                    )
                else
                    questionnaires.add(
                        DailyScoredQuestionnaireDrawable.NightLoneliness
                    )
            }

            dailyRound.symptomsId?.let {
                questionnaires.add(
                    DailyNotScoredQuestionnaireDrawable.Symptoms
                )
            }

            totalSize = questionnaires.size
        }
    }


    private var actualQuestionnaire = 0

    fun onInit() {
        _state.value = if (questionnaires.isNotEmpty())
            QuestionnaireRoundState.Show
        else
            QuestionnaireRoundState.Finished
    }

    fun onResumeRound() {
        _state.value = if (actualQuestionnaire + 1 < totalSize) {
            actualQuestionnaire++
            QuestionnaireRoundState.Show
        }
        else
            QuestionnaireRoundState.Finishing
    }

    fun onShow(navigator: DestinationsNavigator) {
        if (actualQuestionnaire < questionnaires.size) {
            val direction = when (questionnaires[actualQuestionnaire]) {
                DailyScoredQuestionnaireDrawable.MorningStress ->
                    DailyStressScreenDestination(
                        entityId = dailyRound.stressId!!,
                        moment = DailyRound.Moment.Morning,
                        questionnaireIndex = actualQuestionnaire,
                        questionnaireSize = totalSize
                    )

                DailyScoredQuestionnaireDrawable.MorningDepression ->
                    DailyDepressionScreenDestination(
                        entityId = dailyRound.depressionId!!,
                        moment = DailyRound.Moment.Morning,
                        questionnaireIndex = actualQuestionnaire,
                        questionnaireSize = totalSize
                    )

                DailyScoredQuestionnaireDrawable.MorningLoneliness ->
                    DailyLonelinessScreenDestination(
                        entityId = dailyRound.lonelinessId!!,
                        moment = DailyRound.Moment.Morning,
                        questionnaireIndex = actualQuestionnaire,
                        questionnaireSize = totalSize
                    )

                DailyScoredQuestionnaireDrawable.NightStress ->
                    DailyStressScreenDestination(
                        entityId = dailyRound.stressId!!,
                        moment = DailyRound.Moment.Night,
                        questionnaireIndex = actualQuestionnaire,
                        questionnaireSize = totalSize
                    )

                DailyScoredQuestionnaireDrawable.NightDepression ->
                    DailyDepressionScreenDestination(
                        entityId = dailyRound.depressionId!!,
                        moment = DailyRound.Moment.Night,
                        questionnaireIndex = actualQuestionnaire,
                        questionnaireSize = totalSize
                    )

                DailyScoredQuestionnaireDrawable.NightLoneliness ->
                    DailyLonelinessScreenDestination(
                        entityId = dailyRound.lonelinessId!!,
                        moment = DailyRound.Moment.Night,
                        questionnaireIndex = actualQuestionnaire,
                        questionnaireSize = totalSize
                    )

                DailyNotScoredQuestionnaireDrawable.MorningSuicide ->
                    DailySuicideScreenDestination(
                        entityId = dailyRound.suicideId!!,
                        moment = DailyRound.Moment.Morning,
                        questionnaireIndex = actualQuestionnaire,
                        questionnaireSize = totalSize
                    )

                DailyNotScoredQuestionnaireDrawable.NightSuicide ->
                    DailySuicideScreenDestination(
                        entityId = dailyRound.suicideId!!,
                        moment = DailyRound.Moment.Night,
                        questionnaireIndex = actualQuestionnaire,
                        questionnaireSize = totalSize
                    )

                DailyNotScoredQuestionnaireDrawable.Symptoms ->
                    DailySymptomsScreenDestination(
                        entityId = dailyRound.symptomsId!!
                    )

                else -> null
            }
            direction?.let { navigator.navigate(it) }
        }
        _state.value = QuestionnaireRoundState.PostShow
    }

    fun onFinishing() {
        runBlocking {
            updateRound()
        }
        _state.value = QuestionnaireRoundState.Finished
    }

    private suspend fun updateRound() {
        dailyRoundRepository.update(dailyRound)
    }
}