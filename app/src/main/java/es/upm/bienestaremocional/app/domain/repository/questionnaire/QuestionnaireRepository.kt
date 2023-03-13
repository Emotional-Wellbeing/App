package es.upm.bienestaremocional.app.domain.repository.questionnaire

import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireEntity

interface QuestionnaireRepository<T> where T : QuestionnaireEntity
{
    suspend fun insert(element: T) : Long
    suspend fun update(element: T)
    suspend fun getAll(): List<T>
    suspend fun getAllFromLastSevenDays(): List<T>
    suspend fun getAllCompleted(): List<T>
    suspend fun get(id: Long): T?
}