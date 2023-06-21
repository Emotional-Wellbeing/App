package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffStress
import java.time.ZonedDateTime

interface OneOffStressRepository : QuestionnaireRepository<OneOffStress> {
    override suspend fun insert(element: OneOffStress): Long
    override suspend fun update(element: OneOffStress)
    override suspend fun get(id: Long): OneOffStress?
    override suspend fun getAll(): List<OneOffStress>
    override suspend fun getAllFromCurrentWeek(): List<OneOffStress>
    override suspend fun getAllFromLastSevenDays(): List<OneOffStress>
    override suspend fun getAllFromRange(
        range: Range<ZonedDateTime>,
        onlyCompleted: Boolean
    ): List<OneOffStress>

    override suspend fun getAllFromYesterday(): List<OneOffStress>
    override suspend fun getLastCompleted(): OneOffStress?
}