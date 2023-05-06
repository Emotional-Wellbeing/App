package es.upm.bienestaremocional.app.data.trafficstats

import android.annotation.SuppressLint
import android.widget.ArrayAdapter
import android.net.TrafficStats
import android.view.ViewGroup
import android.view.LayoutInflater
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import es.upm.bienestaremocional.R

class Traffic : AppCompatActivity() {
    private var dataUsageTotalLast: Long = 0
    var adapterApplications: ArrayAdapter<ApplicationItem?>? = null
    fun init() {
        if (TrafficStats.getTotalRxBytes() != TrafficStats.UNSUPPORTED.toLong() && TrafficStats.getTotalTxBytes() != TrafficStats.UNSUPPORTED.toLong()) {
            handler.postDelayed(runnable, 0)
            initAdapter()
        }
    }

    var handler = Handler()
    var runnable: Runnable = object : Runnable {
        @SuppressLint("SetTextI18n")
        override fun run() {
            val mobile = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes()
            val total = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes()
            //   tvDataUsageWiFi!!.text = "" + (total - mobile) / 1024 + " Kb"
            //   tvDataUsageMobile!!.text = "" + mobile / 1024 + " Kb"//esto es solo para imprimirlo
            //   tvDataUsageTotal!!.text = "" + total / 1024 + " Kb"
            if (dataUsageTotalLast != total) {
                dataUsageTotalLast = total
                //
                updateAdapter()
            }
            handler.postDelayed(this, 5000)
        }
    }

    fun initAdapter() {
        adapterApplications = object :
            ArrayAdapter<ApplicationItem?>(applicationContext, R.layout.item_install_application) {
            @SuppressLint("SetTextI18n")
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val app = getItem(position)
                val result: View = (convertView
                    ?: LayoutInflater.from(parent.context)) as View

                return result
            }

        }

        for (app in applicationContext.packageManager.getInstalledApplications(0)) {
            val item: ApplicationItem? = ApplicationItem.create(app)
            if (item != null) {
                (adapterApplications as ArrayAdapter<ApplicationItem?>).add(item)
            }
        }
    }

    fun updateAdapter() {
        var i = 0
        val l = adapterApplications!!.count
        while (i < l) {
            val app = adapterApplications!!.getItem(i)
            app!!.update()
            i++
        }
        adapterApplications!!.sort { lhs, rhs -> (rhs!!.totalUsageKb - lhs!!.totalUsageKb) }
        adapterApplications!!.notifyDataSetChanged() //esto creo q para ver cuánto ha consumido cada una, prescindible...
    }
}