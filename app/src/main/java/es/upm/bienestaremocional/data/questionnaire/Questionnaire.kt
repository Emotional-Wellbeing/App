package es.upm.bienestaremocional.data.questionnaire

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R

/**
 * Information of the available Questionnaire on the app
 * @param id: String to identify unequivocally the questionnaire
 * @param mandatory: Boolean to set if the user must do the questionnaire
 * @param labelRes: StringResource with the label to present the questionnaire
 * @param measureRes: StringResource with the label of the measure
 * @param questionRes: ArrayResource with the questions available in the questionnaire
 * @param answerRes: ArrayResource with the answers available in the questionnaire
 * @param numberOfQuestions: Number of the questions in the questionnaire
 * @param numberOfAnswers: Number of the answers in the questionnaire
 * @param questionScoreOffset: Offset of the score of each question of the answer
 * @param questionsWithInvertedScore: Set with the indexes of the inverted score questions
 * @param levels: List of the ScoreLevel of the questionnaire
 * @param minScore: Minimum possible score for the questionnaire
 * @param maxScore: Maximum possible score for the questionnaire
 */
enum class Questionnaire(val id: String,
                         val mandatory : Boolean,
                         @StringRes val labelRes: Int,
                         @StringRes val measureRes: Int,
                         @ArrayRes val questionRes: Int,
                         @ArrayRes val answerRes: Int,
                         val numberOfQuestions : Int,
                         val numberOfAnswers : Int,
                         val questionScoreOffset : Int,
                         val questionsWithInvertedScore : Set<Int> = setOf(),
                         val levels: List<ScoreLevel>,
                         val minScore : Int,
                         val maxScore : Int,
                         val advices : Map<Level,List<Int>>
)
{
    PSS(id = "pss",
        mandatory = true,
        labelRes = R.string.pss_label,
        measureRes = R.string.stress,
        questionRes = R.array.pss_questions,
        answerRes = R.array.five_answers_questionnaire,
        numberOfQuestions = 10,
        numberOfAnswers = 5,
        questionScoreOffset = 0,
        questionsWithInvertedScore = setOf(3,4,6,7),
        levels = listOf(
            ScoreLevel(0,13,Level.Low),
            ScoreLevel(14,26,Level.Moderate),
            ScoreLevel(27,40,Level.High),
        ),
        minScore = 0,
        maxScore = 40,
        advices = mapOf(
            Pair(Level.Low, listOf(R.string.low_stress_advice)),
            Pair(Level.Moderate, listOf(R.string.moderate_stress_advice)),
            Pair(Level.High, listOf(R.string.high_stress_advice)),
        )
    ),
    PHQ(id = "phq",
        mandatory = false,
        labelRes = R.string.phq_label,
        measureRes = R.string.depression,
        questionRes = R.array.phq_questions,
        answerRes = R.array.four_answers_questionnaire,
        numberOfQuestions = 9,
        numberOfAnswers = 4,
        questionScoreOffset = 0,
        levels = listOf(
            ScoreLevel(0,4,Level.Minimal),
            ScoreLevel(5,9,Level.Mild),
            ScoreLevel(10,14,Level.Moderate),
            ScoreLevel(15,19,Level.ModeratelySevere),
            ScoreLevel(20,27,Level.Severe),
        ),
        minScore = 0,
        maxScore = 27,
        advices = mapOf(
            Pair(Level.Minimal, listOf(R.string.minimal_depression_advice)),
            Pair(Level.Mild, listOf(R.string.mild_depression_advice)),
            Pair(Level.Moderate, listOf(R.string.moderate_depression_advice)),
            Pair(Level.ModeratelySevere, listOf(R.string.moderately_severe_depression_advice)),
            Pair(Level.Severe, listOf(R.string.severe_depression_advice)),
        )
    ),
    UCLA(id = "ucla",
        mandatory = false,
        labelRes = R.string.ucla_label,
        measureRes = R.string.loneliness,
        questionRes = R.array.ucla_questions,
        answerRes = R.array.four_answers_questionnaire,
        numberOfQuestions = 20,
        numberOfAnswers = 4,
        questionScoreOffset = 1,
        questionsWithInvertedScore = setOf(0, 4, 5, 8, 9, 14, 15, 18, 19),
        levels = listOf(
            ScoreLevel(20,40,Level.Low),
            ScoreLevel(41,60,Level.Moderate),
            ScoreLevel(61,80,Level.High),
        ),
        minScore = 20,
        maxScore = 80,
        advices = mapOf(
            Pair(Level.Low, listOf(R.string.low_loneliness_advice)),
            Pair(Level.Moderate, listOf(R.string.moderate_loneliness_advice)),
            Pair(Level.High, listOf(R.string.high_loneliness_advice)),
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
         * Get all labels of all [Questionnaire] possible values
         * @return [List] of [String] with the labels
         */
        @Composable
        fun getLabels(): List<String> =
            get().map { stringResource(id = it.labelRes) }

        /**
         * Get all labels of the optional [Questionnaire] possible values
         * @return [List] of [String] with the labels
         */
        @Composable
        fun getOptionalLabels(): List<String> =
            getOptional().map { stringResource(id = it.labelRes) }

        /**
         * Obtain a Questionnaire from its id, or null if the string doesn't match a Questionnaire
         * @param id to decode
         */
        fun decode(id: String): Questionnaire? =
            when(id)
            {
                PSS.id -> PSS
                PHQ.id -> PHQ
                UCLA.id -> UCLA
                else -> null
            }
    }
}