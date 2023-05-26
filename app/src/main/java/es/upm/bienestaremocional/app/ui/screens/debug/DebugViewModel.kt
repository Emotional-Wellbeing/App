package es.upm.bienestaremocional.app.ui.screens.debug

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.LastUpload
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRound
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundFull
import es.upm.bienestaremocional.app.data.info.AppInfo
import es.upm.bienestaremocional.app.data.notification.NotificationsAvailable
import es.upm.bienestaremocional.app.data.questionnaire.generatePHQEntry
import es.upm.bienestaremocional.app.data.questionnaire.generatePSSEntry
import es.upm.bienestaremocional.app.data.questionnaire.generateUCLAEntry
import es.upm.bienestaremocional.app.data.worker.NotificationWorker
import es.upm.bienestaremocional.app.data.worker.UploadWorker
import es.upm.bienestaremocional.app.data.worker.WorkAdministrator
import es.upm.bienestaremocional.app.domain.repository.LastUploadRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRoundFullRepository
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRoundReducedRepository
import es.upm.bienestaremocional.app.domain.repository.remote.RemoteOperationResult
import es.upm.bienestaremocional.app.domain.repository.remote.RemoteRepository
import es.upm.bienestaremocional.app.domain.usecases.PostUserDataUseCase
import es.upm.bienestaremocional.app.ui.notification.Notification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(
    private val notification: Notification,
    private val questionnaireRoundWithQuestionnairesRepository: QuestionnaireRoundFullRepository,
    private val questionnaireRoundReducedRepository: QuestionnaireRoundReducedRepository,
    private val appDAO: AppDAO,
    private val remoteRepository: RemoteRepository,
    private val workAdministrator: WorkAdministrator,
    private val appInfo: AppInfo,
    private val lastUploadRepository: LastUploadRepository,
    private val postUserDataUseCase: PostUserDataUseCase,
): ViewModel()
{
    //state
    private val _state = MutableStateFlow<DebugState>(DebugState.ShowOptions)
    val state: StateFlow<DebugState> = _state.asStateFlow()

    //data
    private val _questionnaireRoundsUncompleted = MutableLiveData<List<QuestionnaireRoundFull>>()
    val questionnaireRoundsUncompleted: LiveData<List<QuestionnaireRoundFull>>
        get() = _questionnaireRoundsUncompleted
    private val _questionnaireRounds = MutableLiveData<List<QuestionnaireRoundFull>>()
    val questionnaireRounds: LiveData<List<QuestionnaireRoundFull>>
        get() = _questionnaireRounds
    private var _workInfo : LiveData<List<WorkInfo>> = MutableLiveData()
    val workInfo: LiveData<List<WorkInfo>>
        get() = _workInfo

    private fun fetchUncompletedQuestionnaireRounds()
    {
        viewModelScope.launch {
            _questionnaireRoundsUncompleted.value = questionnaireRoundWithQuestionnairesRepository.getAllUncompleted()
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
        fetchUncompletedQuestionnaireRounds()
    }

    suspend fun onPrepoulateDatabase()
    {
        val days = 180
        List(days) { index ->
            NotificationsAvailable.allNotifications.forEach {
                val createdAt = it.time.minusDays(index.toLong())
                    .atZone(ZoneId.systemDefault())
                    .toEpochSecond() * 1000

                val pss = generatePSSEntry(createdAt)
                val phq = generatePHQEntry(createdAt)
                val ucla = generateUCLAEntry(createdAt)

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

    fun onNotificationWorker(context : Context)
    {
        val request = OneTimeWorkRequestBuilder<NotificationWorker>().build()
        WorkManager.getInstance(context).enqueue(request)
    }

    fun onUploadWorker(context: Context)
    {
        val request = OneTimeWorkRequestBuilder<UploadWorker>().build()
        WorkManager.getInstance(context).enqueue(request)
    }

    suspend fun onGetScore(): Int?
    {
        return remoteRepository.getScore()
    }

    suspend fun onPostUserData(): Boolean
    {
        return postUserDataUseCase.execute() == RemoteOperationResult.Success
    }

    fun onQueryWorkerStatus()
    {
        _state.value = DebugState.QueryWorkManager
        _workInfo = workAdministrator.queryWorkerStatus()
    }

    suspend fun onGetUID(): String =  appInfo.getUserID()

    suspend fun onResetUploadTimestamps()
    {
        for (type in LastUpload.Type.values())
        {
            lastUploadRepository.update(
                LastUpload(
                    type = type,
                    timestamp = 0,
                )
            )
        }
    }
}