package es.upm.bienestaremocional.data.database.dao

import androidx.room.*
import es.upm.bienestaremocional.data.database.entity.LastUpload
import es.upm.bienestaremocional.data.database.entity.daily.DailyDepression
import es.upm.bienestaremocional.data.database.entity.daily.DailyLoneliness
import es.upm.bienestaremocional.data.database.entity.daily.DailyStress
import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import es.upm.bienestaremocional.data.database.entity.daily.DailySymptoms
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffDepression
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffLoneliness
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffStress
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import es.upm.bienestaremocional.data.database.entity.round.DailyRoundFull
import es.upm.bienestaremocional.data.database.entity.round.OneOffRound
import es.upm.bienestaremocional.data.database.entity.round.OneOffRoundFull

/**
 * Dao with all the operations related to database
 */
@Dao
interface AppDAO
{
    /**
     * -------------------------------------------------------------------------------------------
     * Inserts
     * -------------------------------------------------------------------------------------------
     */

    /**
     * Insert a LastUpload value in database
     * @return ID of inserted row
     */
    @Insert
    suspend fun insert(lastUpload: LastUpload) : Long

    /**
     * Insert a One Off Round in database
     * @return The ID of One Off Round inserted
     */
    @Insert
    suspend fun insert(oneOffRound: OneOffRound) : Long

    /**
     * Insert a Daily Round in database
     * @return The ID of Daily Round inserted
     */
    @Insert
    suspend fun insert(dailyRound: DailyRound) : Long

    /**
     * Insert a OneOffStress in database
     * @return The ID of OneOffStress inserted
     */
    @Insert
    suspend fun insert(oneOffStress: OneOffStress) : Long

    /**
     * Insert a OneOffDepression in database
     * @return The ID of OneOffDepression inserted
     */
    @Insert
    suspend fun insert(oneOffDepression: OneOffDepression) : Long

    /**
     * Insert a OneOffLoneliness in database
     * @return The ID of OneOffLoneliness inserted
     */
    @Insert
    suspend fun insert(oneOffLoneliness: OneOffLoneliness) : Long

    /**
     * Insert a DailyAnxiety questionnaire in database
     * @return The ID of DailyAnxiety inserted
     */
    @Insert
    suspend fun insert(dailyStress: DailyStress) : Long

    /**
     * Insert a DailyDepression questionnaire in database
     * @return The ID of DailyDepression inserted
     */
    @Insert
    suspend fun insert(dailyDepression: DailyDepression) : Long

    /**
     * Insert a DailyLoneliness questionnaire in database
     * @return The ID of DailyLoneliness inserted
     */
    @Insert
    suspend fun insert(dailyLoneliness: DailyLoneliness) : Long

    /**
     * Insert a DailySuicide questionnaire in database
     * @return The ID of DailySuicide inserted
     */
    @Insert
    suspend fun insert(dailySuicide: DailySuicide) : Long

    /**
     * Insert a DailySymptoms questionnaire in database
     * @return The ID of DailySymptoms inserted
     */
    @Insert
    suspend fun insert(dailySymptoms: DailySymptoms) : Long

    /**
     * -------------------------------------------------------------------------------------------
     * Updates
     * -------------------------------------------------------------------------------------------
     */

    /**
     * Update a lastUpload in database
     */
    @Update
    suspend fun update(lastUpload: LastUpload)

    /**
     * Update a OneOffRound in database
     */
    @Update
    suspend fun update(oneOffRound: OneOffRound)

    /**
     * Update a DailyRound in database
     */
    @Update
    suspend fun update(dailyRound: DailyRound)

    /**
     * Update a OneOffStress in database
     */
    @Update
    suspend fun update(oneOffStress: OneOffStress)

    /**
     * Update a OneOffDepression in database
     */
    @Update
    suspend fun update(oneOffDepression: OneOffDepression)

    /**
     * Update a OneOffLoneliness in database
     */
    @Update
    suspend fun update(oneOffLoneliness: OneOffLoneliness)

    /**
     * Update a DailyAnxiety in database
     */
    @Update
    suspend fun update(dailyStress: DailyStress)

