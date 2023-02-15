package es.upm.bienestaremocional.app.data.repository

import es.upm.bienestaremocional.app.data.database.entity.UCLA

interface UCLARepository
{
    suspend fun insert(ucla: UCLA) : Long
    suspend fun update(ucla: UCLA)
    suspend fun getAll(): List<UCLA>
    suspend fun get(id: Long): UCLA
}