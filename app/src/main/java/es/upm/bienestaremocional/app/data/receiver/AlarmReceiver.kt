package es.upm.bienestaremocional.app.data.receiver

import android.app.AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import es.upm.bienestaremocional.app.data.alarm.AlarmManager
import es.upm.bienestaremocional.app.domain.repository.questionnaire.QuestionnaireRoundReducedRepository
import es.upm.bienestaremocional.app.ui.notification.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * This receiver is called when an alarm is executed
 */
@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver()
{
    @Inject
    lateinit var alarmManager: AlarmManager

    @Inject
    lateinit var notification: Notification

    @Inject
    lateinit var questionnaireRoundReducedRepository: QuestionnaireRoundReducedRepository

    @Inject
    @Named("logTag")
    lateinit var logTag: String


    override fun onReceive(context: Context, intent: Intent)
    {
        when (intent.action)
        {
            ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED ->
            {
                //permission granted, so cancel previous and re-schedule all alarms
                alarmManager.cancelAlarms()
                alarmManager.reScheduleAlarms()
            }
            "alarm" ->
            {
                Log.d(logTag,"An alarm was triggered")
                intent.extras?.getInt("alarm_code")?.let { alarmManager.decodeAndSchedule(it) }
                CoroutineScope(Dispatchers.IO).launch {
                    val questionnaireRoundReduced = questionnaireRoundReducedRepository.insert()
                    notification.showQuestionnaireNotification(questionnaireRoundReduced)
                }
            }
        }
    }
}