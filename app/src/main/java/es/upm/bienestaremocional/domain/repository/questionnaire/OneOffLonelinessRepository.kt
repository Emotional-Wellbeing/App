package es.upm.bienestaremocional.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffLoneliness
import java.time.ZonedDateTime

interface OneOffLonelinessRepository : QuestionnaireRepository<OneOffLoneliness> {
    override suspend fun insert(element: OneOffLoneliness): Long
    override suspend fun update(element: OneOffLoneliness)
    override suspend fun get(id: Long): OneOffLoneliness?
    override suspend fun getAll(): List<OneOffLoneliness>
    override suspend fun getAllFromCurrentWeek(): List<OneOffLoneliness>
    override suspend fun getAllFromLastSevenDays(): List<OneOffLoneliness>
    override suspend fun getAllFromRange(
        range: Range<ZonedDateTime>,
        onlyCompleted: Boolean
    ): List<OneOffLoneliness>

    override suspend fun getAllFromYesterday(): List<OneOffLoneliness>
    override suspend fun getLastCompleted(): OneOffLoneliness?
}