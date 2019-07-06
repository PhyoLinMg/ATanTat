package com.elemental.atantat.utils.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.elemental.atantat.utils.NetworkUtil

class InternetReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val networkUtil= NetworkUtil()
        var status:String?=networkUtil.getConnectivityStatusString(context!!)
        if(status!!.isEmpty()){
            status="No Internet Connection"
        }

        Toast.makeText(context,status,Toast.LENGTH_LONG).show()
    }
}