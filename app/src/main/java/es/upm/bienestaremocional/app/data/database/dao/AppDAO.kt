package es.upm.bienestaremocional.app.data.database.dao

import androidx.room.*
import es.upm.bienestaremocional.app.data.database.entity.*

/**
 * Dao with all the operations related to database
 */
@Dao
interface AppDAO
{
    /**
     * Insert a QuestionnaireRound in database
     * @return The ID of QuestionnaireRound inserted
     */
    @Insert
    suspend fun insert(questionnaireRound: QuestionnaireRound) : Long

    /**
     * Insert a PSS in database
     * @return The ID of PSS inserted
     */
    @Insert
    suspend fun insert(pss: PSS) : Long

    /**
     * Insert a PHQ in database
     * @return The ID of PHQ inserted
     */
    @Insert
    suspend fun insert(phq: PHQ) : Long

    /**
     * Insert a UCLA in database
     * @return The ID of UCLA inserted
     */
    @Insert
    suspend fun insert(ucla: UCLA) : Long

    /**
     * Update a QuestionnaireRound in database
     */
    @Update
    suspend fun update(questionnaireRound: QuestionnaireRound)

    /**
     * Update a PSS in database
     */
    @Update
    suspend fun update(pss: PSS)

    /**
     * Update a PHQ in database
     */
    @Update
    suspend fun update(phq: PHQ)

    /**
     * Update a UCLA in database
     */
    @Update
    suspend fun update(ucla: UCLA)

    /**
     * Query all QuestionnaireRounds in database ordered from newest to oldest
     * @return List with the result of the query
     */
    @Query("SELECT * FROM questionnaire_round ORDER BY created_at DESC")
    suspend fun getAllQuestionnaireRound(): List<QuestionnaireRound>

    /**
     * Query all PSS questionnaires in database ordered from newest to oldest
     * @return List with the result of the query
     */
    @Query("SELECT * FROM pss ORDER BY pss_created_at DESC")
    suspend fun getAllPSS(): List<PSS>

    /**
     * Query all PSS questionnaires in database ordered from newest to oldest
     * @return List with the result of the query
     */
    @Query("SELECT * FROM phq ORDER BY phq_created_at DESC")
    suspend fun getAllPHQ(): List<PHQ>

    /**
     * Query all UCLA questionnaires in database ordered from newest to oldest
     * @return List with the result of the query
     */
    @Query("SELECT * FROM ucla ORDER BY ucla_created_at DESC")
    suspend fun getAllUCLA(): List<UCLA>

    @Query("SELECT * " +
            "FROM pss " +
            "WHERE pss_created_at BETWEEN :start AND :end " +
            "ORDER BY pss_created_at DESC")
    suspend fun getAllPSSFromRange(start: Long, end: Long): List<PSS>

    @Query("SELECT * " +
            "FROM phq " +
            "WHERE phq_created_at BETWEEN :start AND :end " +
            "ORDER BY phq_created_at DESC")
    suspend fun getAllPHQFromRange(start: Long, end: Long): List<PHQ>

    @Query("SELECT * " +
            "FROM ucla " +
            "WHERE ucla_created_at BETWEEN :start AND :end " +
            "ORDER BY ucla_created_at DESC")
    suspend fun getAllUCLAFromRange(start: Long, end: Long): List<UCLA>

    /**
     * Query all PSS questionnaires completed in database ordered from newest to oldest
     * @return List with the result of the query
     */
    @Query("SELECT * FROM pss WHERE pss_completed = 1 ORDER BY pss_created_at DESC")
    suspend fun getAllPSSCompleted(): List<PSS>

    /**
     * Query all PSS questionnaires completed in database ordered from newest to oldest
     * @return List with the result of the query
     */
    @Query("SELECT * FROM phq WHERE phq_completed = 1 ORDER BY phq_created_at DESC")
    suspend fun getAllPHQCompleted(): List<PHQ>

    /**
     * Query all UCLA questionnaires completed in database ordered from newest to oldest
     * @return List with the result of the query
     */
    @Query("SELECT * FROM ucla WHERE ucla_completed = 1 ORDER BY ucla_created_at DESC")
    suspend fun getAllUCLACompleted(): List<UCLA>

    /**
     * Query last PSS questionnaire completed in database
     * @return The PSS questionnaire, null if no PSS has been completed.
     */
    @Query("SELECT * FROM pss WHERE pss_completed = 1 ORDER BY pss_created_at DESC LIMIT 1")
    suspend fun getLastPSSCompleted(): PSS?

