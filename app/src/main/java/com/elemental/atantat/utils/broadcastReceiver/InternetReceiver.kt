package com.elemental.atantat.utils.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.elemental.atantat.data.models.YesNo
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.usecases.PostAttendanceUseCase
import com.elemental.atantat.utils.NetworkUtil
import org.jetbrains.anko.doAsync
import kotlin.coroutines.CoroutineContext

class InternetReceiver(val context: Context): BroadcastReceiver() {
    private val db:AtanTatDatabase= AtanTatDatabase.invoke(context)
    private val yesno: MutableList<YesNo> = ArrayList()

    override fun onReceive(context: Context?, intent: Intent?) {
        val networkUtil= NetworkUtil()
        var status:String?=networkUtil.getConnectivityStatusString(context!!)
        if(status!!.isEmpty()){
            Log.d("empty","It is empty ty")
        }
        else if(status=="Mobile data enabled" || status=="Wifi enabled"){
            post()
        }
    }
    private fun post(){
        doAsync {
            if(yesno.isEmpty()){
                yesno.addAll(db.SubjectDao().getattendence())
            }
        }
        val postAttendanceUseCase=PostAttendanceUseCase(context)
        postAttendanceUseCase.go(yesno)
        Log.d("gg",yesno.toString())

    }
}