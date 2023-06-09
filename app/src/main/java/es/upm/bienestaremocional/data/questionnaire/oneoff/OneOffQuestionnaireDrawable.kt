package es.upm.bienestaremocional.data.questionnaire.oneoff

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.QuestionnaireDrawableStringAnswers
import es.upm.bienestaremocional.data.questionnaire.QuestionnaireScored
import es.upm.bienestaremocional.data.questionnaire.ScoreLevel

/**
 * Information of the available Questionnaire on the app
 * @param measureRes: StringResource with the label of the measure
 * @param questionRes: ArrayResource with the questions available in the questionnaire
 * @param answerRes: ArrayResource with the answers available in the questionnaire
 * @param numberOfQuestions: Number of the questions in the questionnaire
 * @param numberOfAnswers: Number of the answers in the questionnaire
 * @param questionScoreOffset: Offset of the score of each question of the answer
 * @param questionsWithInvertedScore: Set with the indexes of the inverted score questions
 * @param levels: List of the ScoreLevel of the questionnaire
 */
enum class OneOffQuestionnaireDrawable(
    val numberOfAnswers : Int,
    val questionScoreOffset : Int = 0,
    val questionsWithInvertedScore : Set<Int> = setOf(),
    override val numberOfQuestions : Int,
    override val answerRange: IntRange,
    override val levels: List<ScoreLevel>,
    override val minScore: Int,
    override val maxScore: Int,
    @StringRes override val measureRes: Int,
    @ArrayRes override val questionRes: Int,
    @ArrayRes override val answerRes: Array<Int>,
) : QuestionnaireDrawableStringAnswers, QuestionnaireScored
{
    Stress(
        numberOfQuestions = OneOffQuestionnaire.Stress.numberOfQuestions,
        answerRange = OneOffQuestionnaire.Stress.answerRange,
        levels = OneOffQuestionnaire.Stress.levels,
        minScore = OneOffQuestionnaire.Stress.minScore,
        maxScore = OneOffQuestionnaire.Stress.maxScore,
        numberOfAnswers = OneOffQuestionnaire.Stress.numberOfQuestions,
        questionScoreOffset = OneOffQuestionnaire.Stress.questionScoreOffset,
        questionsWithInvertedScore = OneOffQuestionnaire.Stress.questionsWithInvertedScore,
        measureRes = R.string.stress,
        questionRes = R.array.one_off_stress_questions,
        answerRes = arrayOf(R.array.five_answers_questionnaire),
    ),
    Depression(
        numberOfQuestions = OneOffQuestionnaire.Depression.numberOfQuestions,
        answerRange = OneOffQuestionnaire.Depression.answerRange,
        levels = OneOffQuestionnaire.Depression.levels,
        minScore = OneOffQuestionnaire.Depression.minScore,
        maxScore = OneOffQuestionnaire.Depression.maxScore,
        numberOfAnswers = OneOffQuestionnaire.Depression.numberOfQuestions,
        questionScoreOffset = OneOffQuestionnaire.Depression.questionScoreOffset,
        questionsWithInvertedScore = OneOffQuestionnaire.Depression.questionsWithInvertedScore,
        measureRes = R.string.depression,
        questionRes = R.array.one_off_depression_questions,
        answerRes = arrayOf(R.array.four_answers_questionnaire),
    ),
    Loneliness(
        numberOfQuestions = OneOffQuestionnaire.Loneliness.numberOfQuestions,
        answerRange = OneOffQuestionnaire.Loneliness.answerRange,
        levels = OneOffQuestionnaire.Loneliness.levels,
        minScore = OneOffQuestionnaire.Loneliness.minScore,
        maxScore = OneOffQuestionnaire.Loneliness.maxScore,
        numberOfAnswers = OneOffQuestionnaire.Loneliness.numberOfQuestions,
        questionScoreOffset = OneOffQuestionnaire.Loneliness.questionScoreOffset,
        questionsWithInvertedScore = OneOffQuestionnaire.Loneliness.questionsWithInvertedScore,
        measureRes = R.string.loneliness,
        questionRes = R.array.one_off_loneliness_questions,
        answerRes = arrayOf(R.array.four_answers_questionnaire),
    );
}