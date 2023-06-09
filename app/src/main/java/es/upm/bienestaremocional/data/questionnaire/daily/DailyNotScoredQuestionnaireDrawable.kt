package es.upm.bienestaremocional.data.questionnaire.daily
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.questionnaire.QuestionnaireDrawableStringAnswers

/**
 * Information of the available Questionnaire on the app
 * @param questionRes: ArrayResource with the questions available in the questionnaire
 */
enum class DailyNotScoredQuestionnaireDrawable(override val numberOfQuestions: Int,
                                               override val answerRange: IntRange,
                                               @StringRes override val measureRes: Int,
                                               @ArrayRes override val questionRes: Int,
                                               @ArrayRes override val answerRes: Array<Int>,
) : QuestionnaireDrawableStringAnswers
{
    MorningSuicide(
        numberOfQuestions = DailyNotScoredQuestionnaire.Suicide.numberOfQuestions,
        answerRange = DailyNotScoredQuestionnaire.Suicide.answerRange,
        measureRes = R.string.suicide,
        questionRes = R.array.daily_morning_suicide_questions,
        answerRes = arrayOf(R.array.boolean_answers),
    ),
    NightSuicide(
        numberOfQuestions = DailyNotScoredQuestionnaire.Suicide.numberOfQuestions,
        answerRange = DailyNotScoredQuestionnaire.Suicide.answerRange,
        measureRes = R.string.suicide,
        questionRes = R.array.daily_night_suicide_questions,
        answerRes = arrayOf(R.array.boolean_answers),
    ),
    Symptoms(
        numberOfQuestions = DailyNotScoredQuestionnaire.Symptoms.numberOfQuestions,
        answerRange = DailyNotScoredQuestionnaire.Symptoms.answerRange,
        measureRes = R.string.symptoms,
        questionRes = R.array.daily_symptoms_questions,
        answerRes = arrayOf(
            R.array.appetite_answers,
            R.array.felt_answers,
            R.array.rest_answers,
            R.array.concentration_answers,
            R.array.libido_answers,
            R.array.pain_answers
        ),
    );
}