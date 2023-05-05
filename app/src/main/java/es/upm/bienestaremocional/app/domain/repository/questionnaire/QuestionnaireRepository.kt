package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireEntity
import java.time.LocalDate

interface QuestionnaireRepository<T> where T : QuestionnaireEntity
{
    suspend fun insert(element: T) : Long
    suspend fun update(element: T)
    suspend fun getAll(): List<T>
    suspend fun getAllFromLastSevenDays(): List<T>
    suspend fun getAllFromYesterday(): List<T>
    suspend fun getAllFromRange(range: Range<LocalDate>): List<T>
    suspend fun getAllCompleted(): List<T>
    suspend fun getLastCompleted(): T?
    suspend fun get(id: Long): T?
}