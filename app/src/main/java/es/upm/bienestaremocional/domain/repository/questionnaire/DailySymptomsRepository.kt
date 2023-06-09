package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.data.database.entity.daily.DailySymptoms
import java.time.ZonedDateTime

interface DailySymptomsRepository : QuestionnaireRepository<DailySymptoms>
{
    override suspend fun insert(element: DailySymptoms): Long
    override suspend fun update(element: DailySymptoms)
    override suspend fun get(id: Long): DailySymptoms?
    override suspend fun getAll(): List<DailySymptoms>
    override suspend fun getAllFromCurrentWeek(): List<DailySymptoms>
    override suspend fun getAllFromLastSevenDays(): List<DailySymptoms>
    override suspend fun getAllFromRange(range: Range<ZonedDateTime>): List<DailySymptoms>
    override suspend fun getAllFromYesterday(): List<DailySymptoms>
    override suspend fun getLastCompleted(): DailySymptoms?
}