    /**
     * Update a DailyDepression in database
     */
    @Update
    suspend fun update(dailyDepression: DailyDepression)

    /**
     * Update a DailyLoneliness in database
     */
    @Update
    suspend fun update(dailyLoneliness: DailyLoneliness)

    /**
     * Update a DailySuicide in database
     */
    @Update
    suspend fun update(dailySuicide: DailySuicide)

    /**
     * Update a DailySymptoms in database
     */
    @Update
    suspend fun update(dailySymptoms: DailySymptoms)

    /**
     * -------------------------------------------------------------------------------------------
     * Get by
     * -------------------------------------------------------------------------------------------
     */

    /**
     * Query last upload by type
     * @return LastUpload instance, null if no OneOffDepression has been completed.
     */
    @Query("SELECT * FROM last_upload WHERE type = :type")
    suspend fun getLastUpload(type: LastUpload.Type): LastUpload?

    /**
     * Query the information of the QuestionnaireRound by their ID
     * @return The QuestionnaireRound associated
     */
    @Query("SELECT * FROM one_off_round WHERE id = :id")
    suspend fun getOneOffRound(id: Long): OneOffRound?

    /**
     * Query the information of the OneOffLoneliness questionnaire by their ID
     * @return The OneOffLoneliness associated
     */
    @Transaction
    @Query("SELECT one_off_round.*, " +
            "one_off_stress.*, " +
            "one_off_depression.*, " +
            "one_off_loneliness.* " +
            "FROM one_off_round " +
            "LEFT JOIN one_off_stress ON one_off_round.stress_id = one_off_stress.one_off_stress_id " +
            "LEFT JOIN one_off_depression ON one_off_round.depression_id = one_off_depression.one_off_depression_id " +
            "LEFT JOIN one_off_loneliness ON one_off_round.loneliness_id = one_off_loneliness.one_off_loneliness_id " +
            "WHERE one_off_round.id = :id")
    suspend fun getOneOffRoundFull(id: Long): OneOffRoundFull?

    /**
     * Query the information of the QuestionnaireRound by their ID
     * @return The QuestionnaireRound associated
     */
    @Query("SELECT * FROM daily_round WHERE id = :id")
    suspend fun getDailyRound(id: Long): DailyRound?

    @Transaction
    @Query("SELECT daily_round.*, " +
            "daily_stress.*, " +
            "daily_depression.*, " +
            "daily_loneliness.*, " +
            "daily_suicide.*, " +
            "daily_symptoms.* " +
            "FROM daily_round " +
            "LEFT JOIN daily_stress ON daily_round.stress_id = daily_stress.daily_stress_id " +
            "LEFT JOIN daily_depression ON daily_round.depression_id = daily_depression.daily_depression_id " +
            "LEFT JOIN daily_loneliness ON daily_round.loneliness_id = daily_loneliness.daily_loneliness_id " +
            "LEFT JOIN daily_suicide ON daily_round.suicide_id = daily_suicide.daily_suicide_id " +
            "LEFT JOIN daily_symptoms ON daily_round.symptoms_id = daily_symptoms.daily_symptoms_id " +
            "WHERE daily_round.id = :id")
    suspend fun getDailyRoundFull(id: Long): DailyRoundFull?

    /**
     * Query the information of the OneOffStress questionnaire by their ID
     * @return The OneOffStress associated
     */
    @Query("SELECT * FROM one_off_stress WHERE one_off_stress_id = :id")
    suspend fun getOneOffStress(id: Long): OneOffStress?

    /**
     * Query the information of the OneOffDepression questionnaire by their ID
     * @return The OneOffDepression associated
     */
    @Query("SELECT * FROM one_off_depression WHERE one_off_depression_id = :id")
    suspend fun getOneOffDepression(id: Long): OneOffDepression?

    /**
     * Query the information of the OneOffLoneliness questionnaire by their ID
     * @return The OneOffLoneliness associated
     */
    @Query("SELECT * FROM one_off_loneliness WHERE one_off_loneliness_id = :id")
    suspend fun getOneOffLoneliness(id: Long): OneOffLoneliness?

