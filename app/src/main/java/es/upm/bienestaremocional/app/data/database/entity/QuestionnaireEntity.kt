package es.upm.bienestaremocional.app.data.database.entity

abstract class QuestionnaireEntity
{
    abstract val id: Long
    abstract val createdAt: Long
    abstract var modifiedAt: Long
    abstract var score: Int?
    abstract var scoreLevel: String?
    abstract var completed: Boolean
}