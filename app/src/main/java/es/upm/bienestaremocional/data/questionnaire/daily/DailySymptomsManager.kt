package es.upm.bienestaremocional.data.questionnaire.daily

import es.upm.bienestaremocional.data.database.entity.daily.DailySymptoms
import es.upm.bienestaremocional.data.questionnaire.NotScoredManager

class DailySymptomsManager : NotScoredManager<DailySymptoms>() {
    override val answers: Array<Int?> =
        arrayOfNulls(DailyNotScoredQuestionnaire.Symptoms.numberOfQuestions)
    override val numberOfQuestions: Int = DailyNotScoredQuestionnaire.Symptoms.numberOfQuestions
    override val answerRange: IntRange = DailyNotScoredQuestionnaire.Symptoms.answerRange

    override fun loadEntity(element: DailySymptoms) {
        answers[0] = element.appetite?.ordinal
        answers[1] = element.energy?.ordinal
        answers[2] = element.rest?.ordinal
        answers[3] = element.focus?.ordinal
        answers[4] = element.libido?.ordinal
        answers[5] = element.pain?.ordinal
    }

    override fun setEntity(element: DailySymptoms) {
        element.apply {
            completed = this@DailySymptomsManager.questionnaireFulfilled
            appetite = this@DailySymptomsManager.answers.getOrNull(0)
                ?.let { DailySymptoms.Appetite.get(it) }
            energy = this@DailySymptomsManager.answers.getOrNull(1)
                ?.let { DailySymptoms.Energy.get(it) }
            rest = this@DailySymptomsManager.answers.getOrNull(2)
                ?.let { DailySymptoms.Rest.get(it) }
            focus = this@DailySymptomsManager.answers.getOrNull(3)
                ?.let { DailySymptoms.Focus.get(it) }
            libido = this@DailySymptomsManager.answers.getOrNull(4)
                ?.let { DailySymptoms.Libido.get(it) }
            pain = this@DailySymptomsManager.answers.getOrNull(5)
                ?.let { DailySymptoms.Pain.get(it) }
        }
    }
}