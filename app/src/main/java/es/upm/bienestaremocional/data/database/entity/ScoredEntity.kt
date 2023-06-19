package es.upm.bienestaremocional.data.database.entity

/**
 * Contains data related with score, extending [MeasureEntity]
 */
interface ScoredEntity : MeasureEntity {
    var score: Int?
    var scoreLevel: String?
}