package es.upm.bienestaremocional.app.data.trafficstats

import android.annotation.SuppressLint
import android.widget.ArrayAdapter
import android.net.TrafficStats
import android.view.ViewGroup
import android.view.LayoutInflater
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.database.entity.BackgroundDataEntity
import es.upm.bienestaremocional.app.domain.repository.questionnaire.BackgroundRepository
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class Traffic : AppCompatActivity(), CoroutineScope {
    private var dataUsageTotalLast: Long = 0
    var adapterApplications: ArrayAdapter<ApplicationItem?>? = null
    lateinit var entity: BackgroundDataEntity
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
            val wiFi: Long = (total - mobile) / 1024
            val mobileData: Long = mobile / 1024
            val info: List<String?> = listOf("WiFI:$wiFi Kb","Mobile:$mobileData Kb")
            val json = Gson().toJson(info)
            entity= setEntity(json)
            insert(entity)

            if (dataUsageTotalLast != total) {
                dataUsageTotalLast = total
                //
                updateAdapter()
            }
            handler.postDelayed(this, 5000)
        }
    }

    private fun initAdapter() {
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
        adapterApplications!!.notifyDataSetChanged()
    }


    fun setEntity(info: String) : BackgroundDataEntity {
        val entity = BackgroundDataEntity()
        entity.userid = 0
        entity.datatype = "Internet"
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