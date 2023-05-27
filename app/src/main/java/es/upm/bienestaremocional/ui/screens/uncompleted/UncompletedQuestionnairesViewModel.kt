package es.upm.bienestaremocional.ui.screens.uncompleted

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.QuestionnaireRoundFull
import es.upm.bienestaremocional.data.settings.AppSettings
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRoundFullRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UncompletedQuestionnairesViewModel @Inject constructor(
    private val questionnaireRoundFullRepository: QuestionnaireRoundFullRepository,
    val appSettings: AppSettings
): ViewModel()
{

    private val _questionnaireRoundsUncompleted = MutableLiveData<List<QuestionnaireRoundFull>>()
    val questionnaireRoundsUncompleted: LiveData<List<QuestionnaireRoundFull>>
        get() = _questionnaireRoundsUncompleted
    
    init {
        viewModelScope.launch {
            _questionnaireRoundsUncompleted.value = questionnaireRoundFullRepository.getAllUncompleted()

        }
    }

}
