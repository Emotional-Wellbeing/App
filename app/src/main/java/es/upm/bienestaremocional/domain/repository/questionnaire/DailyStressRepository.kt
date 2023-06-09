package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.data.database.entity.daily.DailyStress
import java.time.ZonedDateTime

interface DailyStressRepository : QuestionnaireRepository<DailyStress>
{
    override suspend fun insert(element: DailyStress): Long
    override suspend fun update(element: DailyStress)
    override suspend fun get(id: Long): DailyStress?
    override suspend fun getAll(): List<DailyStress>
    override suspend fun getAllFromCurrentWeek(): List<DailyStress>
    override suspend fun getAllFromLastSevenDays(): List<DailyStress>
    override suspend fun getAllFromRange(range: Range<ZonedDateTime>): List<DailyStress>
    override suspend fun getAllFromYesterday(): List<DailyStress>
    override suspend fun getLastCompleted(): DailyStress?
}