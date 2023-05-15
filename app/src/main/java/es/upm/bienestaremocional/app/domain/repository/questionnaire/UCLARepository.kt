package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.app.data.database.entity.UCLA
import java.time.LocalDate

/**
 * Repository to interact with [UCLA] entity.
 * Delete operation is not present by design.
 */
interface UCLARepository: QuestionnaireRepository<UCLA>
{
    override suspend fun insert(element: UCLA) : Long
    override suspend fun update(element: UCLA)
    override suspend fun getAll(): List<UCLA>
    override suspend fun getAllFromLastSevenDays(): List<UCLA>
    override suspend fun getAllFromCurrentWeek() : List<UCLA>
    override suspend fun getAllFromYesterday(): List<UCLA>
    override suspend fun getAllFromRange(range: Range<LocalDate>): List<UCLA>
    override suspend fun getAllCompleted(): List<UCLA>
    override suspend fun getLastCompleted(): UCLA?
    override suspend fun get(id: Long): UCLA?
}