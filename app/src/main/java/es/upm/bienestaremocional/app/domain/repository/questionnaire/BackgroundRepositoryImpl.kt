package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Log
import android.util.Range
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.BackgroundData
import es.upm.bienestaremocional.app.data.database.entity.BackgroundDataEntity
import es.upm.bienestaremocional.app.data.database.entity.PHQ
import es.upm.bienestaremocional.app.domain.processing.getCurrentWeek
import es.upm.bienestaremocional.app.domain.processing.getLastSevenDays
import es.upm.bienestaremocional.app.domain.processing.getStartAndEndOfYesterday
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

/**
 * Implementation of [PHQRepository]
 * Ensures the establishment of the modifiedDate in update operations and logs all executions
 */
class BackgroundRepositoryImpl @Inject constructor(
    private val dao: AppDAO,
    private val logTag: String
){

    suspend fun insert(data: BackgroundData): Long {
        Log.d(logTag, "inserting new background data")
        return dao.insert(data)
    }

}