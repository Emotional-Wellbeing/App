package es.upm.bienestaremocional.app.data.alarm

interface AlarmManager
{
    fun cancelAlarms()
    fun reScheduleAlarms()
    fun decodeAndSchedule(id: Int)
}