    /**
     * Query the information of the OneOffLoneliness questionnaire by their ID
     * @return The OneOffLoneliness associated
     */
    @Query("SELECT * FROM daily_stress WHERE daily_stress_id = :id")
    suspend fun getDailyStress(id: Long): DailyStress?

    /**
     * Query the information of the OneOffLoneliness questionnaire by their ID
     * @return The OneOffLoneliness associated
     */
    @Query("SELECT * FROM daily_depression WHERE daily_depression_id = :id")
    suspend fun getDailyDepression(id: Long): DailyDepression?

    /**
     * Query the information of the OneOffLoneliness questionnaire by their ID
     * @return The OneOffLoneliness associated
     */
    @Query("SELECT * FROM daily_loneliness WHERE daily_loneliness_id = :id")
    suspend fun getDailyLoneliness(id: Long): DailyLoneliness?

    /**
     * Query the information of the OneOffLoneliness questionnaire by their ID
     * @return The OneOffLoneliness associated
     */
    @Query("SELECT * FROM daily_suicide WHERE daily_suicide_id = :id")
    suspend fun getDailySuicide(id: Long): DailySuicide?

    /**
     * Query the information of the OneOffLoneliness questionnaire by their ID
     * @return The OneOffLoneliness associated
     */
    @Query("SELECT * FROM daily_symptoms WHERE daily_symptoms_id = :id")
    suspend fun getDailySymptoms(id: Long): DailySymptoms?


    /**
     * -------------------------------------------------------------------------------------------
     * Get All
     * -------------------------------------------------------------------------------------------
     */

    @Query("SELECT * FROM one_off_round ORDER BY created_at DESC")
    suspend fun getAllOneOffRound(): List<OneOffRound>

    @Transaction
    @Query("SELECT one_off_round.*, " +
            "one_off_stress.*, " +
            "one_off_depression.*, " +
            "one_off_loneliness.* " +
            "FROM one_off_round " +
            "LEFT JOIN one_off_stress ON one_off_round.stress_id = one_off_stress.one_off_stress_id " +
            "LEFT JOIN one_off_depression ON one_off_round.depression_id = one_off_depression.one_off_depression_id " +
            "LEFT JOIN one_off_loneliness ON one_off_round.loneliness_id = one_off_loneliness.one_off_loneliness_id " +
            "ORDER BY one_off_round.created_at DESC")
    suspend fun getAllOneOffRoundFull(): List<OneOffRoundFull>


    @Transaction
    @Query("SELECT daily_round.*, " +
            "daily_stress.*, " +
            "daily_depression.*, " +
            "daily_loneliness.*, " +
            "daily_suicide.*, " +
            "daily_symptoms.* " +
            "FROM daily_round " +
            "LEFT JOIN daily_stress ON daily_round.stress_id = daily_stress.daily_stress_id " +
            "LEFT JOIN daily_depression ON daily_round.depression_id = daily_depression.daily_depression_id " +
            "LEFT JOIN daily_loneliness ON daily_round.loneliness_id = daily_loneliness.daily_loneliness_id " +
            "LEFT JOIN daily_suicide ON daily_round.suicide_id = daily_suicide.daily_suicide_id " +
            "LEFT JOIN daily_symptoms ON daily_round.symptoms_id = daily_symptoms.daily_symptoms_id " +
            "ORDER BY daily_round.created_at DESC")
    suspend fun getAllDailyRoundFull(): List<DailyRoundFull>

    @Query("SELECT * FROM daily_round ORDER BY created_at DESC")
    suspend fun getAllDailyRound(): List<DailyRound>

    @Query("SELECT * FROM one_off_stress ORDER BY one_off_stress_created_at DESC")
    suspend fun getAllOneOffStress(): List<OneOffStress>

    @Query("SELECT * FROM one_off_depression ORDER BY one_off_depression_created_at DESC")
    suspend fun getAllOneOffDepression(): List<OneOffDepression>

    @Query("SELECT * FROM one_off_loneliness ORDER BY one_off_loneliness_created_at DESC")
    suspend fun getAllOneOffLoneliness(): List<OneOffLoneliness>

    @Query("SELECT * FROM daily_stress ORDER BY daily_stress_created_at DESC")
    suspend fun getAllDailyStress(): List<DailyStress>

