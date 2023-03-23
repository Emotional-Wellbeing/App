package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.app.data.database.entity.PHQ
import java.time.LocalDate

/**
 * Repository to interact with [PHQ] entity.
 * Delete operation is not present by design.
 */
interface PHQRepository: QuestionnaireRepository<PHQ>
{
    override suspend fun insert(element: PHQ) : Long
    override suspend fun update(element: PHQ)
    override suspend fun getAll(): List<PHQ>
    override suspend fun getAllFromLastSevenDays(): List<PHQ>
    override suspend fun getAllFromRange(range: Range<LocalDate>): List<PHQ>
    override suspend fun getAllCompleted(): List<PHQ>
    override suspend fun get(id: Long): PHQ?
}