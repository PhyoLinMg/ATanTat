package com.elemental.atantat.utils.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.elemental.atantat.data.models.Times
import com.elemental.atantat.db.AtanTatDatabase
import java.util.*

class NotiReceiver(val context: Context): BroadcastReceiver() {
    private val db:AtanTatDatabase= AtanTatDatabase.invoke(context)
    private val times: MutableList<Times> = ArrayList()

    override fun onReceive(context: Context?, intent: Intent?) {
        val hour=getHourofDay()
        val min=getMinute()
        times.addAll(db.PeriodDao().times())
        times.forEach {
            Log.d("times",it.toString())
        }

    }
    fun getHourofDay(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    fun getMinute(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MINUTE)
    }

}