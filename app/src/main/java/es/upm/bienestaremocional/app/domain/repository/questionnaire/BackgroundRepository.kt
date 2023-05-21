package es.upm.bienestaremocional.app.domain.repository.questionnaire

import android.util.Range
import es.upm.bienestaremocional.app.data.database.entity.BackgroundData
import es.upm.bienestaremocional.app.data.database.entity.BackgroundDataEntity
import es.upm.bienestaremocional.app.data.database.entity.PHQ
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireEntity
import java.time.LocalDate

/**
 * Repository to interact with [PHQ] entity.
 * Delete operation is not present by design.
 */
interface BackgroundRepository<T>  where T : BackgroundDataEntity
{
    fun insert(element: T) : Long
}