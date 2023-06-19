package es.upm.bienestaremocional.data.questionnaire.oneoff

import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.ScoredManager

abstract class OneOffManager<in T> : ScoredManager<T>() where T : MeasureEntity {
    abstract val questionsWithInvertedScore: Set<Int>
    abstract val numberOfAnswers: Int
    abstract val questionScoreOffset: Int

    override val score: Int?
        get() {
            return if (questionnaireFulfilled) {
                var auxiliaryScore = 0
                answers.forEachIndexed { index, answer ->

                    var tempScore = answer!!
                    if (questionsWithInvertedScore.contains(index))
                        tempScore = numberOfAnswers - 1 - tempScore
                    tempScore += questionScoreOffset

                    auxiliaryScore += tempScore
                }
                auxiliaryScore
            }
            else
                null
        }
}