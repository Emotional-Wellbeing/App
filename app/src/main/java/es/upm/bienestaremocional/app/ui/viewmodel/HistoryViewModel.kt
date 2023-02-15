package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundWithQuestionnaires
import es.upm.bienestaremocional.app.data.repository.QuestionnaireRoundWithQuestionnairesRepository
import kotlinx.coroutines.launch


class HistoryViewModel(private val questionnaireRoundWithQuestionnairesRepository: QuestionnaireRoundWithQuestionnairesRepository) : ViewModel()
{
    companion object
    {
        val Factory : ViewModelProvider.Factory = viewModelFactory{
            initializer {
                HistoryViewModel(MainApplication.questionnaireRoundWithQuestionnairesRepository)
            }
        }
    }

    private val _questionnaireRounds = MutableLiveData<List<QuestionnaireRoundWithQuestionnaires>>()
    val questionnaireRounds: LiveData<List<QuestionnaireRoundWithQuestionnaires>>
        get() = _questionnaireRounds

    fun fetchQuestionnaireRounds()
    {
        viewModelScope.launch {
            _questionnaireRounds.value = questionnaireRoundWithQuestionnairesRepository.getAll()
        }
    }
}