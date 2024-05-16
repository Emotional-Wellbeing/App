package es.upm.bienestaremocional.data.questionnaire

import es.upm.bienestaremocional.data.database.entity.daily.DailyDepression
import es.upm.bienestaremocional.data.database.entity.daily.DailyLoneliness
import es.upm.bienestaremocional.data.database.entity.daily.DailyStress
import es.upm.bienestaremocional.data.questionnaire.daily.DailyDepressionManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailyLonelinessManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailyStressManager
import kotlin.random.Random

/**
 * Generates an Daily Stress entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @param fulfilled : If questionnaire must be fulfilled or not. If not, a random answer is picked to be uncompleted
 * @return Entry
 */
fun generateDailyStressEntry(
    createdAt: Long,
    fulfilled: Boolean = true
): DailyStress {
    val dailyStressManager = DailyStressManager()
    val dailyStress = DailyStress(createdAt = createdAt)

    val uncompletedAnswer = if (fulfilled)
        null
    else
        Random.nextInt(dailyStressManager.numberOfQuestions)

    for (questionIndex in 0 until dailyStressManager.numberOfQuestions) {
        if (questionIndex != uncompletedAnswer)
            dailyStressManager.setAnswer(
                questionIndex = questionIndex,
                answer = Random.nextInt(
                    dailyStressManager.answerRange.first,
                    dailyStressManager.answerRange.last + 1
                )
            )
    }
    dailyStressManager.setEntity(dailyStress)
    return dailyStress
}

/**
 * Generates an Daily Depression entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @param fulfilled : If questionnaire must be fulfilled or not. If not, a random answer is picked to be uncompleted
 * @return Entry
 */
fun generateDailyDepressionEntry(
    createdAt: Long,
    fulfilled: Boolean = true
): DailyDepression {
    val dailyDepressionManager = DailyDepressionManager()
    val dailyDepression = DailyDepression(createdAt = createdAt)

    val uncompletedAnswer = if (fulfilled)
        null
    else
        Random.nextInt(dailyDepressionManager.numberOfQuestions)

    for (questionIndex in 0 until dailyDepressionManager.numberOfQuestions) {
        if (questionIndex != uncompletedAnswer)
            dailyDepressionManager.setAnswer(
                questionIndex = questionIndex,
                answer = Random.nextInt(
                    dailyDepressionManager.answerRange.first,
                    dailyDepressionManager.answerRange.last + 1
                )
            )
    }
    dailyDepressionManager.setEntity(dailyDepression)
    return dailyDepression
}

/**
 * Generates an Daily Loneliness entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @param fulfilled : If questionnaire must be fulfilled or not. If not, a random answer is picked to be uncompleted
 * @return Entry
 */
fun generateDailyLonelinessEntry(
    createdAt: Long,
    fulfilled: Boolean = true
): DailyLoneliness {
    val dailyLonelinessManager = DailyLonelinessManager()
    val dailyLoneliness = DailyLoneliness(createdAt = createdAt)

    val uncompletedAnswer = if (fulfilled)
        null
    else
        Random.nextInt(dailyLonelinessManager.numberOfQuestions)

    for (questionIndex in 0 until dailyLonelinessManager.numberOfQuestions) {
        if (questionIndex != uncompletedAnswer)
            dailyLonelinessManager.setAnswer(
                questionIndex = questionIndex,
                answer = Random.nextInt(
                    dailyLonelinessManager.answerRange.first,
                    dailyLonelinessManager.answerRange.last + 1
                )
            )
    }
    dailyLonelinessManager.setEntity(dailyLoneliness)
    return dailyLoneliness
}