    @Query("SELECT * FROM daily_depression ORDER BY daily_depression_created_at DESC")
    suspend fun getAllDailyDepression(): List<DailyDepression>

    @Query("SELECT * FROM daily_loneliness ORDER BY daily_loneliness_created_at DESC")
    suspend fun getAllDailyLoneliness(): List<DailyLoneliness>

    @Query("SELECT * FROM daily_suicide ORDER BY daily_suicide_created_at DESC")
    suspend fun getAllDailySuicide(): List<DailySuicide>

    @Query("SELECT * FROM daily_symptoms ORDER BY daily_symptoms_created_at DESC")
    suspend fun getAllDailySymptoms(): List<DailySymptoms>

    /**
     * -------------------------------------------------------------------------------------------
     * Get All Completed
     * -------------------------------------------------------------------------------------------
     */

    @Query("SELECT * " +
            "FROM one_off_stress " +
            "WHERE one_off_stress_completed = 1 " +
            "ORDER BY one_off_stress_created_at DESC")
    suspend fun getAllOneOffStressCompleted(): List<OneOffStress>

    @Query("SELECT * " +
            "FROM one_off_depression " +
            "WHERE one_off_depression_completed = 1 " +
            "ORDER BY one_off_depression_created_at DESC")
    suspend fun getAllOneOffDepressionCompleted(): List<OneOffDepression>

    @Query("SELECT * " +
            "FROM one_off_loneliness " +
            "WHERE one_off_loneliness_completed = 1 " +
            "ORDER BY one_off_loneliness_created_at DESC")
    suspend fun getAllOneOffLonelinessCompleted(): List<OneOffLoneliness>

    @Query("SELECT * " +
            "FROM daily_stress " +
            "WHERE daily_stress_completed = 1 " +
            "ORDER BY daily_stress_created_at DESC")
    suspend fun getAllDailyStressCompleted(): List<DailyStress>

    @Query("SELECT * " +
            "FROM daily_depression " +
            "WHERE daily_depression_completed = 1 " +
            "ORDER BY daily_depression_created_at DESC")
    suspend fun getAllDailyDepressionCompleted(): List<DailyDepression>

    @Query("SELECT * " +
            "FROM daily_loneliness " +
            "WHERE daily_loneliness_completed = 1 " +
            "ORDER BY daily_loneliness_created_at DESC")
    suspend fun getAllDailyLonelinessCompleted(): List<DailyLoneliness>

    @Query("SELECT * " +
            "FROM daily_suicide " +
            "WHERE daily_suicide_completed = 1 " +
            "ORDER BY daily_suicide_created_at DESC")
    suspend fun getAllDailySuicideCompleted(): List<DailySuicide>

    @Query("SELECT * " +
            "FROM daily_symptoms " +
            "WHERE daily_symptoms_completed = 1 " +
            "ORDER BY daily_symptoms_created_at DESC")
    suspend fun getAllDailySymptomsCompleted(): List<DailySymptoms>

    /**
     * -------------------------------------------------------------------------------------------
     * Get All From Range
     * -------------------------------------------------------------------------------------------
     */

    @Query("SELECT * " +
            "FROM one_off_stress " +
            "WHERE one_off_stress_created_at BETWEEN :start AND :end " +
            "ORDER BY one_off_stress_created_at DESC")
    suspend fun getAllOneOffStressFromRange(start: Long, end: Long): List<OneOffStress>

    @Query("SELECT * " +
            "FROM one_off_depression " +
            "WHERE one_off_depression_created_at BETWEEN :start AND :end " +
            "ORDER BY one_off_depression_created_at DESC")
    suspend fun getAllOneOffDepressionFromRange(start: Long, end: Long): List<OneOffDepression>

    @Query("SELECT * " +
            "FROM one_off_loneliness " +
            "WHERE one_off_loneliness_created_at BETWEEN :start AND :end " +
            "ORDER BY one_off_loneliness_created_at DESC")
    suspend fun getAllOneOffLonelinessFromRange(start: Long, end: Long): List<OneOffLoneliness>

