package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.ScoredManager
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffStressManager
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffStressRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository
import es.upm.bienestaremocional.ui.screens.destinations.OneOffStressScreenDestination
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@HiltViewModel
class OneOffStressViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val dailyStressRepository: OneOffStressRepository,
    dailyStressManager: OneOffStressManager,
) : ScoredQuestionnaireViewModel(
    repository = dailyStressRepository as QuestionnaireRepository<MeasureEntity>,
    manager = dailyStressManager as ScoredManager<MeasureEntity>,
    entityId = OneOffStressScreenDestination.argsFrom(savedStateHandle).entityId,
)