package es.upm.bienestaremocional.app.data.questionnaire

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
 * @param questionRes: ArrayResource with the questions available in the questionnaire
 * @param answerRes: ArrayResource with the answers available in the questionnaire
 * @param numberOfQuestions: Number of the questions in the questionnaire
 * @param numberOfAnswers: Number of the answers in the questionnaire
 * @param questionScoreOffset: Offset of the score of each question of the answer
 * @param questionsWithInvertedScore: Set with the indexes of the inverted score questions
 * @param levels: List of the ScoreLevel of the questionnaire
 */
enum class Questionnaire(val id: String,
                         val mandatory : Boolean,
                         @StringRes val labelRes: Int,
                         @ArrayRes val questionRes: Int,
                         @ArrayRes val answerRes: Int,
                         val numberOfQuestions : Int,
                         val numberOfAnswers : Int,
                         val questionScoreOffset : Int,
                         val questionsWithInvertedScore : Set<Int> = setOf(),
                         val levels: List<ScoreLevel>,
                         val maxScore : Int
)
{
    PSS(id = "pss",
        mandatory = true,
        labelRes = R.string.pss_label,
        questionRes = R.array.pss_questions,
        answerRes = R.array.five_answers_questionnaire,
        numberOfQuestions = 10,
        numberOfAnswers = 5,
        questionScoreOffset = 0,
        questionsWithInvertedScore = setOf(3,4,6,7),
        levels = listOf(
            ScoreLevel(0,13,LevelLabel.Low),
            ScoreLevel(14,26,LevelLabel.Moderate),
            ScoreLevel(27,40,LevelLabel.High),
        ),
        maxScore = 40
    ),
    PHQ(id = "phq",
        mandatory = false,
        labelRes = R.string.phq_label,
        questionRes = R.array.phq_questions,
        answerRes = R.array.four_answers_questionnaire,
        numberOfQuestions = 9,
        numberOfAnswers = 4,
        questionScoreOffset = 0,
        levels = listOf(
            ScoreLevel(0,4,LevelLabel.Minimal),
            ScoreLevel(5,9,LevelLabel.Mild),
            ScoreLevel(10,14,LevelLabel.Moderate),
            ScoreLevel(15,19,LevelLabel.ModeratelySevere),
            ScoreLevel(20,27,LevelLabel.Severe),
        ),
        maxScore = 27
    ),
    UCLA(id = "ucla",
        mandatory = false,
        labelRes = R.string.ucla_label,
        questionRes = R.array.ucla_questions,
        answerRes = R.array.four_answers_questionnaire,
        numberOfQuestions = 20,
        numberOfAnswers = 4,
        questionScoreOffset = 1,
        questionsWithInvertedScore = setOf(0, 4, 5, 8, 9, 14, 15, 18, 19),
        levels = listOf(
            ScoreLevel(20,40,LevelLabel.Low),
            ScoreLevel(40,60,LevelLabel.Moderate),
            ScoreLevel(60,80,LevelLabel.High),
        ),
        maxScore = 80
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