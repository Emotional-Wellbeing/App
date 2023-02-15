package es.upm.bienestaremocional.app.data.repository

import es.upm.bienestaremocional.app.data.database.entity.PSS

interface PSSRepository
{
    suspend fun insert(pss: PSS) : Long
    suspend fun update(pss: PSS)
    suspend fun getAll(): List<PSS>
    suspend fun get(id: Long): PSS
}