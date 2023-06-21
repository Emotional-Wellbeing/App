package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffDepression
import java.time.ZonedDateTime

interface OneOffDepressionRepository : QuestionnaireRepository<OneOffDepression> {
    override suspend fun insert(element: OneOffDepression): Long
    override suspend fun update(element: OneOffDepression)
    override suspend fun get(id: Long): OneOffDepression?
    override suspend fun getAll(): List<OneOffDepression>
    override suspend fun getAllFromCurrentWeek(): List<OneOffDepression>
    override suspend fun getAllFromLastSevenDays(): List<OneOffDepression>
    override suspend fun getAllFromRange(
        range: Range<ZonedDateTime>,
        onlyCompleted: Boolean
    ): List<OneOffDepression>

    override suspend fun getAllFromYesterday(): List<OneOffDepression>
    override suspend fun getLastElement(): OneOffDepression?
}