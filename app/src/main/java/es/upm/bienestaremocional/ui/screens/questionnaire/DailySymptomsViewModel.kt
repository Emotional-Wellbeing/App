package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.NotScoredManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailySymptomsManager
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySymptomsRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository
import es.upm.bienestaremocional.destinations.DailySymptomsScreenDestination
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@HiltViewModel
class DailySymptomsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dailySymptomsRepository: DailySymptomsRepository,
    dailySymptomsManager: DailySymptomsManager,
) : NotScoredQuestionnaireViewModel(
    repository = dailySymptomsRepository as QuestionnaireRepository<MeasureEntity>,
    manager = dailySymptomsManager as NotScoredManager<MeasureEntity>,
    entityId = DailySymptomsScreenDestination.argsFrom(savedStateHandle).entityId,
)