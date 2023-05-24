package es.upm.bienestaremocional.app.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Mapping of PHQ table
 */
@Entity(tableName = "bgdata")
data class BackgroundData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_user")
    override var userid: Long = 0,

    @ColumnInfo(name = "datatype")
    override var datatype: String? = null,

    @ColumnInfo(name = "time")
    override var timestamp: Long,

    @ColumnInfo(name = "data")
    override var json: String? = null

) : BackgroundDataEntity()
