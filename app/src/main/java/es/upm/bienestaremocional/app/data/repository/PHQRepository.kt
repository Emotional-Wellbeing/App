package es.upm.bienestaremocional.app.data.repository

import es.upm.bienestaremocional.app.data.database.entity.PHQ

interface PHQRepository 
{
    suspend fun insert(phq: PHQ) : Long
    suspend fun update(phq: PHQ)
    suspend fun getAll(): List<PHQ>
    suspend fun get(id: Long): PHQ
}