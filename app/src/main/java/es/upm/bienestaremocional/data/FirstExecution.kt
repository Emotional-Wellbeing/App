package es.upm.bienestaremocional.data

import android.app.NotificationManager
import es.upm.bienestaremocional.data.database.entity.LastUpload
import es.upm.bienestaremocional.data.notification.NotificationChannels
import es.upm.bienestaremocional.data.notification.createNotificationChannel
import es.upm.bienestaremocional.data.worker.WorkAdministrator
import es.upm.bienestaremocional.domain.repository.LastUploadRepository
import es.upm.bienestaremocional.utils.obtainTimestamp
import java.time.Instant

suspend fun firstTimeExecution(
    notificationManager: NotificationManager,
    workAdministrator: WorkAdministrator,
    lastUploadRepository: LastUploadRepository,
) {
    //build channel notifications
    for (appChannel in NotificationChannels.values())
        createNotificationChannel(
            notificationManager = notificationManager,
            channel = appChannel
        )
    //schedule notifications
    workAdministrator.scheduleDailyMorningNotificationWorker()
    workAdministrator.scheduleDailyNightNotificationWorker()
    workAdministrator.scheduleOneOffNotificationWorker()
    workAdministrator.scheduleUploadWorker()
    workAdministrator.scheduleUploadPhoneDataWorker()
    workAdministrator.scheduleUploadTrafficDataWorker()


    //insert values in last upload table
    val now = obtainTimestamp(Instant.now(), null)

    for (type in LastUpload.Type.values()) {
        lastUploadRepository.insert(
            LastUpload(
                type = type,
                timestamp = now
            )
        )
    }
}