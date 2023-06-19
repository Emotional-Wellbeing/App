package es.upm.bienestaremocional.data.questionnaire

import es.upm.bienestaremocional.data.database.entity.daily.DailyDepression
import es.upm.bienestaremocional.data.database.entity.daily.DailyLoneliness
import es.upm.bienestaremocional.data.database.entity.daily.DailyStress
import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import es.upm.bienestaremocional.data.database.entity.daily.DailySymptoms
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffDepression
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffLoneliness
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffStress
import es.upm.bienestaremocional.data.questionnaire.daily.DailyDepressionManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailyLonelinessManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailyStressManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailySuicideManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailySymptomsManager
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffDepressionManager
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffLonelinessManager
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffStressManager
import kotlin.random.Random

/**
 * Generates an One Off Stress Entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @param fulfilled : If questionnaire must be fulfilled or not. If not, a random answer is picked to be uncompleted
 * @return Entry
 */
fun generateOneOffStressEntry(
    createdAt: Long,
    fulfilled: Boolean = true
): OneOffStress {
    val oneOffStressManager = OneOffStressManager()
    val oneOffStress = OneOffStress(createdAt = createdAt)

    val uncompletedAnswer = if (fulfilled)
        null
    else
        Random.nextInt(oneOffStressManager.numberOfQuestions)

    for (questionIndex in 0 until oneOffStressManager.numberOfQuestions) {
        if (questionIndex != uncompletedAnswer)
            oneOffStressManager.setAnswer(
                questionIndex = questionIndex,
                answer = Random.nextInt(0, oneOffStressManager.numberOfAnswers)
            )
    }
    oneOffStressManager.setEntity(oneOffStress)
    return oneOffStress
}

/**
 * Generates an One Off Depression Entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @param fulfilled : If questionnaire must be fulfilled or not. If not, a random answer is picked to be uncompleted
 * @return Entry
 */
fun generateOneOffDepressionEntry(
    createdAt: Long,
    fulfilled: Boolean = true
): OneOffDepression {
    val oneOffDepressionManager = OneOffDepressionManager()
    val oneOffDepression = OneOffDepression(createdAt = createdAt)

    val uncompletedAnswer = if (fulfilled)
        null
    else
        Random.nextInt(oneOffDepressionManager.numberOfQuestions)

    for (questionIndex in 0 until oneOffDepressionManager.numberOfQuestions) {
        if (questionIndex != uncompletedAnswer)
            oneOffDepressionManager.setAnswer(
                questionIndex = questionIndex,
                answer = Random.nextInt(
                    0,
                    oneOffDepressionManager.numberOfAnswers
                )
            )
    }
    oneOffDepressionManager.setEntity(oneOffDepression)
    return oneOffDepression
}

/**
 * Generates an One Off Loneliness entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @param fulfilled : If questionnaire must be fulfilled or not. If not, a random answer is picked to be uncompleted
 * @return Entry
 */
fun generateOneOffLonelinessEntry(
    createdAt: Long,
    fulfilled: Boolean = true
): OneOffLoneliness {
    val oneOffLonelinessManager = OneOffLonelinessManager()
    val oneOffLoneliness = OneOffLoneliness(createdAt = createdAt)

    val uncompletedAnswer = if (fulfilled)
        null
    else
        Random.nextInt(oneOffLonelinessManager.numberOfQuestions)

    for (questionIndex in 0 until oneOffLonelinessManager.numberOfQuestions) {
        if (questionIndex != uncompletedAnswer)
            oneOffLonelinessManager.setAnswer(
                questionIndex = questionIndex,
                answer = Random.nextInt(
                    0,
                    oneOffLonelinessManager.numberOfAnswers
                )
            )
    }
    oneOffLonelinessManager.setEntity(oneOffLoneliness)
    return oneOffLoneliness
}

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

/**
 * Generates an Daily Suicide entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @param fulfilled : If questionnaire must be fulfilled or not. If not, a random answer is picked to be uncompleted
 * @return Entry
 */
fun generateDailySuicideEntry(
    createdAt: Long,
    fulfilled: Boolean = true
): DailySuicide {
    val dailySuicideManager = DailySuicideManager()
    val dailySuicide = DailySuicide(createdAt = createdAt)

    val differentAnswer = Random.nextInt(dailySuicideManager.numberOfQuestions)

    for (questionIndex in 0 until dailySuicideManager.numberOfQuestions)
    {
        if (fulfilled)
        {
            // Different answer will be the only 0, so previous answers must be 1
            if (questionIndex <= differentAnswer)
            {
                val answer = if (questionIndex < differentAnswer)
                    1
                else
                    0

                dailySuicideManager.setAnswer(
                    questionIndex = questionIndex,
                    answer = answer
                )
            }
        }
        else
        {
            // If one 0 is present, the entry is fulfilled
            if (questionIndex < differentAnswer)
                dailySuicideManager.setAnswer(
                    questionIndex = questionIndex,
                    answer = 1
                )
        }
    }
    dailySuicideManager.setEntity(dailySuicide)
    return dailySuicide
}

/**
 * Generates an Daily Symptoms entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @param fulfilled : If questionnaire must be fulfilled or not. If not, a random answer is picked to be uncompleted
 * @return Entry
 */
fun generateDailySymptomsEntry(
    createdAt: Long,
    fulfilled: Boolean = true
): DailySymptoms {
    val dailySymptomsManager = DailySymptomsManager()
    val dailySymptoms = DailySymptoms(createdAt = createdAt)

    val uncompletedAnswer = if (fulfilled)
        null
    else
        Random.nextInt(dailySymptomsManager.numberOfQuestions)

    for (questionIndex in 0 until dailySymptomsManager.numberOfQuestions) {
        if (questionIndex != uncompletedAnswer)
            dailySymptomsManager.setAnswer(
                questionIndex = questionIndex,
                answer = Random.nextInt(
                    dailySymptomsManager.answerRange.first,
                    dailySymptomsManager.answerRange.last + 1
                )
            )
    }
    dailySymptomsManager.setEntity(dailySymptoms)
    return dailySymptoms
}

