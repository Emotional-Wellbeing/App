package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import java.time.ZonedDateTime

interface DailySuicideRepository : QuestionnaireRepository<DailySuicide>
{
    override suspend fun insert(element: DailySuicide): Long
    override suspend fun update(element: DailySuicide)
    override suspend fun get(id: Long): DailySuicide?
    override suspend fun getAll(): List<DailySuicide>
    override suspend fun getAllFromCurrentWeek(): List<DailySuicide>
    override suspend fun getAllFromLastSevenDays(): List<DailySuicide>
    override suspend fun getAllFromRange(range: Range<ZonedDateTime>): List<DailySuicide>
    override suspend fun getAllFromYesterday(): List<DailySuicide>
    override suspend fun getLastCompleted(): DailySuicide?
}