package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.data.database.entity.daily.DailyDepression
import java.time.ZonedDateTime

interface DailyDepressionRepository : QuestionnaireRepository<DailyDepression>
{
    override suspend fun insert(element: DailyDepression): Long
    override suspend fun update(element: DailyDepression)
    override suspend fun get(id: Long): DailyDepression?
    override suspend fun getAll(): List<DailyDepression>
    override suspend fun getAllFromCurrentWeek(): List<DailyDepression>
    override suspend fun getAllFromLastSevenDays(): List<DailyDepression>
    override suspend fun getAllFromRange(range: Range<ZonedDateTime>): List<DailyDepression>
    override suspend fun getAllFromYesterday(): List<DailyDepression>
    override suspend fun getLastCompleted(): DailyDepression?
}