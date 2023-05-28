package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.data.database.entity.PHQ
import java.time.ZonedDateTime

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
    override suspend fun getAllFromCurrentWeek() : List<PHQ>
    override suspend fun getAllFromYesterday(): List<PHQ>
    override suspend fun getAllFromRange(range: Range<ZonedDateTime>): List<PHQ>
    override suspend fun getAllCompleted(): List<PHQ>
    override suspend fun getLastCompleted(): PHQ?
    override suspend fun get(id: Long): PHQ?
}