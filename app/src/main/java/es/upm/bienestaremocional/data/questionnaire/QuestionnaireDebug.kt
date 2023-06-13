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
 * @return Entry
 */
fun generateOneOffStressEntry(createdAt: Long): OneOffStress
{
    val oneOffStressManager = OneOffStressManager()
    val oneOffStress = OneOffStress(createdAt = createdAt)
    for (questionIndex in 0 until oneOffStressManager.numberOfQuestions)
    {
        oneOffStressManager.setAnswer(questionIndex, Random.nextInt(0,oneOffStressManager.numberOfAnswers))
    }
    oneOffStressManager.setEntity(oneOffStress)
    return oneOffStress
}

/**
 * Generates am One Off Depression with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @return Entry
 */
fun generateOneOffDepressionEntry(createdAt: Long): OneOffDepression
{
    val oneOffDepressionManager = OneOffDepressionManager()
    val oneOffDepression = OneOffDepression(createdAt = createdAt)
    for (questionIndex in 0 until oneOffDepressionManager.numberOfQuestions)
    {
        oneOffDepressionManager.setAnswer(questionIndex, Random.nextInt(0,oneOffDepressionManager.numberOfAnswers))
    }
    oneOffDepressionManager.setEntity(oneOffDepression)
    return oneOffDepression
}

/**
 * Generates an One Off Loneliness entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @return Entry
 */
fun generateOneOffLonelinessEntry(createdAt: Long): OneOffLoneliness
{
    val oneOffLonelinessManager = OneOffLonelinessManager()
    val oneOffLoneliness = OneOffLoneliness(createdAt = createdAt)
    for (questionIndex in 0 until oneOffLonelinessManager.numberOfQuestions)
    {
        oneOffLonelinessManager.setAnswer(questionIndex, Random.nextInt(0,oneOffLonelinessManager.numberOfAnswers))
    }
    oneOffLonelinessManager.setEntity(oneOffLoneliness)
    return oneOffLoneliness
}

/**
 * Generates an One Off Loneliness entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @return Entry
 */
fun generateDailyStressEntry(createdAt: Long): DailyStress
{
    val dailyStressManager = DailyStressManager()
    val dailyStress = DailyStress(createdAt = createdAt)
    for (questionIndex in 0 until dailyStressManager.numberOfQuestions)
    {
        dailyStressManager.setAnswer(
            questionIndex,
            Random.nextInt(
                dailyStressManager.answerRange.first,
                dailyStressManager.answerRange.last + 1
            )
        )
    }
    dailyStressManager.setEntity(dailyStress)
    return dailyStress
}

/**
 * Generates an One Off Loneliness entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @return Entry
 */
fun generateDailyDepressionEntry(createdAt: Long): DailyDepression
{
    val dailyDepressionManager = DailyDepressionManager()
    val dailyDepression = DailyDepression(createdAt = createdAt)
    for (questionIndex in 0 until dailyDepressionManager.numberOfQuestions)
    {
        dailyDepressionManager.setAnswer(
            questionIndex,
            Random.nextInt(
                dailyDepressionManager.answerRange.first,
                dailyDepressionManager.answerRange.last + 1
            )
        )
    }
    dailyDepressionManager.setEntity(dailyDepression)
    return dailyDepression
}

/**
 * Generates an One Off Loneliness entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @return Entry
 */
fun generateDailyLonelinessEntry(createdAt: Long): DailyLoneliness
{
    val dailyLonelinessManager = DailyLonelinessManager()
    val dailyLoneliness = DailyLoneliness(createdAt = createdAt)
    for (questionIndex in 0 until dailyLonelinessManager.numberOfQuestions)
    {
        dailyLonelinessManager.setAnswer(
            questionIndex,
            Random.nextInt(
                dailyLonelinessManager.answerRange.first,
                dailyLonelinessManager.answerRange.last + 1
            )
        )
    }
    dailyLonelinessManager.setEntity(dailyLoneliness)
    return dailyLoneliness
}

/**
 * Generates an One Off Loneliness entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @return Entry
 */
fun generateDailySuicideEntry(createdAt: Long): DailySuicide
{
    val dailySuicideManager = DailySuicideManager()
    val dailySuicide = DailySuicide(createdAt = createdAt)
    for (questionIndex in 0 until dailySuicideManager.numberOfQuestions)
    {
        dailySuicideManager.setAnswer(
            questionIndex,
            Random.nextInt(
                dailySuicideManager.answerRange.first,
                dailySuicideManager.answerRange.last + 1
            )
        )
    }
    dailySuicideManager.setCompleted()
    dailySuicideManager.setEntity(dailySuicide)
    return dailySuicide
}

/**
 * Generates an One Off Loneliness entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @return Entry
 */
fun generateDailySymptomsEntry(createdAt: Long): DailySymptoms
{
    val dailySymptomsManager = DailySymptomsManager()
    val dailySymptoms = DailySymptoms(createdAt = createdAt)
    for (questionIndex in 0 until dailySymptomsManager.numberOfQuestions)
    {
        dailySymptomsManager.setAnswer(
            questionIndex,
            Random.nextInt(
                dailySymptomsManager.answerRange.first,
                dailySymptomsManager.answerRange.last + 1
            )
        )
    }
    dailySymptomsManager.setEntity(dailySymptoms)
    return dailySymptoms
}

