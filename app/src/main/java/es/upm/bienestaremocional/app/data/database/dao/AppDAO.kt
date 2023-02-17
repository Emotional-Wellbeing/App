package es.upm.bienestaremocional.app.data.database.dao

import androidx.room.*
import es.upm.bienestaremocional.app.data.database.entity.*

@Dao
interface AppDAO
{
    @Insert
    suspend fun insert(questionnaireRound: QuestionnaireRound) : Long

    @Insert
    suspend fun insert(pss: PSS) : Long

    @Insert
    suspend fun insert(phq: PHQ) : Long

    @Insert
    suspend fun insert(ucla: UCLA) : Long

    @Update
    suspend fun update(questionnaireRound: QuestionnaireRound)

    @Update
    suspend fun update(pss: PSS)

    @Update
    suspend fun update(phq: PHQ)

    @Update
    suspend fun update(ucla: UCLA)

    @Query("SELECT * FROM questionnaire_round ORDER BY created_at DESC")
    suspend fun getAllQuestionnaireRound(): List<QuestionnaireRound>

    @Query("SELECT * FROM pss ORDER BY pss_created_at DESC")
    suspend fun getAllPSS(): List<PSS>

    @Query("SELECT * FROM phq ORDER BY phq_created_at DESC")
    suspend fun getAllPHQ(): List<PHQ>

    @Query("SELECT * FROM ucla ORDER BY ucla_created_at DESC")
    suspend fun getAllUCLA(): List<UCLA>

    @Query("SELECT * FROM questionnaire_round WHERE id = :id")
    suspend fun getQuestionnaireRound(id: Long): QuestionnaireRound

    @Query("SELECT * FROM pss WHERE pss_id = :id")
    suspend fun getPSS(id: Long): PSS

    @Query("SELECT * FROM phq WHERE phq_id = :id")
    suspend fun getPHQ(id: Long): PHQ

    @Query("SELECT * FROM ucla WHERE ucla_id = :id")
    suspend fun getUCLA(id: Long): UCLA

    @Transaction
    @Query("SELECT qr.*, " +
            "pss.*, " +
            "phq.*, " +
            "ucla.* " +
            "FROM questionnaire_round as qr " +
            "LEFT JOIN pss ON pss.pss_id = qr.pss_id " +
            "LEFT JOIN phq ON phq.phq_id = qr.phq_id " +
            "LEFT JOIN ucla ON ucla.ucla_id = qr.ucla_id")
    suspend fun getAllQuestionnaireRoundFull(): List<QuestionnaireRoundFull>

    @Transaction
    @Query("SELECT qr.*, " +
            "pss.*, " +
            "phq.*, " +
            "ucla.* " +
            "FROM questionnaire_round as qr " +
            "LEFT JOIN pss ON pss.pss_id = qr.pss_id " +
            "LEFT JOIN phq ON phq.phq_id = qr.phq_id " +
            "LEFT JOIN ucla ON ucla.ucla_id = qr.ucla_id " +
            "WHERE qr.id = :id")
    suspend fun getQuestionnaireRoundFull(id: Long): QuestionnaireRoundFull
}