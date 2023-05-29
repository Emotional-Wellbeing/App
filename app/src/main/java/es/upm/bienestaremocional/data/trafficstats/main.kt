package es.upm.bienestaremocional.data.trafficstats

import android.annotation.SuppressLint
import android.net.TrafficStats

class Traffic{

    fun init(): String {
        if (TrafficStats.getTotalRxBytes() != TrafficStats.UNSUPPORTED.toLong() && TrafficStats.getTotalTxBytes() != TrafficStats.UNSUPPORTED.toLong()) {
            return run()
        }
        return "[\"WiFI\": \"N/A Kb\", \"Mobile\": \"N/A Kb\"]"
    }

    @SuppressLint("SetTextI18n")
    fun run(): String {
        val mobile = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes()
        val total = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes()
        val wiFi: Long = (total - mobile) / 1024
        val mobileData: Long = mobile / 1024

        return "[\"WiFI\": \"$wiFi Kb\", \"Mobile\": \"$mobileData Kb\"]"
    }

}