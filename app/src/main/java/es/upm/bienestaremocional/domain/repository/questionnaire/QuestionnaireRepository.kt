package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import java.time.ZonedDateTime

interface QuestionnaireRepository<T> where T : MeasureEntity
{
    suspend fun insert(element: T) : Long
    suspend fun update(element: T)
    suspend fun get(id: Long): T?
    suspend fun getAll(): List<T>
    suspend fun getAllFromCurrentWeek() : List<T>
    suspend fun getAllFromLastSevenDays(): List<T>
    suspend fun getAllFromRange(range: Range<ZonedDateTime>, onlyCompleted: Boolean): List<T>
    suspend fun getAllFromYesterday(): List<T>
    suspend fun getLastElement(): T?
}