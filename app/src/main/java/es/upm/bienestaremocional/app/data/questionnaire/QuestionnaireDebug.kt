package es.upm.bienestaremocional.app.data.questionnaire

import es.upm.bienestaremocional.app.data.alarm.AlarmsAvailable
import es.upm.bienestaremocional.app.data.database.entity.PHQ
import es.upm.bienestaremocional.app.data.database.entity.PSS
import es.upm.bienestaremocional.app.data.database.entity.UCLA
import java.time.ZoneId
import kotlin.random.Random

/**
 * Generates a PSS entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @return Entry
 */
fun generatePSSEntry(createdAt: Long): PSS
{
    val pssManager = PSSManager()
    val pss = PSS(createdAt = createdAt)
    for (questionIndex in 0 until pssManager.numberOfQuestions)
    {
        pssManager.setAnswer(questionIndex, Random.nextInt(0,pssManager.numberOfAnswers))
    }
    pssManager.setEntity(pss)
    return pss
}

/**
 * Generates a PHQ entry with random data without inserting in database.
 * @param createdAt: Entry's timestamp
 * @return Entry
 */
fun generatePHQEntry(createdAt: Long): PHQ
{
    val phqManager = PHQManager()
    val phq = PHQ(createdAt = createdAt)
    for (questionIndex in 0 until phqManager.numberOfQuestions)
    {
        phqManager.setAnswer(questionIndex, Random.nextInt(0,phqManager.numberOfAnswers))
    }
    phqManager.setEntity(phq)
    return phq
}

/**
 * Generates an UCLA entry with random data  without inserting in database.
 * @param createdAt: Entry's timestamp
 * @return Entry
 */
fun generateUCLAEntry(createdAt: Long): UCLA
{
    val uclaManager = UCLAManager()
    val ucla = UCLA(createdAt = createdAt)
    for (questionIndex in 0 until uclaManager.numberOfQuestions)
    {
        uclaManager.setAnswer(questionIndex, Random.nextInt(0,uclaManager.numberOfAnswers))
    }
    uclaManager.setEntity(ucla)
    return ucla
}

/**
 * Generates a bunch of pss questionnaires: all available questionnaires at all hours
 * @param days: Amount of days to generate bunch (14 by default)
 */
fun generateBunchOfPSSEntries(days: Int = 14): List<PSS>
{
    val result : MutableList<PSS> = mutableListOf()

    for (index in 0..days)
    {
        AlarmsAvailable.allAlarms.forEach {
            val createdAt = it.time.minusDays(index.toLong())
                .atZone(ZoneId.systemDefault())
                .toEpochSecond() * 1000
            result.add(generatePSSEntry(createdAt))
        }
    }
    return result
}

/**
 * Generates a bunch of phq questionnaires: all available questionnaires at all hours
 * @param days: Amount of days to generate bunch (14 by default)
 */
fun generateBunchOfPHQEntries(days: Int = 14): List<PHQ>
{
    val result : MutableList<PHQ> = mutableListOf()

    for (index in 0..days)
    {
        AlarmsAvailable.allAlarms.forEach {
            val createdAt = it.time.minusDays(index.toLong())
                .atZone(ZoneId.systemDefault())
                .toEpochSecond() * 1000
            result.add(generatePHQEntry(createdAt))
        }
    }
    return result
}

/**
 * Generates a bunch of ucla questionnaires: all available questionnaires at all hours
 * @param days: Amount of days to generate bunch (14 by default)
 */
fun generateBunchOfUCLAEntries(days: Int = 14): List<UCLA>
{
    val result : MutableList<UCLA> = mutableListOf()

    for (index in 0..days)
    {
        AlarmsAvailable.allAlarms.forEach {
            val createdAt = it.time.minusDays(index.toLong())
                .atZone(ZoneId.systemDefault())
                .toEpochSecond() * 1000
            result.add(generateUCLAEntry(createdAt))
        }
    }
    return result
}