package es.upm.bienestaremocional.data.database.entity

/**
 * Contains the essential data on a measure entity
 */
interface MeasureEntity {
    val id: Long
    val createdAt: Long
    var modifiedAt: Long
    var completed: Boolean
}