package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundFull
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRoundFullRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val questionnaireRoundWithQuestionnairesRepository: QuestionnaireRoundFullRepository
) : ViewModel()
{
    private val _questionnaireRounds = MutableLiveData<List<QuestionnaireRoundFull>>()
    val questionnaireRounds: LiveData<List<QuestionnaireRoundFull>>
        get() = _questionnaireRounds

    fun fetchQuestionnaireRounds()
    {
        viewModelScope.launch {
            _questionnaireRounds.value = questionnaireRoundWithQuestionnairesRepository.getAll()
        }
    }
}