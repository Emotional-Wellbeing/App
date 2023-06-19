package es.upm.bienestaremocional.data.usage

import android.app.Activity
import android.app.usage.UsageStatsManager
import android.app.usage.UsageStats
import android.content.Context
import android.view.ViewGroup
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import java.util.*

class Usage : Activity(), AdapterView.OnItemSelectedListener{
    private var mUsageStatsManager: UsageStatsManager? = null
    private var mInflater: LayoutInflater? = null
    private var mAdapter: Usage.UsageStatsAdapter? = null
    private var mPm: PackageManager? = null

    var usageInfo: String =""

    @RequiresApi(Build.VERSION_CODES.Q)
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
                    var message: String
                    if ((type != "") && (pkgStats.totalTimeVisible >0)) {
                        if (usageInfo != "")
                            usageInfo += ", "
                        message = "\"App\": { \"AppName\": \"" + pkgStats.packageName +
                                "\", \"firstTimeStamp\": " + pkgStats.firstTimeStamp +
                                ", \"lastTimeStamp\": " + pkgStats.lastTimeStamp +
                                ", \"lastTimeUsed\": " + pkgStats.lastTimeUsed +
                                ", \"lastTimeVisible\": " + pkgStats.lastTimeVisible +
                                ", \"totalTimeVisible\": " + pkgStats.totalTimeVisible +
                                ", \"AppType\": \"" + type + "\"}"

                        usageInfo += message
                    }
                }
            }
        }
    }

    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        mAdapter!!.sortList(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // do nothing
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getAppUsage(contexto: Context): String {
        mUsageStatsManager = contexto.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        requestPermissions(contexto)
        mInflater = contexto.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mPm = contexto.packageManager

        mAdapter = UsageStatsAdapter()

        if (usageInfo != "")
            return usageInfo

        return "\"App\": \"N/A\""
    }

    private fun requestPermissions(contexto: Context) {
        val stats = mUsageStatsManager
            ?.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, 0, System.currentTimeMillis())
         if (stats?.isEmpty() == true) {
            contexto.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }
    fun findApp (appName: String): String {
        val type = ""
        val rRSS = listOf("facebook","twitter","instagram","tiktok","snapchat","whatsapp","messenger","telegram")
        val dating = listOf("tinder","badoo","meetic","bumble","grindr")
        val games = listOf("candy","mine","treasure","crush","sudoku","game","pokemongo","impact","scape","among","otome","madness","zombies")
        val entertaining = listOf("youtube","netflix","hbo","disney","prime","video","ivoox","tiktok","audible","book","star","crunchyroll", "firefox", "opera","chrome","9gag","los40","spotify","rtve","bbc","duolingo")
        val house = listOf("santander","bbva","bankinter","openbank","repsol","naturgy","iberdrola","tapo","tplink","aeat","amazon","cl@ve","sodexo","zooplus","wallapop")
        val work = listOf("office","word","excel","powerpoint","authenticator","teams","slack","zoom","moodle")

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
        val datingCount = dating.size
        for (i in 0 until datingCount) {
            if (appName.contains(dating[i]))
                 return "dating"
        }
        val houseCount = house.size
        for (i in 0 until houseCount) {
            if (appName.contains(house[i]))
                return "house"
        }
        val workCount = work.size
        for (i in 0 until workCount) {
            if (appName.contains(work[i]))
                return "work"
        }
        val entertainingCount = entertaining.size
        for (i in 0 until entertainingCount) {
            if (appName.contains(entertaining[i]))
                return "entertaining"
        }

        return type
    }


}