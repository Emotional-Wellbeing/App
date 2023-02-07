package es.upm.bienestaremocional.app.data.questionnaire

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R

enum class Questionnaire(val id: String,
                         val mandatory : Boolean,
                         val labelRes: Int,
                         val questionRes: Int,
                         val answerRes: Int,
                         val numberOfQuestions : Int,
                         val numberOfAnswers : Int,
                         val questionsWithInvertedScore : Set<Int>,
                         val questionScoreOffset : Int,
)
{
    PSS("pss",
        true,
        R.string.pss_label,
        R.array.pss_questions,
        R.array.five_answers_questionnaire,
        10,
        5,
        setOf(3,4,5,6),
        0
    ),
    PHQ("phq",
        false,
        R.string.phq_label,
        R.array.phq_questions,
        R.array.four_answers_questionnaire,
        9,
        4,
        setOf(),
        0,
    ),
    UCLA("ucla",
        false,
        R.string.ucla_label,
        R.array.ucla_questions,
        R.array.four_answers_questionnaire,
        20,
        4,
        setOf(0, 4, 5, 8, 9, 14, 15, 18, 19),
        1
    );

    companion object
    {
        /**
         * Get all [Questionnaire]
         * @return [List] of [Questionnaire]
         */
        fun get(): List<Questionnaire> = values().asList()

        /**
         * Get all [Questionnaire] that aren't mandatory
         * @return [List] of [Questionnaire]
         */
        fun getOptional(): List<Questionnaire> = values().filter { !it.mandatory }

        /**
         * Get all labels of the [Questionnaire] possible values
         * @return [List] of [String] with the labels
         */
        @Composable
        fun getLabels(): List<String> = get().map { stringResource(id = it.labelRes)  }

        /**
         * Get all labels of the optional [Questionnaire] possible values
         * @return [List] of [String] with the labels
         */
        @Composable
        fun getOptionalLabels(): List<String> {
            val aux = getOptional().map { stringResource(id = it.labelRes)  }
            Log.d("hola","adios")
            return aux
        }

        /**
         * Obtain a Questionnaire from its id, or null if the string doesn't match a Questionnaire
         * @param id to decode
         */
        fun decode(id: String): Questionnaire? =
            when(id)
            {
                PHQ.id -> PHQ
                UCLA.id -> UCLA
                else -> null
            }
    }
}