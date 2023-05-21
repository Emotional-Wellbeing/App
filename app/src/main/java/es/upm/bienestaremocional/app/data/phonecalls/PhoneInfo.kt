package es.upm.bienestaremocional.app.data.phonecalls

import android.Manifest.permission.READ_CALL_LOG
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.CallLog.Calls.*
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import es.upm.bienestaremocional.app.data.database.entity.BackgroundDataEntity
import es.upm.bienestaremocional.app.data.info.AppInfo
import es.upm.bienestaremocional.app.data.securePrivateData
import es.upm.bienestaremocional.app.domain.repository.questionnaire.BackgroundRepository
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class PhoneInfo: CoroutineScope {

    lateinit var entity: BackgroundDataEntity

    fun getCallLogs(context: Context)  {
        //check permissions
        if (checkPermissions(context)) {
            val c = context.applicationContext
            val projection = arrayOf(CACHED_NAME, NUMBER, DATE, DURATION)

            val cursor = c.contentResolver.query(
                CONTENT_URI,
                projection,
                null,
                null,
                null,
                null
            )
            cursorToMatrix(cursor)
        }
    }

    private fun cursorToMatrix(cursor: Cursor?): List<List<String?>> {
        val matrix = mutableListOf<List<String?>>()
        cursor?.use {
            while (it.moveToNext()) {
                val list = listOf(
                    securePrivateData(it.getStringFromColumn(CACHED_NAME)),
                    securePrivateData(it.getStringFromColumn(NUMBER)),
                    it.getStringFromColumn(DATE),
                    it.getStringFromColumn(DURATION)
                )
                val json = Gson().toJson(list)
                entity= setEntity(json)
                insert(entity)

            }
        }
        return matrix
    }

    @SuppressLint("Range")
    private fun Cursor.getStringFromColumn(columnName: String) =
        getString(getColumnIndex(columnName))

    private fun checkPermissions(context: Context) : Boolean{
        val permission = ContextCompat.checkSelfPermission(context,
            READ_CALL_LOG)

        return permission == PackageManager.PERMISSION_GRANTED
        }

    private fun setEntity(info: String) : BackgroundDataEntity {
        val entity = BackgroundDataEntity()
        entity.userid = 0
        entity.datatype = "PhoneInfo"
        entity.timestamp = System.currentTimeMillis()
        entity.json = info

        return entity
    }

    fun insert(entity: BackgroundDataEntity) {
        (null as BackgroundRepository<BackgroundDataEntity>?)?.insert(entity)
    }

    override val coroutineContext: CoroutineContext
        get() = TODO("Not yet implemented")

}