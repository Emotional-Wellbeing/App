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
     * Insert a [LastUpload] value in database
     * @return ID of inserted row
     */
    @Insert
    suspend fun insert(lastUpload: LastUpload) : Long

    /**
     * Insert a [OneOffRound] in database
     * @return The ID of [OneOffRound] inserted
     */
    @Insert
    suspend fun insert(oneOffRound: OneOffRound) : Long

    /**
     * Insert a [DailyRound] in database
     * @return The ID of [DailyRound] inserted
     */
    @Insert
    suspend fun insert(dailyRound: DailyRound) : Long

    /**
     * Insert a [OneOffStress] in database
     * @return The ID of [OneOffStress] inserted
     */
    @Insert
    suspend fun insert(oneOffStress: OneOffStress) : Long

    /**
     * Insert a [OneOffDepression] in database
     * @return The ID of [OneOffDepression] inserted
     */
    @Insert
    suspend fun insert(oneOffDepression: OneOffDepression) : Long

    /**
     * Insert a [OneOffLoneliness] in database
     * @return The ID of [OneOffLoneliness] inserted
     */
    @Insert
    suspend fun insert(oneOffLoneliness: OneOffLoneliness) : Long

    /**
     * Insert a [DailyStress] in database
     * @return The ID of [DailyStress] inserted
     */
    @Insert
    suspend fun insert(dailyStress: DailyStress) : Long

    /**
     * Insert a [DailyDepression] in database
     * @return The ID of [DailyDepression] inserted
     */
    @Insert
    suspend fun insert(dailyDepression: DailyDepression) : Long

    /**
     * Insert a [DailyLoneliness] in database
     * @return The ID of [DailyLoneliness] inserted
     */
    @Insert
    suspend fun insert(dailyLoneliness: DailyLoneliness) : Long

    /**
     * Insert a [DailySuicide] in database
     * @return The ID of [DailySuicide] inserted
     */
    @Insert
    suspend fun insert(dailySuicide: DailySuicide) : Long

    /**
     * Insert a [DailySymptoms] in database
     * @return The ID of [DailySymptoms] inserted
     */
    @Insert
    suspend fun insert(dailySymptoms: DailySymptoms) : Long

    /**
     * -------------------------------------------------------------------------------------------
     * Updates
     * -------------------------------------------------------------------------------------------
     */

    /**
     * Update a [LastUpload] in database
     */
    @Update
    suspend fun update(lastUpload: LastUpload)

    /**
     * Update an [OneOffRound] in database
     */
    @Update
    suspend fun update(oneOffRound: OneOffRound)

    /**
     * Update a [DailyRound] in database
     */
    @Update
    suspend fun update(dailyRound: DailyRound)

    /**
     * Update an [OneOffStress] in database
     */
    @Update
    suspend fun update(oneOffStress: OneOffStress)

    /**
     * Update an [OneOffDepression] in database
     */
    @Update
    suspend fun update(oneOffDepression: OneOffDepression)

    /**
     * Update an [OneOffLoneliness] in database
     */
    @Update
    suspend fun update(oneOffLoneliness: OneOffLoneliness)

    /**
     * Update a [DailyStress] in database
     */
    @Update
    suspend fun update(dailyStress: DailyStress)

    /**
     * Update a [DailyDepression] in database
     */
    @Update
    suspend fun update(dailyDepression: DailyDepression)

    /**
     * Update a [DailyLoneliness] in database
     */
    @Update
    suspend fun update(dailyLoneliness: DailyLoneliness)

    /**
     * Update a [DailySuicide] in database
     */
    @Update
    suspend fun update(dailySuicide: DailySuicide)

    /**
     * Update a [DailySymptoms] in database
     */
    @Update
    suspend fun update(dailySymptoms: DailySymptoms)

    /**
     * -------------------------------------------------------------------------------------------
     * Get by
     * -------------------------------------------------------------------------------------------
     */

    /**
     * Query [LastUpload] by type
     * @return [LastUpload] instance if has been founded
     */
    @Query("SELECT * FROM last_upload WHERE type = :type")
    suspend fun getLastUpload(type: LastUpload.Type): LastUpload?

    /**
     * Query [OneOffRound] by their ID
     * @return The [OneOffRound] associated, null if id doesn't match any row
     */
    @Query("SELECT * FROM one_off_round WHERE id = :id")
    suspend fun getOneOffRound(id: Long): OneOffRound?

    /**
     * Query the information of the [OneOffRound] with the questionnaires ([OneOffRoundFull])
     * by their ID
     * @return The [OneOffRound] associated, null if id doesn't match any row
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
     * Query [DailyRound] by their ID
     * @return The [DailyRound] associated, null if id doesn't match any row
     */
    @Query("SELECT * FROM daily_round WHERE id = :id")
    suspend fun getDailyRound(id: Long): DailyRound?

    /**
     * Query the information of the [DailyRound] with the questionnaires ([DailyRoundFull])
     * by their ID
     * @return The [DailyRound] associated, null if id doesn't match any row
     */
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
     * Query the information of the [OneOffStress] by their ID
     * @return The [OneOffStress] associated, null if id doesn't match any row
     */
    @Query("SELECT * FROM one_off_stress WHERE one_off_stress_id = :id")
    suspend fun getOneOffStress(id: Long): OneOffStress?

    /**
     * Query the information of the [OneOffDepression] by their ID
     * @return The [OneOffDepression] associated, null if id doesn't match any row
     */
    @Query("SELECT * FROM one_off_depression WHERE one_off_depression_id = :id")
    suspend fun getOneOffDepression(id: Long): OneOffDepression?

    /**
     * Query the information of the [OneOffLoneliness] by their ID
     * @return The [OneOffLoneliness] associated, null if id doesn't match any row
     */
    @Query("SELECT * FROM one_off_loneliness WHERE one_off_loneliness_id = :id")
    suspend fun getOneOffLoneliness(id: Long): OneOffLoneliness?

    /**
     * Query the information of the [DailyStress] by their ID
     * @return The [DailyStress] associated, null if id doesn't match any row
     */
    @Query("SELECT * FROM daily_stress WHERE daily_stress_id = :id")
    suspend fun getDailyStress(id: Long): DailyStress?

    /**
     * Query the information of the [DailyDepression] by their ID
     * @return The [DailyDepression] associated, null if id doesn't match any row
     */
    @Query("SELECT * FROM daily_depression WHERE daily_depression_id = :id")
    suspend fun getDailyDepression(id: Long): DailyDepression?

    /**
     * Query the information of the [DailyLoneliness] by their ID
     * @return The [DailyLoneliness] associated, null if id doesn't match any row
     */
    @Query("SELECT * FROM daily_loneliness WHERE daily_loneliness_id = :id")
    suspend fun getDailyLoneliness(id: Long): DailyLoneliness?

    /**
     * Query the information of the [DailySuicide] by their ID
     * @return The [DailySuicide] associated, null if id doesn't match any row
     */
    @Query("SELECT * FROM daily_suicide WHERE daily_suicide_id = :id")
    suspend fun getDailySuicide(id: Long): DailySuicide?

    /**
     * Query the information of the [DailySymptoms] by their ID
     * @return The [DailySymptoms] associated, null if id doesn't match any row
     */
    @Query("SELECT * FROM daily_symptoms WHERE daily_symptoms_id = :id")
    suspend fun getDailySymptoms(id: Long): DailySymptoms?


    /**
     * -------------------------------------------------------------------------------------------
     * Get All
     * -------------------------------------------------------------------------------------------
     */

    /**
     * Get all [OneOffRound] sorted from the newest to the oldest
     * @return List of [OneOffRound]
     */
    @Query("SELECT * FROM one_off_round ORDER BY created_at DESC")
    suspend fun getAllOneOffRound(): List<OneOffRound>

    /**
     * Get all [OneOffRoundFull] sorted from the newest to the oldest
     * @return List of [OneOffRoundFull]
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
            "ORDER BY one_off_round.created_at DESC")
    suspend fun getAllOneOffRoundFull(): List<OneOffRoundFull>

    /**
     * Get all [DailyRound] sorted from the newest to the oldest
     * @return List of [DailyRound]
     */
    @Query("SELECT * FROM daily_round ORDER BY created_at DESC")
    suspend fun getAllDailyRound(): List<DailyRound>

    /**
     * Get all [DailyRoundFull] sorted from the newest to the oldest
     * @return List of [DailyRoundFull]
     */
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

    /**
     * Get all [OneOffStress] sorted from the newest to the oldest
     * @return List of [OneOffStress]
     */
    @Query("SELECT * FROM one_off_stress ORDER BY one_off_stress_created_at DESC")
    suspend fun getAllOneOffStress(): List<OneOffStress>

    /**
     * Get all [OneOffDepression] sorted from the newest to the oldest
     * @return List of [OneOffDepression]
     */
    @Query("SELECT * FROM one_off_depression ORDER BY one_off_depression_created_at DESC")
    suspend fun getAllOneOffDepression(): List<OneOffDepression>

    /**
     * Get all [OneOffLoneliness] sorted from the newest to the oldest
     * @return List of [OneOffLoneliness]
     */
    @Query("SELECT * FROM one_off_loneliness ORDER BY one_off_loneliness_created_at DESC")
    suspend fun getAllOneOffLoneliness(): List<OneOffLoneliness>

    /**
     * Get all [DailyStress] sorted from the newest to the oldest
     * @return List of [DailyStress]
     */
    @Query("SELECT * FROM daily_stress ORDER BY daily_stress_created_at DESC")
    suspend fun getAllDailyStress(): List<DailyStress>

    /**
     * Get all [DailyDepression] sorted from the newest to the oldest
     * @return List of [DailyDepression]
     */
    @Query("SELECT * FROM daily_depression ORDER BY daily_depression_created_at DESC")
    suspend fun getAllDailyDepression(): List<DailyDepression>

    /**
     * Get all [DailyLoneliness] sorted from the newest to the oldest
     * @return List of [DailyLoneliness]
     */
    @Query("SELECT * FROM daily_loneliness ORDER BY daily_loneliness_created_at DESC")
    suspend fun getAllDailyLoneliness(): List<DailyLoneliness>

    /**
     * Get all [DailySuicide] sorted from the newest to the oldest
     * @return List of [DailySuicide]
     */
    @Query("SELECT * FROM daily_suicide ORDER BY daily_suicide_created_at DESC")
    suspend fun getAllDailySuicide(): List<DailySuicide>

    /**
     * Get all [DailySymptoms] sorted from the newest to the oldest
     * @return List of [DailySymptoms]
     */
    @Query("SELECT * FROM daily_symptoms ORDER BY daily_symptoms_created_at DESC")
    suspend fun getAllDailySymptoms(): List<DailySymptoms>

    /**
     * -------------------------------------------------------------------------------------------
     * Get completed by range
     * -------------------------------------------------------------------------------------------
     */

    /**
     * Get all [OneOffStress] completed, sorted from the newest to the oldest,
     * sorted from the newest to the oldest from the range indicated using modification timestamp
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [OneOffStress]
     */
    @Query("SELECT * " +
            "FROM one_off_stress " +
            "WHERE one_off_stress_completed = 1 " +
            "AND one_off_stress_modified_at BETWEEN :start AND :end " +
            "ORDER BY one_off_stress_created_at DESC")
    suspend fun getAllOneOffStressCompletedFromRange(start: Long, end: Long): List<OneOffStress>

    /**
     * Get all [OneOffDepression] completed, sorted from the newest to the oldest,
     * sorted from the newest to the oldest from the range indicated using modification timestamp
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [OneOffDepression]
     */
    @Query("SELECT * " +
            "FROM one_off_depression " +
            "WHERE one_off_depression_completed = 1 " +
            "AND one_off_depression_modified_at BETWEEN :start AND :end " +
            "ORDER BY one_off_depression_created_at DESC")
    suspend fun getAllOneOffDepressionCompletedFromRange(start: Long, end: Long):
            List<OneOffDepression>

    /**
     * Get all [OneOffLoneliness] completed, sorted from the newest to the oldest,
     * sorted from the newest to the oldest from the range indicated using modification timestamp
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [OneOffLoneliness]
     */
    @Query("SELECT * " +
            "FROM one_off_loneliness " +
            "WHERE one_off_loneliness_completed = 1 " +
            "AND one_off_loneliness_modified_at BETWEEN :start AND :end " +
            "ORDER BY one_off_loneliness_created_at DESC")
    suspend fun getAllOneOffLonelinessCompletedFromRange(start: Long, end: Long):
            List<OneOffLoneliness>

    /**
     * Get all [DailyStress] completed, sorted from the newest to the oldest,
     * sorted from the newest to the oldest from the range indicated using modification timestamp
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [DailyStress]
     */
    @Query("SELECT * " +
            "FROM daily_stress " +
            "WHERE daily_stress_completed = 1 " +
            "AND daily_stress_modified_at BETWEEN :start AND :end " +
            "ORDER BY daily_stress_created_at DESC")
    suspend fun getAllDailyStressCompletedFromRange(start: Long, end: Long): List<DailyStress>

    /**
     * Get all [DailyDepression] completed, sorted from the newest to the oldest,
     * sorted from the newest to the oldest from the range indicated using modification timestamp
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [DailyDepression]
     */
    @Query("SELECT * " +
            "FROM daily_depression " +
            "WHERE daily_depression_completed = 1 " +
            "AND daily_depression_modified_at BETWEEN :start AND :end " +
            "ORDER BY daily_depression_created_at DESC")
    suspend fun getAllDailyDepressionCompletedFromRange(start: Long, end: Long):
            List<DailyDepression>

    /**
     * Get all [DailyLoneliness] completed, sorted from the newest to the oldest,
     * sorted from the newest to the oldest from the range indicated using modification timestamp
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [DailyLoneliness]
     */
    @Query("SELECT * " +
            "FROM daily_loneliness " +
            "WHERE daily_loneliness_completed = 1 " +
            "AND daily_loneliness_modified_at BETWEEN :start AND :end " +
            "ORDER BY daily_loneliness_created_at DESC")
    suspend fun getAllDailyLonelinessCompletedFromRange(start: Long, end: Long):
            List<DailyLoneliness>

    /**
     * Get all [DailySuicide] completed, sorted from the newest to the oldest,
     * sorted from the newest to the oldest from the range indicated using modification timestamp
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [DailySuicide]
     */
    @Query("SELECT * " +
            "FROM daily_suicide " +
            "WHERE daily_suicide_completed = 1 " +
            "AND daily_suicide_modified_at BETWEEN :start AND :end " +
            "ORDER BY daily_suicide_created_at DESC")
    suspend fun getAllDailySuicideCompletedFromRange(start: Long, end: Long): List<DailySuicide>

    /**
     * Get all [DailySymptoms] completed, sorted from the newest to the oldest,
     * sorted from the newest to the oldest from the range indicated using modification timestamp
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [DailySymptoms]
     */
    @Query("SELECT * " +
            "FROM daily_symptoms " +
            "WHERE daily_symptoms_completed = 1 " +
            "AND daily_symptoms_modified_at BETWEEN :start AND :end " +
            "ORDER BY daily_symptoms_created_at DESC")
    suspend fun getAllDailySymptomsCompletedFromRange(start: Long, end: Long): List<DailySymptoms>

    /**
     * -------------------------------------------------------------------------------------------
     * Get All From Range
     * -------------------------------------------------------------------------------------------
     */

    /**
     * Get all [OneOffStress] sorted from the newest to the oldest from the range indicated
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [OneOffStress]
     */
    @Query("SELECT * " +
            "FROM one_off_stress " +
            "WHERE one_off_stress_created_at BETWEEN :start AND :end " +
            "ORDER BY one_off_stress_created_at DESC")
    suspend fun getAllOneOffStressFromRange(start: Long, end: Long): List<OneOffStress>

    /**
     * Get all [OneOffDepression] sorted from the newest to the oldest from the range indicated
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [OneOffDepression]
     */
    @Query("SELECT * " +
            "FROM one_off_depression " +
            "WHERE one_off_depression_created_at BETWEEN :start AND :end " +
            "ORDER BY one_off_depression_created_at DESC")
    suspend fun getAllOneOffDepressionFromRange(start: Long, end: Long): List<OneOffDepression>

    /**
     * Get all [OneOffLoneliness] sorted from the newest to the oldest from the range indicated
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [OneOffLoneliness]
     */
    @Query("SELECT * " +
            "FROM one_off_loneliness " +
            "WHERE one_off_loneliness_created_at BETWEEN :start AND :end " +
            "ORDER BY one_off_loneliness_created_at DESC")
    suspend fun getAllOneOffLonelinessFromRange(start: Long, end: Long): List<OneOffLoneliness>

    /**
     * Get all [DailyStress] sorted from the newest to the oldest from the range indicated
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [DailyStress]
     */
    @Query("SELECT * " +
            "FROM daily_stress " +
            "WHERE daily_stress_created_at BETWEEN :start AND :end " +
            "ORDER BY daily_stress_created_at DESC")
    suspend fun getAllDailyStressFromRange(start: Long, end: Long): List<DailyStress>

    /**
     * Get all [DailyDepression] sorted from the newest to the oldest from the range indicated
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [DailyDepression]
     */
    @Query("SELECT * " +
            "FROM daily_depression " +
            "WHERE daily_depression_created_at BETWEEN :start AND :end " +
            "ORDER BY daily_depression_created_at DESC")
    suspend fun getAllDailyDepressionFromRange(start: Long, end: Long): List<DailyDepression>

    /**
     * Get all [DailyLoneliness] sorted from the newest to the oldest from the range indicated
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [DailyLoneliness]
     */
    @Query("SELECT * " +
            "FROM daily_loneliness " +
            "WHERE daily_loneliness_created_at BETWEEN :start AND :end " +
            "ORDER BY daily_loneliness_created_at DESC")
    suspend fun getAllDailyLonelinessFromRange(start: Long, end: Long): List<DailyLoneliness>

    /**
     * Get all [DailySuicide] sorted from the newest to the oldest from the range indicated
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [DailySuicide]
     */
    @Query("SELECT * " +
            "FROM daily_suicide " +
            "WHERE daily_suicide_created_at BETWEEN :start AND :end " +
            "ORDER BY daily_suicide_created_at DESC")
    suspend fun getAllDailySuicideFromRange(start: Long, end: Long): List<DailySuicide>

    /**
     * Get all [DailySymptoms] sorted from the newest to the oldest from the range indicated
     * @param start Timestamp of the start of the range, expressed in milliseconds
     * @param end Timestamp of the end of the range, expressed in milliseconds
     * @return List of [DailySymptoms]
     */
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

    /**
     * Get all [OneOffRoundFull] with at least one questionnaire uncompleted,
     * sorted from the newest to the oldest
     * @return List of [OneOffRoundFull]
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

    /**
     * Get all [DailyRoundFull] with at least one questionnaire uncompleted,
     * sorted from the newest to the oldest
     * @return List of [DailyRoundFull]
     */
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
     * Get Last
     * -------------------------------------------------------------------------------------------
     */

    /**
     * Get last [OneOffStress]
     * @return [OneOffStress], null if id doesn't match any row
     */
    @Query("SELECT * " +
            "FROM one_off_stress " +
            "ORDER BY one_off_stress_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastOneOffStress(): OneOffStress?

    /**
     * Get last [OneOffDepression]
     * @return [OneOffDepression], null if id doesn't match any row
     */
    @Query("SELECT * " +
            "FROM one_off_depression " +
            "ORDER BY one_off_depression_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastOneOffDepression(): OneOffDepression?

    /**
     * Get last [OneOffLoneliness]
     * @return [OneOffLoneliness], null if id doesn't match any row
     */
    @Query("SELECT * " +
            "FROM one_off_loneliness " +
            "ORDER BY one_off_loneliness_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastOneOffLoneliness(): OneOffLoneliness?

    /**
     * Get last [DailyStress]
     * @return [DailyStress], null if id doesn't match any row
     */
    @Query("SELECT * " +
            "FROM daily_stress " +
            "ORDER BY daily_stress_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastDailyStress(): DailyStress?

    /**
     * Get last [DailyDepression]
     * @return [DailyDepression], null if id doesn't match any row
     */
    @Query("SELECT * " +
            "FROM daily_depression " +
            "ORDER BY daily_depression_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastDailyDepression(): DailyDepression?

    /**
     * Get last [DailyLoneliness]
     * @return [DailyLoneliness], null if id doesn't match any row
     */
    @Query("SELECT * " +
            "FROM daily_loneliness " +
            "ORDER BY daily_loneliness_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastDailyLoneliness(): DailyLoneliness?

    /**
     * Get last [DailySuicide]
     * @return [DailySuicide], null if id doesn't match any row
     */
    @Query("SELECT * " +
            "FROM daily_suicide " +
            "ORDER BY daily_suicide_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastDailySuicide(): DailySuicide?

    /**
     * Get last [DailySymptoms]
     * @return [DailySymptoms], null if id doesn't match any row
     */
    @Query("SELECT * " +
            "FROM daily_symptoms " +
            "ORDER BY daily_symptoms_created_at DESC " +
            "LIMIT 1")
    suspend fun getLastDailySymptoms(): DailySymptoms?


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