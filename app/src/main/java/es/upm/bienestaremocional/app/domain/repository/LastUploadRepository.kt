package es.upm.bienestaremocional.app.domain.repository

import es.upm.bienestaremocional.app.data.database.entity.LastUpload

interface LastUploadRepository {
    suspend fun insert(lastUpload: LastUpload) : Long
    suspend fun update(lastUpload: LastUpload)
    suspend fun get(type: LastUpload.Type) : LastUpload?
}