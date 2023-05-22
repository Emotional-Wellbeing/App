package es.upm.bienestaremocional.app.data.trafficstats

import android.annotation.SuppressLint
import android.net.TrafficStats
import com.google.gson.Gson
import es.upm.bienestaremocional.app.data.database.entity.BackgroundDataEntity
import es.upm.bienestaremocional.app.domain.repository.questionnaire.BackgroundRepository
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class Traffic : CoroutineScope {

    lateinit var entity: BackgroundDataEntity
    fun init() {
        if (TrafficStats.getTotalRxBytes() != TrafficStats.UNSUPPORTED.toLong() && TrafficStats.getTotalTxBytes() != TrafficStats.UNSUPPORTED.toLong()) {
            run()
        }
    }
        @SuppressLint("SetTextI18n")
  fun run() {
            val mobile = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes()
            val total = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes()
            val wiFi: Long = (total - mobile) / 1024
            val mobileData: Long = mobile / 1024
            val info: List<String?> = listOf("WiFI:$wiFi Kb","Mobile:$mobileData Kb")
            val json = Gson().toJson(info)
            entity= setEntity(json)
            insert(entity)

        }

    private fun setEntity(info: String) : BackgroundDataEntity {
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