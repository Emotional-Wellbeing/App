package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.data.database.entity.daily.DailyLoneliness
import java.time.ZonedDateTime

interface DailyLonelinessRepository : QuestionnaireRepository<DailyLoneliness> {
    override suspend fun insert(element: DailyLoneliness): Long
    override suspend fun update(element: DailyLoneliness)
    override suspend fun get(id: Long): DailyLoneliness?
    override suspend fun getAll(): List<DailyLoneliness>
    override suspend fun getAllFromCurrentWeek(): List<DailyLoneliness>
    override suspend fun getAllFromLastSevenDays(): List<DailyLoneliness>
    override suspend fun getAllFromRange(
        range: Range<ZonedDateTime>,
        onlyCompleted: Boolean
    ): List<DailyLoneliness>

    override suspend fun getAllFromYesterday(): List<DailyLoneliness>
    override suspend fun getLastElement(): DailyLoneliness?
}