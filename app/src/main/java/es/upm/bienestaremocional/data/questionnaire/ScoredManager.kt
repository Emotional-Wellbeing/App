package es.upm.bienestaremocional.data.questionnaire

import es.upm.bienestaremocional.data.database.entity.MeasureEntity

abstract class ScoredManager<in T> : NotScoredManager<T>() where T : MeasureEntity {
    abstract val levels: List<ScoreLevel>

    /**
     * Compute the score of the questionnaire if all questions has been answered, null if not
     */
    open val score: Int?
        get() {
            return if (questionnaireFulfilled)
                answers.filterNotNull().sum()
            else
                null
        }

    /**
     * Compute the [ScoreLevel] of the questionnaire if all questions has been answered, null if not
     */
    val scoreLevel: ScoreLevel?
        get() {
            return if (questionnaireFulfilled) {
                var scoreLevel: ScoreLevel? = null
                for (level in levels) {
                    if (score in level.min..level.max) {
                        scoreLevel = level
                        break
                    }
                }
                scoreLevel
            }
            else
                null
        }
}