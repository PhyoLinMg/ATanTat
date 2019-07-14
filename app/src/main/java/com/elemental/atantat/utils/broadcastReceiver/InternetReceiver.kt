package com.elemental.atantat.utils.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.utils.NetworkUtil

class InternetReceiver(val context: Context): BroadcastReceiver() {
    private val db:AtanTatDatabase= AtanTatDatabase.invoke(context)

    override fun onReceive(context: Context?, intent: Intent?) {
        val networkUtil= NetworkUtil()
        var status:String?=networkUtil.getConnectivityStatusString(context!!)
        if(status!!.isEmpty()){
            status="No Internet Connection"
            Toast.makeText(context,"nice job done",Toast.LENGTH_SHORT).show()
        }
        else if(status=="Mobile data enabled" ||status=="Wifi enabled"){
            Toast.makeText(context,"good condition",Toast.LENGTH_SHORT).show()
        }


    }
}