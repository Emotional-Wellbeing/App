package es.upm.bienestaremocional.app.data.phonecalls

import android.Manifest.permission.READ_CALL_LOG
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.CallLog.Calls.*
import androidx.core.content.ContextCompat
import es.upm.bienestaremocional.app.data.securePrivateData

class PhoneInfo {

    fun getCallLogs(context: Context): String {
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
            return cursorToList(cursor)
        }
        return "N/A"
    }

    private fun cursorToList(cursor: Cursor?): String {
        var message = "{"
        cursor?.use {
            while (it.moveToNext()) {
                var json : String = "[\"Name\": " + securePrivateData(it.getStringFromColumn(CACHED_NAME))+
                        ", \"Number\": "+ securePrivateData(it.getStringFromColumn(NUMBER))+
                        ", \"Date\": "+ it.getStringFromColumn(DATE)+
                        ", \"Duration\": "+ it.getStringFromColumn(DURATION)+
                        "]"

                if (it.moveToNext())
                    json += ","

                message += json
            }
            message += "}"
        }
        return message
    }

    @SuppressLint("Range")
    private fun Cursor.getStringFromColumn(columnName: String) =
        getString(getColumnIndex(columnName))

    private fun checkPermissions(context: Context) : Boolean{
        val permission = ContextCompat.checkSelfPermission(context,
            READ_CALL_LOG)

        return permission == PackageManager.PERMISSION_GRANTED
    }
}