    @Query("SELECT * " +
            "FROM daily_stress " +
            "WHERE daily_stress_created_at BETWEEN :start AND :end " +
            "ORDER BY daily_stress_created_at DESC")
    suspend fun getAllDailyStressFromRange(start: Long, end: Long): List<DailyStress>

    @Query("SELECT * " +
            "FROM daily_depression " +
            "WHERE daily_depression_created_at BETWEEN :start AND :end " +
            "ORDER BY daily_depression_created_at DESC")
    suspend fun getAllDailyDepressionFromRange(start: Long, end: Long): List<DailyDepression>

    @Query("SELECT * " +
            "FROM daily_loneliness " +
            "WHERE daily_loneliness_created_at BETWEEN :start AND :end " +
            "ORDER BY daily_loneliness_created_at DESC")
    suspend fun getAllDailyLonelinessFromRange(start: Long, end: Long): List<DailyLoneliness>

    @Query("SELECT * " +
            "FROM daily_suicide " +
            "WHERE daily_suicide_created_at BETWEEN :start AND :end " +
            "ORDER BY daily_suicide_created_at DESC")
    suspend fun getAllDailySuicideFromRange(start: Long, end: Long): List<DailySuicide>

    @Query("SELECT * " +
            "FROM daily_symptoms " +
            "WHERE daily_symptoms_created_at BETWEEN :start AND :end " +
            "ORDER BY daily_symptoms_created_at DESC")
    suspend fun getAllDailySymptomsFromRange(start: Long, end: Long): List<DailySymptoms>

    /**
     * -------------------------------------------------------------------------------------------
     * Get All Uncompleted
     * -------------------------------------------------------------------------------------------
     */

    @Transaction
    @Query("SELECT one_off_round.*, " +
            "one_off_stress.*, " +
            "one_off_depression.*, " +
            "one_off_loneliness.* " +
            "FROM one_off_round " +
            "LEFT JOIN one_off_stress ON one_off_round.stress_id = one_off_stress.one_off_stress_id " +
            "LEFT JOIN one_off_depression ON one_off_round.depression_id = one_off_depression.one_off_depression_id " +
            "LEFT JOIN one_off_loneliness ON one_off_round.loneliness_id = one_off_loneliness.one_off_loneliness_id " +
            "WHERE (one_off_stress.one_off_stress_id IS NOT NULL AND one_off_stress.one_off_stress_completed = 0) " +
            "OR (one_off_depression.one_off_depression_id IS NOT NULL AND one_off_depression.one_off_depression_completed = 0) " +
            "OR (one_off_loneliness.one_off_loneliness_id IS NOT NULL AND one_off_loneliness.one_off_loneliness_completed  = 0) " +
            "ORDER BY one_off_round.created_at DESC")
    suspend fun getAllOneOffRoundFullUncompleted(): List<OneOffRoundFull>

    @Transaction
    @Query("SELECT daily_round.*, " +
            "daily_stress.*, " +
            "daily_depression.*, " +
            "daily_loneliness.*, " +
            "daily_suicide.*, " +
            "daily_symptoms.* " +
            "FROM daily_round " +
            "LEFT JOIN daily_stress ON daily_round.stress_id = daily_stress.daily_stress_id " +
            "LEFT JOIN daily_depression ON daily_round.depression_id = daily_depression.daily_depression_id " +
            "LEFT JOIN daily_loneliness ON daily_round.loneliness_id = daily_loneliness.daily_loneliness_id " +
            "LEFT JOIN daily_suicide ON daily_round.suicide_id = daily_suicide.daily_suicide_id " +
            "LEFT JOIN daily_symptoms ON daily_round.symptoms_id = daily_symptoms.daily_symptoms_id " +
            "WHERE (daily_stress.daily_stress_id IS NOT NULL AND daily_stress.daily_stress_completed = 0) " +
            "OR (daily_depression.daily_depression_id IS NOT NULL AND daily_depression.daily_depression_completed = 0) " +
            "OR (daily_loneliness.daily_loneliness_id IS NOT NULL AND daily_loneliness.daily_loneliness_completed  = 0) " +
            "OR (daily_suicide.daily_suicide_id IS NOT NULL AND daily_suicide.daily_suicide_completed = 0) " +
            "OR (daily_symptoms.daily_symptoms_id IS NOT NULL AND daily_symptoms.daily_symptoms_completed  = 0) " +
            "ORDER BY daily_round.created_at DESC")
    suspend fun getAllDailyRoundFullUncompleted(): List<DailyRoundFull>



