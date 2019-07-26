package com.elemental.atantat.utils.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.elemental.atantat.data.models.Times
import com.elemental.atantat.db.AtanTatDatabase
import java.util.*

class NotiReceiver: BroadcastReceiver() {
   // private val db:AtanTatDatabase= AtanTatDatabase.invoke(context)
    private val times: MutableList<Times> = ArrayList()

    override fun onReceive(context: Context?, intent: Intent?) {
        val hour=getHourofDay()
        val min=getMinute()
        Toast.makeText(context,"Received",Toast.LENGTH_SHORT).show()

    }

    private fun getHourofDay(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    private fun getMinute(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MINUTE)
    }

}