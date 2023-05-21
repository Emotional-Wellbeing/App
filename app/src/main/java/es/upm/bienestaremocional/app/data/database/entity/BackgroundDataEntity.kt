package es.upm.bienestaremocional.app.data.database.entity

import java.sql.Timestamp

open class BackgroundDataEntity
{
    open var userid: Long = 0
    open var datatype: String? = null
    open var timestamp: Long = 0
    open var json: String? = null
}