    /**
     * -------------------------------------------------------------------------------------------
     * Get Last Completed
     * -------------------------------------------------------------------------------------------
     */

    @Query("SELECT * " +
            "FROM one_off_stress " +
            "WHERE one_off_stress_completed = 1 " +
            "ORDER BY one_off_stress_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastOneOffStressCompleted(): OneOffStress?

    @Query("SELECT * " +
            "FROM one_off_depression " +
            "WHERE one_off_depression_completed = 1 " +
            "ORDER BY one_off_depression_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastOneOffDepressionCompleted(): OneOffDepression?

    @Query("SELECT * " +
            "FROM one_off_loneliness " +
            "WHERE one_off_loneliness_completed = 1 " +
            "ORDER BY one_off_loneliness_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastOneOffLonelinessCompleted(): OneOffLoneliness?

    @Query("SELECT * " +
            "FROM daily_stress " +
            "WHERE daily_stress_completed = 1 " +
            "ORDER BY daily_stress_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastDailyStressCompleted(): DailyStress?

    @Query("SELECT * " +
            "FROM daily_depression " +
            "WHERE daily_depression_completed = 1 " +
            "ORDER BY daily_depression_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastDailyDepressionCompleted(): DailyDepression?

    @Query("SELECT * " +
            "FROM daily_loneliness " +
            "WHERE daily_loneliness_completed = 1 " +
            "ORDER BY daily_loneliness_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastDailyLonelinessCompleted(): DailyLoneliness?

    @Query("SELECT * " +
            "FROM daily_suicide " +
            "WHERE daily_suicide_completed = 1 " +
            "ORDER BY daily_suicide_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastDailySuicideCompleted(): DailySuicide?

    @Query("SELECT * " +
            "FROM daily_symptoms " +
            "WHERE daily_symptoms_completed = 1 " +
            "ORDER BY daily_symptoms_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastDailySymptomsCompleted(): DailySymptoms?


    /////

/*
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
    suspend fun getAllQuestionnaireRoundFull(): List<OneOffRoundFull>

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
    suspend fun getQuestionnaireRoundFull(id: Long): OneOffRoundFull

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
    suspend fun getAllQuestionnaireRoundUncompleted(): List<OneOffRoundFull>*/

    /**
     * -------------------------------------------------------------------------------------------
     * Debug
     * -------------------------------------------------------------------------------------------
     */
    @Query("DELETE FROM last_upload")
    fun nukeLastUploadTable()
    @Query("DELETE FROM one_off_round")
    fun nukeOneOffRoundTable()
    @Query("DELETE FROM daily_round")
    fun nukeDailyRoundTable()
    @Query("DELETE FROM one_off_depression")
    fun nukeOneOffDepressionTable()
    @Query("DELETE FROM one_off_stress")
    fun nukeOneOffStressTable()
    @Query("DELETE FROM one_off_loneliness")
    fun nukeOneOffLonelinessTable()
    @Query("DELETE FROM daily_stress")
    fun nukeDailyStressTable()
    @Query("DELETE FROM daily_depression")
    fun nukeDailyDepressionTable()
    @Query("DELETE FROM daily_loneliness")
    fun nukeDailyLonelinessTable()
    @Query("DELETE FROM daily_suicide")
    fun nukeDailySuicideTable()
    @Query("DELETE FROM daily_symptoms")
    fun nukeDailySymptomsTable()


    @Transaction
    suspend fun nukeDatabase()
    {
        nukeLastUploadTable()
        nukeOneOffRoundTable()
        nukeDailyRoundTable()

        nukeOneOffDepressionTable()
        nukeOneOffStressTable()
        nukeOneOffLonelinessTable()

        nukeDailyStressTable()
        nukeDailyDepressionTable()
        nukeDailyLonelinessTable()
        nukeDailySuicideTable()
        nukeDailySymptomsTable()
    }
}