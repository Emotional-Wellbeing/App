package es.upm.bienestaremocional.data.questionnaire

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

interface QuestionnaireDrawableStringAnswers : Questionnaire {
    @get:StringRes
    val measureRes: Int
    @get:ArrayRes
    val questionRes: Int
    @get:ArrayRes
    val answerRes: Array<Int>
}