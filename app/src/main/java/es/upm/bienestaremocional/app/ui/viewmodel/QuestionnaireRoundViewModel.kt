package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.ui.state.QuestionnaireRoundState

class QuestionnaireRoundViewModel(private val appSettings: AppSettingsInterface) : ViewModel()
{
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                QuestionnaireRoundViewModel(appSettings = MainApplication.appSettings)
            }
        }
    }
    var state: QuestionnaireRoundState by mutableStateOf(QuestionnaireRoundState.InProgress(0))

    private val questionnaires = Questionnaire.getMandatory() + appSettings.getQuestionnairesSelectedValue().toList()

    private val questionnairesData = questionnaires.mapIndexed {
            index, questionnaire -> QuestionnaireData(questionnaire,"${index+1}/${questionnaires.size}: ") }
    private var actualQuestionnaire = 0

    fun getQuestionnaireData(): QuestionnaireData = questionnairesData[actualQuestionnaire]

    fun onFinish()
    {
        state = if (actualQuestionnaire + 1 < questionnaires.size) {
            actualQuestionnaire++
            QuestionnaireRoundState.InProgress(actualQuestionnaire)
        }
        else
            QuestionnaireRoundState.Finishing
    }

    fun onSkip()
    {
        state = if (actualQuestionnaire + 1 < questionnaires.size) {
            actualQuestionnaire++
            QuestionnaireRoundState.InProgress(actualQuestionnaire)
        }
        else
            QuestionnaireRoundState.Finishing
    }
}