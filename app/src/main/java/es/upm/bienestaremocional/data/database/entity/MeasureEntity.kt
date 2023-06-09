package es.upm.bienestaremocional.data.database.entity

interface MeasureEntity
{
    val id: Long
    val createdAt: Long
    var modifiedAt: Long
    var completed: Boolean
}