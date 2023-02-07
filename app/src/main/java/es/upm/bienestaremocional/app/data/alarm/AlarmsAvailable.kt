package es.upm.bienestaremocional.app.data.alarm

import es.upm.bienestaremocional.app.data.settings.LUNCH_ALARM_CODE
import es.upm.bienestaremocional.app.data.settings.NIGHT_ALARM_CODE
import java.time.LocalTime
import java.time.format.DateTimeFormatter


/**
 * Object with the alarms that could be executed
 */
object AlarmsAvailable
{
    private val dtf = DateTimeFormatter.ofPattern("HH:mm")

    val nightAlarm = AlarmItem(LocalTime.parse("21:00", dtf), NIGHT_ALARM_CODE)
    val lunchAlarm = AlarmItem(LocalTime.parse("14:00", dtf), LUNCH_ALARM_CODE)

    //this is used for cancel all alarms before set new alarms
    val allAlarms = listOf(nightAlarm, lunchAlarm)
}

