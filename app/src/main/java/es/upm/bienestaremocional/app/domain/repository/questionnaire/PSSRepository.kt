package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.app.data.database.entity.PSS
import java.time.LocalDate

/**
 * Repository to interact with [PSS] entity.
 * Delete operation is not present by design.
 */
interface PSSRepository : QuestionnaireRepository<PSS>
{
    override suspend fun insert(element: PSS) : Long
    override suspend fun update(element: PSS)
    override suspend fun getAll(): List<PSS>
    override suspend fun getAllFromLastSevenDays(): List<PSS>
    override suspend fun getAllFromRange(range: Range<LocalDate>): List<PSS>
    override suspend fun getAllCompleted(): List<PSS>
    override suspend fun get(id: Long): PSS?
}