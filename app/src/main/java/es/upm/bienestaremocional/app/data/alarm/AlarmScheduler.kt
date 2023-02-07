package es.upm.bienestaremocional.app.data.alarm

interface AlarmScheduler {
    fun schedule(alarm: AlarmItem)
    fun schedule(alarms: List<AlarmItem>)
    fun cancel(alarm: AlarmItem)
    fun cancel(alarms: List<AlarmItem>)
}