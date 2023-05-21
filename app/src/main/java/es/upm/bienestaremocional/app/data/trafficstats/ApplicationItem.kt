package es.upm.bienestaremocional.app.data.trafficstats

import android.net.TrafficStats
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import kotlin.math.roundToInt

class ApplicationItem(private val app: ApplicationInfo) { //por aquí no estamos pasando...
    private var tx: Long = 0
    private var rx: Long = 0
    private var wifi_tx: Long = 0
    private var wifi_rx: Long = 0
    private var mobil_tx: Long = 0
    private var mobil_rx: Long = 0
    private var current_tx: Long = 0
    private var current_rx: Long = 0
    private var isMobil = false
    fun update() {
        val delta_tx = TrafficStats.getUidTxBytes(app.uid) - tx
        val delta_rx = TrafficStats.getUidRxBytes(app.uid) - rx
        tx = TrafficStats.getUidTxBytes(app.uid)
        rx = TrafficStats.getUidRxBytes(app.uid)
        current_tx += delta_tx
        current_rx += delta_rx
        if (isMobil) {
            mobil_tx += delta_tx
            mobil_rx += delta_rx
        } else {
            wifi_tx += delta_tx
            wifi_rx += delta_rx
        }
    }

    val totalUsageKb: Int
        get() = ((tx + rx) / 1024).toFloat().roundToInt()

    fun getApplicationLabel(_packageManager: PackageManager): String {
        return _packageManager.getApplicationLabel(app).toString()
    }

    fun getIcon(_packageManager: PackageManager): Drawable {
        return _packageManager.getApplicationIcon(app)
    }

    fun setMobilTraffic(_isMobil: Boolean) {
        isMobil = _isMobil
    }

    companion object {
        fun create(_app: ApplicationInfo): ApplicationItem? {
            val _tx = TrafficStats.getUidTxBytes(_app.uid)
            val _rx = TrafficStats.getUidRxBytes(_app.uid)
            return if (_tx + _rx > 0) ApplicationItem(_app) else null
        }
    }

    init {
        update()
    }
}