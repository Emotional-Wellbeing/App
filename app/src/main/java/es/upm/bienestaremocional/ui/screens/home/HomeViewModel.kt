package es.upm.bienestaremocional.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.Measure
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.data.settings.AppSettings
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyRoundFullRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyStressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dailyStressRepository: DailyStressRepository,
    private val dailyDepressionRepository: DailyDepressionRepository,
    private val dailyLonelinessRepository: DailyLonelinessRepository,
    private val dailyRoundFullRepository: DailyRoundFullRepository,
    val appSettings: AppSettings
) : ViewModel() {

    private val _uncompletedQuestionnaires: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val uncompletedQuestionnaires: StateFlow<Boolean> = _uncompletedQuestionnaires.asStateFlow()

    val questionnaires: List<DailyScoredQuestionnaire> =
        runBlocking {
            val measures = Measure.getMandatory() + appSettings.getMeasuresSelected().first()
            measures.mapNotNull { DailyScoredQuestionnaire.fromMeasure(it) }
        }

    init {
        viewModelScope.launch {
            _uncompletedQuestionnaires.value = dailyRoundFullRepository
                .getAllUncompleted()
                .isNotEmpty()
        }
    }

    suspend fun getStressScore(): Int? {
        return dailyStressRepository.getLastElement()?.score
    }

    suspend fun getDepressionScore(): Int? {
        return dailyDepressionRepository.getLastElement()?.score
    }

    suspend fun getLonelinessScore(): Int? {
        return dailyLonelinessRepository.getLastElement()?.score
    }
}
