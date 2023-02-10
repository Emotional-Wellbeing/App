package es.upm.bienestaremocional.app.data.alarm

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R

/**
 * Enum with the options of frequency of alarms that user could select with the associated alarms
 */
enum class AlarmsFrequency(@StringRes val labelRes: Int, val alarmItems: List<AlarmItem>)
{
    NIGHT_ALARM(R.string.questionnaire_only_night,listOf(AlarmsAvailable.nightAlarm)),
    NIGHT_LUNCH_ALARM(
        R.string.questionnaire_night_lunch,
        listOf(
            AlarmsAvailable.nightAlarm,
            AlarmsAvailable.lunchAlarm
        )
    );

    companion object
    {
        /**
         * Get all [AlarmsFrequency] possible values
         * @return [List] of [AlarmsFrequency] with the values
         */
        fun get(): List<AlarmsFrequency> = values().asList()

        /**
         * Get all labels of the [AlarmsFrequency] possible values
         * @return [List] of [String] with the labels
         */
        @Composable
        fun getLabels(): List<String> = get().map { stringResource(id = it.labelRes)  }
    }
}