package es.upm.bienestaremocional.data.questionnaire

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

/**
 * Contain the drawable resources of the questionnaires that have numeric answers
 */
interface QuestionnaireDrawableNumericAnswers : Questionnaire {
    @get:StringRes
    val measureRes: Int

    @get:ArrayRes
    val questionRes: Int
}