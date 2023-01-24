package es.upm.bienestaremocional.app.data.questionnaire

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R

enum class Questionnaire(val id: String, val labelRes: Int)
{
    PHQ9("phq9",R.string.phq9_label),
    UCLA("ucla",R.string.ucla_label);

    companion object
    {
        /**
         * Get all [Questionnaire]
         * @return [List] of [Questionnaire]
         */
        fun get(): List<Questionnaire> = values().asList()

        /**
         * Get all labels of the [Questionnaire] possible values
         * @return [List] of [String] with the labels
         */
        @Composable
        fun getLabels(): List<String> = get().map { stringResource(id = it.labelRes)  }

        /**
         * Obtain a Questionnaire from its id, or null if the string doesn't match a Questionnaire
         * @param id to decode
         */
        fun decode(id: String): Questionnaire? =
            when(id)
            {
                PHQ9.id -> PHQ9
                UCLA.id -> UCLA
                else -> null
            }
    }
}