package es.upm.bienestaremocional.ui.screens.debug

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.Measure
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.LastUpload
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import es.upm.bienestaremocional.data.database.entity.round.DailyRoundFull
import es.upm.bienestaremocional.data.database.entity.round.OneOffRound
import es.upm.bienestaremocional.data.database.entity.round.OneOffRoundFull
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.questionnaire.generateDailyDepressionEntry
import es.upm.bienestaremocional.data.questionnaire.generateDailyLonelinessEntry
import es.upm.bienestaremocional.data.questionnaire.generateDailyStressEntry
import es.upm.bienestaremocional.data.questionnaire.generateDailySuicideEntry
import es.upm.bienestaremocional.data.questionnaire.generateDailySymptomsEntry
import es.upm.bienestaremocional.data.questionnaire.generateOneOffDepressionEntry
import es.upm.bienestaremocional.data.questionnaire.generateOneOffLonelinessEntry
import es.upm.bienestaremocional.data.questionnaire.generateOneOffStressEntry
import es.upm.bienestaremocional.data.worker.DailyMorningNotificationWorker
import es.upm.bienestaremocional.data.worker.DailyNightNotificationWorker
import es.upm.bienestaremocional.data.worker.OneOffNotificationWorker
import es.upm.bienestaremocional.data.worker.UploadWorker
import es.upm.bienestaremocional.data.worker.WorkAdministrator
import es.upm.bienestaremocional.domain.repository.LastUploadRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyRoundFullRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffRoundFullRepository
import es.upm.bienestaremocional.domain.repository.remote.RemoteOperationResult
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepository
import es.upm.bienestaremocional.domain.usecases.InsertDailyRoundUseCase
import es.upm.bienestaremocional.domain.usecases.InsertOneOffRoundUseCase
import es.upm.bienestaremocional.domain.usecases.PostUserDataUseCase
import es.upm.bienestaremocional.ui.notification.Notification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(
    private val notification: Notification,
    private val dailyRoundFullRepository: DailyRoundFullRepository,
    private val oneOffRoundFullRepository: OneOffRoundFullRepository,
    private val appDAO: AppDAO,
    private val remoteRepository: RemoteRepository,
    private val workAdministrator: WorkAdministrator,
    private val appInfo: AppInfo,
    private val lastUploadRepository: LastUploadRepository,
    private val postUserDataUseCase: PostUserDataUseCase,
    private val insertDailyRoundUseCase: InsertDailyRoundUseCase,
    private val insertOneOffRoundUseCase: InsertOneOffRoundUseCase
): ViewModel()
{
    //state
    private val _state = MutableStateFlow<DebugState>(DebugState.ShowOptions)
    val state: StateFlow<DebugState> = _state.asStateFlow()

    //data
    private val _dailyRoundsUncompleted = MutableLiveData<List<DailyRoundFull>>()
    val dailyRoundsUncompleted: LiveData<List<DailyRoundFull>>
        get() = _dailyRoundsUncompleted
    private val _dailyRounds = MutableLiveData<List<DailyRoundFull>>()
    val dailyRounds: LiveData<List<DailyRoundFull>>
        get() = _dailyRounds

    private val _oneOffRoundsUncompleted = MutableLiveData<List<OneOffRoundFull>>()
    val oneOffRoundsUncompleted: LiveData<List<OneOffRoundFull>>
        get() = _oneOffRoundsUncompleted
    private val _oneOffRounds = MutableLiveData<List<OneOffRoundFull>>()
    val oneOffRounds: LiveData<List<OneOffRoundFull>>
        get() = _oneOffRounds


    private var _workInfo : LiveData<List<WorkInfo>> = MutableLiveData()
    val workInfo: LiveData<List<WorkInfo>>
        get() = _workInfo

    private fun fetchUncompletedQuestionnaireRounds()
    {
        viewModelScope.launch {
            _dailyRoundsUncompleted.value = dailyRoundFullRepository.getAllUncompleted()
            _oneOffRoundsUncompleted.value = oneOffRoundFullRepository.getAllUncompleted()
        }
    }

    private fun fetchAllQuestionnaireRounds()
    {
        viewModelScope.launch {
            _dailyRounds.value = dailyRoundFullRepository.getAll()
            _oneOffRounds.value = oneOffRoundFullRepository.getAll()
        }
    }

    fun onDailyMorningNotification()
    {
        viewModelScope.launch {
            val dailyMorningRound = insertDailyRoundUseCase.insertDailyMorningRound()
            notification.showDailyMorningRoundNotification(dailyMorningRound)
        }
    }

    fun onDailyNightNotification()
    {
        viewModelScope.launch {
            val dailyNightRound = insertDailyRoundUseCase.insertDailyNightRound()
            notification.showDailyNightRoundNotification(dailyNightRound)
        }
    }

    fun onOneOffNotification()
    {
        viewModelScope.launch {
            val oneOffRound = insertOneOffRoundUseCase.insertOneOffRound()
            notification.showOneOffRoundNotification(oneOffRound)
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
            val createdAt = ZonedDateTime
                .now()
                .minusDays(index.toLong())
                .toEpochSecond() * 1000

            //Insert daily morning round
            val dailyMorningMeasures = Measure.get().filter {
                it.frequency != Measure.Frequency.OnlyDailyAtNight
            }

            var dailyMorningStressId : Long? = null
            var dailyMorningDepressionId : Long? = null
            var dailyMorningLonelinessId : Long? = null
            var dailyMorningSuicideId : Long? = null
            var dailyMorningSymptomsId : Long? = null

            dailyMorningMeasures.forEach {
                when(it)
                {
                    Measure.Stress -> {
                        dailyMorningStressId = appDAO.insert(
                            generateDailyStressEntry(createdAt)
                        )
                    }
                    Measure.Depression -> {
                        dailyMorningDepressionId = appDAO.insert(
                            generateDailyDepressionEntry(createdAt)
                        )
                    }
                    Measure.Loneliness -> {
                        dailyMorningLonelinessId = appDAO.insert(
                            generateDailyLonelinessEntry(createdAt)
                        )
                    }
                    Measure.Suicide -> {
                        dailyMorningSuicideId = appDAO.insert(
                            generateDailySuicideEntry(createdAt)
                        )
                    }
                    Measure.Symptoms -> {
                        dailyMorningSymptomsId = appDAO.insert(
                            generateDailySymptomsEntry(createdAt)
                        )
                    }
                }
            }

            appDAO.insert(
                DailyRound(
                    createdAt = createdAt,
                    moment = DailyRound.Moment.Morning,
                    stressId = dailyMorningStressId,
                    depressionId = dailyMorningDepressionId,
                    lonelinessId = dailyMorningLonelinessId,
                    suicideId = dailyMorningSuicideId,
                    symptomsId = dailyMorningSymptomsId
                )
            )

            //Insert daily morning round
            val dailyNightMeasures = Measure.get()

            var dailyNightStressId : Long? = null
            var dailyNightDepressionId : Long? = null
            var dailyNightLonelinessId : Long? = null
            var dailyNightSuicideId : Long? = null
            var dailyNightSymptomsId : Long? = null

            dailyNightMeasures.forEach {
                when(it)
                {
                    Measure.Stress -> {
                        dailyNightStressId = appDAO.insert(
                            generateDailyStressEntry(createdAt)
                        )
                    }
                    Measure.Depression -> {
                        dailyNightDepressionId = appDAO.insert(
                            generateDailyDepressionEntry(createdAt)
                        )
                    }
                    Measure.Loneliness -> {
                        dailyNightLonelinessId = appDAO.insert(
                            generateDailyLonelinessEntry(createdAt)
                        )
                    }
                    Measure.Suicide -> {
                        dailyNightSuicideId = appDAO.insert(
                            generateDailySuicideEntry(createdAt)
                        )
                    }
                    Measure.Symptoms -> {
                        dailyNightSymptomsId = appDAO.insert(
                            generateDailySymptomsEntry(createdAt)
                        )
                    }
                }
            }

            appDAO.insert(
                DailyRound(
                    createdAt = createdAt,
                    moment = DailyRound.Moment.Night,
                    stressId = dailyNightStressId,
                    depressionId = dailyNightDepressionId,
                    lonelinessId = dailyNightLonelinessId,
                    suicideId = dailyNightSuicideId,
                    symptomsId = dailyNightSymptomsId
                )
            )

            //Insert one off round
            if(index.toLong() % OneOffNotificationWorker.repeatInterval.toDays() == 0L)
            {
                val oneOffStress = generateOneOffStressEntry(createdAt)
                val oneOffDepression = generateOneOffDepressionEntry(createdAt)
                val oneOffLoneliness = generateOneOffLonelinessEntry(createdAt)

                val stressId = appDAO.insert(oneOffStress)
                val depressionId = appDAO.insert(oneOffDepression)
                val lonelinessId = appDAO.insert(oneOffLoneliness)

                appDAO.insert(
                    OneOffRound(
                        createdAt = createdAt,
                        stressId = stressId,
                        depressionId = depressionId,
                        lonelinessId  = lonelinessId
                    )
                )
            }
        }
    }

    suspend fun onDeleteDatabase()
    {
        appDAO.nukeDatabase()
    }

    fun onDailyMorningNotificationWorker(context : Context)
    {
        val request = OneTimeWorkRequestBuilder<DailyMorningNotificationWorker>().build()
        WorkManager.getInstance(context).enqueue(request)
    }

    fun onDailyNightNotificationWorker(context : Context)
    {
        val request = OneTimeWorkRequestBuilder<DailyNightNotificationWorker>().build()
        WorkManager.getInstance(context).enqueue(request)
    }

    fun onOneOffNotificationWorker(context : Context)
    {
        val request = OneTimeWorkRequestBuilder<OneOffNotificationWorker>().build()
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