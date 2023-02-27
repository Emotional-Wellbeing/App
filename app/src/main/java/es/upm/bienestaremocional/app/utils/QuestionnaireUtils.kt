package es.upm.bienestaremocional.app.utils

import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire

fun decodeScoreLevel(scoreLevel: String?, questionnaire: Questionnaire) : Int?
{
    var resource: Int? = null
    for (level in questionnaire.levels)
    {
        if(scoreLevel == level.internalLabel) {
            resource = level.label
            break
        }
    }
    return resource
}