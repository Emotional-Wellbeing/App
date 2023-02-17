package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.ui.notification.Notification
import es.upm.bienestaremocional.app.ui.screen.destinations.QuestionnaireRoundScreenDestination
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val notification: Notification
): ViewModel()
{
    //debug
    fun onNotification()
    {
        notification.showQuestionnaireNotification()
    }

    //debug
    fun onQuestionnaire(navigator: DestinationsNavigator)
    {
        navigator.navigate(QuestionnaireRoundScreenDestination)
    }
}