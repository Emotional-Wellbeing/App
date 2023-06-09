package es.upm.bienestaremocional.data.database.entity

interface ScoredEntity : MeasureEntity
{
    var score: Int?
    var scoreLevel: String?
}