package es.upm.bienestaremocional.app.data.trafficstats

import android.annotation.SuppressLint
import android.net.TrafficStats
import com.google.gson.Gson

class Traffic{

    fun init(): String? {
        if (TrafficStats.getTotalRxBytes() != TrafficStats.UNSUPPORTED.toLong() && TrafficStats.getTotalTxBytes() != TrafficStats.UNSUPPORTED.toLong()) {
            return run()
        }
        return ""
    }
        @SuppressLint("SetTextI18n")
  fun run(): String? {
            val mobile = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes()
            val total = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes()
            val wiFi: Long = (total - mobile) / 1024
            val mobileData: Long = mobile / 1024
            val info: List<String?> = listOf("WiFI:$wiFi Kb","Mobile:$mobileData Kb")
            val json = Gson().toJson(info)
            //entity= setEntity(json)
            //insert(entity)
            return json

        }

}