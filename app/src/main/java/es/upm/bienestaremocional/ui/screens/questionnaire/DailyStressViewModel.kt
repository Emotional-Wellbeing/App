package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.ScoredManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailyStressManager
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyStressRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository
import es.upm.bienestaremocional.destinations.DailyStressScreenDestination
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@HiltViewModel
class DailyStressViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val dailyStressRepository: DailyStressRepository,
    dailyStressManager: DailyStressManager,
) : ScoredQuestionnaireViewModel(
    repository = dailyStressRepository as QuestionnaireRepository<MeasureEntity>,
    manager = dailyStressManager as ScoredManager<MeasureEntity>,
    entityId = DailyStressScreenDestination.argsFrom(savedStateHandle).entityId,
)