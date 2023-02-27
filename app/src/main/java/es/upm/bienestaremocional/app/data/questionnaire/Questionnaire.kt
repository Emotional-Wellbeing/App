package es.upm.bienestaremocional.app.data.questionnaire

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R

enum class Questionnaire(val id: String,
                         val mandatory : Boolean,
                         @StringRes val labelRes: Int,
                         @ArrayRes val questionRes: Int,
                         @ArrayRes val answerRes: Int,
                         val numberOfQuestions : Int,
                         val questionScoreOffset : Int,
                         val questionsWithInvertedScore : Set<Int> = setOf(),
                         val levels: List<ScoreLevel>
)
{
    PSS(id = "pss",
        mandatory = true,
        labelRes = R.string.pss_label,
        questionRes = R.array.pss_questions,
        answerRes = R.array.five_answers_questionnaire,
        numberOfQuestions = 10,
        //5,
        questionScoreOffset = 0,
        questionsWithInvertedScore = setOf(3,4,5,6),
        levels = listOf(
            ScoreLevel(0,13,"low",R.string.low),
            ScoreLevel(14,26,"moderate",R.string.moderate),
            ScoreLevel(27,40,"high",R.string.high),
        )
    ),
    PHQ(id = "phq",
        mandatory = false,
        labelRes = R.string.phq_label,
        questionRes = R.array.phq_questions,
        answerRes = R.array.four_answers_questionnaire,
        numberOfQuestions = 9,
        //4,
        questionScoreOffset = 0,
        levels = listOf(
            ScoreLevel(0,4,"minimal",R.string.minimal),
            ScoreLevel(5,9,"mild",R.string.mild),
            ScoreLevel(10,14,"moderate",R.string.moderate),
            ScoreLevel(15,19,"moderate_severe",R.string.moderately_severe),
            ScoreLevel(20,27,"severe",R.string.severe),
        )
    ),
    UCLA(id = "ucla",
        mandatory = false,
        labelRes = R.string.ucla_label,
        questionRes = R.array.ucla_questions,
        answerRes = R.array.four_answers_questionnaire,
        numberOfQuestions = 20,
        //4,
        questionScoreOffset =1,
        questionsWithInvertedScore = setOf(0, 4, 5, 8, 9, 14, 15, 18, 19),
        levels = listOf(
            ScoreLevel(20,40,"low",R.string.low),
            ScoreLevel(40,60,"moderate",R.string.moderate),
            ScoreLevel(60,80,"high",R.string.high),
        )
    );

    companion object
    {
        /**
         * Get all [Questionnaire]
         * @return [List] of [Questionnaire]
         */
        fun get(): List<Questionnaire> = values().asList()

        /**
         * Get all [Questionnaire] that are mandatory
         * @return [List] of [Questionnaire]
         */
        fun getMandatory(): List<Questionnaire> = values().filter { it.mandatory }

        /**
         * Get all [Questionnaire] that aren't mandatory
         * @return [List] of [Questionnaire]
         */
        fun getOptional(): List<Questionnaire> = values().filter { !it.mandatory }

        /**
         * Get all labels of the optional [Questionnaire] possible values
         * @return [List] of [String] with the labels
         */
        @Composable
        fun getOptionalLabels(): List<String> {
            return getOptional().map { stringResource(id = it.labelRes) }
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