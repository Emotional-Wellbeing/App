package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRoundReducedRepository
import es.upm.bienestaremocional.app.ui.notification.Notification
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(
    private val notification: Notification,
    private val questionnaireRoundReducedRepository: QuestionnaireRoundReducedRepository
): ViewModel()
{
    fun onNotification()
    {
        viewModelScope.launch {
            val questionnaireRoundReduced = questionnaireRoundReducedRepository.insert()
            notification.showQuestionnaireNotification(questionnaireRoundReduced)
        }
    }
}