    /**
     * Query last PHQ questionnaire completed in database
     * @return The PHQ questionnaire, null if no PHQ has been completed.
     */
    @Query("SELECT * FROM phq WHERE phq_completed = 1 ORDER BY phq_created_at DESC LIMIT 1")
    suspend fun getLastPHQCompleted(): PHQ?

    /**
     * Query last UCLA questionnaire completed in database
     * @return The UCLA questionnaire, null if no UCLA has been completed.
     */
    @Query("SELECT * FROM ucla WHERE ucla_completed = 1 ORDER BY ucla_created_at DESC LIMIT 1")
    suspend fun getLastUCLACompleted(): UCLA?

    /**
     * Query the information of the QuestionnaireRound by their ID
     * @return The QuestionnaireRound associated
     */
    @Query("SELECT * FROM questionnaire_round WHERE id = :id")
    suspend fun getQuestionnaireRound(id: Long): QuestionnaireRound?

    /**
     * Query the information of the PSS questionnaire by their ID
     * @return The PSS associated
     */
    @Query("SELECT * FROM pss WHERE pss_id = :id")
    suspend fun getPSS(id: Long): PSS?

    /**
     * Query the information of the PHQ questionnaire by their ID
     * @return The PHQ associated
     */
    @Query("SELECT * FROM phq WHERE phq_id = :id")
    suspend fun getPHQ(id: Long): PHQ?

    /**
     * Query the information of the UCLA questionnaire by their ID
     * @return The UCLA associated
     */
    @Query("SELECT * FROM ucla WHERE ucla_id = :id")
    suspend fun getUCLA(id: Long): UCLA?

    /**
     * Query all QuestionnaireRoundFulls in database ordered from newest to oldest
     * @return List with the result of the query
     */
    @Transaction
    @Query("SELECT qr.*, " +
            "pss.*, " +
            "phq.*, " +
            "ucla.* " +
            "FROM questionnaire_round as qr " +
            "LEFT JOIN pss ON pss.pss_id = qr.pss_id " +
            "LEFT JOIN phq ON phq.phq_id = qr.phq_id " +
            "LEFT JOIN ucla ON ucla.ucla_id = qr.ucla_id " +
            "ORDER BY qr.created_at DESC")
    suspend fun getAllQuestionnaireRoundFull(): List<QuestionnaireRoundFull>

    /**
     * Query a QuestionnaireRoundFull in database by their ID
     * @return The associated QuestionnaireRoundFull
     */
    @Transaction
    @Query("SELECT qr.*, " +
            "pss.*, " +
            "phq.*, " +
            "ucla.* " +
            "FROM questionnaire_round as qr " +
            "LEFT JOIN pss ON pss.pss_id = qr.pss_id " +
            "LEFT JOIN phq ON phq.phq_id = qr.phq_id " +
            "LEFT JOIN ucla ON ucla.ucla_id = qr.ucla_id " +
            "WHERE qr.id = :id"
    )
    suspend fun getQuestionnaireRoundFull(id: Long): QuestionnaireRoundFull

    /**
     * Query all QuestionnaireRoundFulls uncompleted in database ordered from newest to oldest
     * @return List with the result of the query
     */
    @Transaction
    @Query("SELECT qr.*, " +
            "pss.*, " +
            "phq.*, " +
            "ucla.* " +
            "FROM questionnaire_round as qr " +
            "LEFT JOIN pss ON pss.pss_id = qr.pss_id " +
            "LEFT JOIN phq ON phq.phq_id = qr.phq_id " +
            "LEFT JOIN ucla ON ucla.ucla_id = qr.ucla_id " +
            "WHERE (pss.pss_id IS NOT NULL AND pss.pss_completed = 0) " +
            "OR (phq.phq_id IS NOT NULL AND phq.phq_completed = 0) " +
            "OR (ucla.ucla_id IS NOT NULL AND ucla.ucla_completed = 0) " +
            "ORDER BY qr.created_at DESC")
    suspend fun getAllQuestionnaireRoundUncompleted(): List<QuestionnaireRoundFull>

    // Debug options
    @Query("DELETE FROM questionnaire_round")
    fun nukeQuestionnareRoundTable()
    @Query("DELETE FROM phq")
    fun nukePHQTable()
    @Query("DELETE FROM pss")
    fun nukePSSTable()
    @Query("DELETE FROM ucla")
    fun nukeUCLATable()

    @Transaction
    suspend fun nukeDatabase()
    {
        nukeQuestionnareRoundTable()
        nukePHQTable()
        nukePSSTable()
        nukeUCLATable()
    }
}