package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.alarm.AlarmsAvailable
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.*
import es.upm.bienestaremocional.app.data.questionnaire.PHQManager
import es.upm.bienestaremocional.app.data.questionnaire.PSSManager
import es.upm.bienestaremocional.app.data.questionnaire.UCLAManager
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRoundFullRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRoundReducedRepository
import es.upm.bienestaremocional.app.ui.notification.Notification
import es.upm.bienestaremocional.app.ui.state.DebugState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DebugViewModel @Inject constructor(
    private val notification: Notification,
    private val questionnaireRoundWithQuestionnairesRepository: QuestionnaireRoundFullRepository,
    private val questionnaireRoundReducedRepository: QuestionnaireRoundReducedRepository,
    private val appDAO: AppDAO
): ViewModel()
{
    //state
    private val _state = MutableStateFlow<DebugState>(DebugState.ShowOptions)
    val state: StateFlow<DebugState> = _state.asStateFlow()

    //data
    private val _questionnaireRoundsIncompleted = MutableLiveData<List<QuestionnaireRoundFull>>()
    val questionnaireRoundsIncompleted: LiveData<List<QuestionnaireRoundFull>>
        get() = _questionnaireRoundsIncompleted
    private val _questionnaireRounds = MutableLiveData<List<QuestionnaireRoundFull>>()
    val questionnaireRounds: LiveData<List<QuestionnaireRoundFull>>
        get() = _questionnaireRounds

    private fun fetchIncompletedQuestionnaireRounds()
    {
        viewModelScope.launch {
            _questionnaireRoundsIncompleted.value = questionnaireRoundWithQuestionnairesRepository.getAllIncompleted()
        }
    }

    private fun fetchAllQuestionnaireRounds()
    {
        viewModelScope.launch {
            _questionnaireRounds.value = questionnaireRoundWithQuestionnairesRepository.getAll()
        }
    }

    fun onNotification()
    {
        viewModelScope.launch {
            val questionnaireRoundReduced = questionnaireRoundReducedRepository.insert()
            notification.showQuestionnaireNotification(questionnaireRoundReduced)
        }
    }

    fun onQueryAllQuestionnaireRounds()
    {
        _state.value = DebugState.QueryAllQuestionnaireRounds
        fetchAllQuestionnaireRounds()
    }

    fun onQueryUncompletedQuestionnaireRounds()
    {
        _state.value = DebugState.QueryUncompletedQuestionnaireRounds
        fetchIncompletedQuestionnaireRounds()
    }

    suspend fun onPrepoulateDatabase()
    {
        val days = 14
        List(days) { index ->
            AlarmsAvailable.allAlarms.forEach {
                val createdAt = it.time.minusDays(index.toLong())
                    .atZone(ZoneId.systemDefault())
                    .toEpochSecond() * 1000

                val pssManager = PSSManager()
                val phqManager = PHQManager()
                val uclaManager = UCLAManager()

                val pss = PSS(createdAt = createdAt)
                val phq = PHQ(createdAt = createdAt)
                val ucla = UCLA(createdAt = createdAt)

                for (questionIndex in 0 until pssManager.numberOfQuestions)
                {
                    pssManager.setAnswer(questionIndex,Random.nextInt(0,pssManager.numberOfAnswers))
                }
                pssManager.setEntity(pss)

                for (questionIndex in 0 until phqManager.numberOfQuestions)
                {
                    phqManager.setAnswer(questionIndex,Random.nextInt(0,phqManager.numberOfAnswers))
                }
                phqManager.setEntity(phq)

                for (questionIndex in 0 until uclaManager.numberOfQuestions)
                {
                    uclaManager.setAnswer(questionIndex,Random.nextInt(0,uclaManager.numberOfAnswers))
                }
                uclaManager.setEntity(ucla)

                val pssId = appDAO.insert(pss)
                val phqId = appDAO.insert(phq)
                val uclaId = appDAO.insert(ucla)

                appDAO.insert(QuestionnaireRound(
                    createdAt = createdAt,
                    pss = pssId,
                    phq = phqId,
                    ucla  = uclaId))
            }
        }
    }

    suspend fun onDeleteDatabase()
    {
        appDAO.nukeDatabase()
    }
}