package es.upm.bienestaremocional.app.data.alarm

import es.upm.bienestaremocional.app.data.notification.LUNCH_ALARM_CODE
import es.upm.bienestaremocional.app.data.notification.NIGHT_ALARM_CODE
import java.time.LocalDateTime


/**
 * Object with the alarms that could be executed
 */
object AlarmsAvailable
{

    val nightAlarm = AlarmItem(LocalDateTime.now()
        .withHour(21)
        .withMinute(0)
        .withSecond(0)
        .withNano(0), NIGHT_ALARM_CODE
    )
    val lunchAlarm = AlarmItem(LocalDateTime.now()
        .withHour(14)
        .withMinute(0)
        .withSecond(0)
        .withNano(0), LUNCH_ALARM_CODE
    )

    //this is used for cancel all alarms before set new alarms
    val allAlarms = listOf(nightAlarm, lunchAlarm)

    /**
     * Obtain AlarmItem from the id present on the intent
     */
    fun decode(code: Int): AlarmItem?
    {
        return when(code)
        {
            nightAlarm.code -> nightAlarm
            lunchAlarm.code -> lunchAlarm
            else -> null
        }
    }
}

