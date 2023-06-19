package es.upm.bienestaremocional.data.questionnaire.daily

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.QuestionnaireDrawableNumericAnswers


/**
 * Extend [DailyScoredQuestionnaire] adding the drawable resources
 * @param numberOfQuestions: Number of the questions in the questionnaire
 * @param answerRange: Inclusive range of the valid responses on a answer
 * @param measureRes: StringResource with the label of the measure
 * @param questionRes: ArrayResource with the questions available in the questionnaire
 */
enum class DailyScoredQuestionnaireDrawable(
    override val numberOfQuestions: Int,
    override val answerRange: IntRange,
    @StringRes override val measureRes: Int,
    @ArrayRes override val questionRes: Int,
) : QuestionnaireDrawableNumericAnswers {
    MorningStress(
        numberOfQuestions = DailyScoredQuestionnaire.Stress.numberOfQuestions,
        answerRange = DailyScoredQuestionnaire.Stress.answerRange,
        measureRes = R.string.stress,
        questionRes = R.array.daily_morning_stress_questions,
    ),
    MorningDepression(
        numberOfQuestions = DailyScoredQuestionnaire.Depression.numberOfQuestions,
        answerRange = DailyScoredQuestionnaire.Depression.answerRange,
        measureRes = R.string.depression,
        questionRes = R.array.daily_morning_depression_questions,
    ),
    MorningLoneliness(
        numberOfQuestions = DailyScoredQuestionnaire.Loneliness.numberOfQuestions,
        answerRange = DailyScoredQuestionnaire.Loneliness.answerRange,
        measureRes = R.string.loneliness,
        questionRes = R.array.daily_morning_loneliness_questions,
    ),
    NightStress(
        numberOfQuestions = DailyScoredQuestionnaire.Stress.numberOfQuestions,
        answerRange = DailyScoredQuestionnaire.Stress.answerRange,
        measureRes = R.string.stress,
        questionRes = R.array.daily_night_stress_questions,
    ),
    NightDepression(
        numberOfQuestions = DailyScoredQuestionnaire.Depression.numberOfQuestions,
        answerRange = DailyScoredQuestionnaire.Depression.answerRange,
        measureRes = R.string.depression,
        questionRes = R.array.daily_night_depression_questions,
    ),
    NightLoneliness(
        numberOfQuestions = DailyScoredQuestionnaire.Loneliness.numberOfQuestions,
        answerRange = DailyScoredQuestionnaire.Loneliness.answerRange,
        measureRes = R.string.loneliness,
        questionRes = R.array.daily_night_loneliness_questions,
    );
}
