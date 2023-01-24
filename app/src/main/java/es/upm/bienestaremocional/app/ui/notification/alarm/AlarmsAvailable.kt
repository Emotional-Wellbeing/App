package es.upm.bienestaremocional.app.ui.notification.alarm

import es.upm.bienestaremocional.app.ui.notification.LUNCH_ALARM_CODE
import es.upm.bienestaremocional.app.ui.notification.NIGHT_ALARM_CODE
import java.time.LocalTime
import java.time.format.DateTimeFormatter


/**
 * Object with the alarms that could be executed
 */
object AlarmsAvailable
{
    private val dtf = DateTimeFormatter.ofPattern("HH:mm")

    val nightAlarm = Alarm(LocalTime.parse("21:00", dtf), NIGHT_ALARM_CODE)
    val lunchAlarm = Alarm(LocalTime.parse("14:00",dtf), LUNCH_ALARM_CODE)

    //this is used for cancel all alarms before set new alarms
    val allAlarms = listOf(nightAlarm, lunchAlarm)
}

