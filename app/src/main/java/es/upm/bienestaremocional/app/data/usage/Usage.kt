package es.upm.bienestaremocional.app.data.usage

import android.app.Activity
import android.app.usage.UsageStatsManager
import android.app.usage.UsageStats
import android.content.Context
import android.view.ViewGroup
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.gson.Gson
import java.util.*

data class PackageData(
    val packageName: String,
    val beginTimeStamp: Long,
    val endTimeStamp: Long,
    val lastTimeUsed: Long,
    val lastTimeVisible: Long,
    val launchCount: Int,
    val totaltimeVisible: Long,
    val type: String
)

class Usage : Activity(), AdapterView.OnItemSelectedListener {
    private var mUsageStatsManager: UsageStatsManager? = null
    private var mInflater: LayoutInflater? = null
    private var mAdapter: Usage.UsageStatsAdapter? = null
    private var mPm: PackageManager? = null
    //private var client : Client? = null

    internal inner class UsageStatsAdapter : BaseAdapter() {
        private var mDisplayOrder = 0
        private val mPackageStats = ArrayList<UsageStats>()

        override fun getCount(): Int {
            return mPackageStats.size
        }

        override fun getItem(position: Int): Any {
            return mPackageStats[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {

            return convertView
        }

        fun sortList(sortOrder: Int) {
            if (mDisplayOrder == sortOrder) {
                // do nothing
                return
            }
            mDisplayOrder = sortOrder

        }

        init {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, -5)
            val stats = mUsageStatsManager!!.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST,
                cal.timeInMillis, System.currentTimeMillis()
            )

            if (stats != null) {

                val statCount = stats.size

                for (i in 0 until statCount) {
                    val pkgStats = stats[i]
                    val type = findApp(pkgStats.packageName)
                    if (type != "")
                    {
                        val info = PackageData(pkgStats.packageName,
                            pkgStats.firstTimeStamp,
                            pkgStats.lastTimeStamp,
                            pkgStats.lastTimeUsed,
                            pkgStats.lastTimeVisible,
                            0,
                            pkgStats.totalTimeVisible,
                            type)
                        val json = Gson().toJson(info)
                        //client?.send(json)
                        //client.run()
                    }
                }
            }
        }
    }

    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        mAdapter!!.sortList(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // do nothing
    }
    //fun getAppUsage(contexto: Context, cliente: Client) {
    fun getAppUsage(contexto: Context) {
        mUsageStatsManager = contexto.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        requestPermissions(contexto)
        mInflater = contexto.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mPm = contexto.packageManager

        mAdapter = UsageStatsAdapter()
    }

    fun requestPermissions(contexto: Context) {
        val stats = mUsageStatsManager
            ?.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, 0, System.currentTimeMillis())
        val isEmpty = stats?.isEmpty()// aquí ya están los valores
        if (isEmpty == true) {
            contexto.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }
    fun findApp (appName: String): String {
        val type = ""
        val rRSS = listOf("facebook","twitter","instagram","tiktok","snapchat","whatsapp")
        val games = listOf("candycrush","sudoku","game","pokemongo","impact")
        val entertaining = listOf("youtube","netflix","hbo","disney","prime","video","tiktok")

        val rRSSCount = rRSS.size
        for (i in 0 until rRSSCount) {
            if (appName.contains(rRSS[i]))
                return "RRSS"
        }
        val gamesCount = games.size
        for (i in 0 until gamesCount) {
            if (appName.contains(games[i]))
                return "games"
        }
        val entertainingCount = entertaining.size
        for (i in 0 until entertainingCount) {
            if (appName.contains(entertaining[i]))
                 return "entertaining"
        }

        return type
    }
}