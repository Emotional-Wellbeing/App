package es.upm.bienestaremocional.domain.repository

import es.upm.bienestaremocional.data.database.entity.LastUpload

interface LastUploadRepository {
    suspend fun insert(lastUpload: LastUpload): Long
    suspend fun update(lastUpload: LastUpload)
    suspend fun get(type: LastUpload.Type): LastUpload?
}