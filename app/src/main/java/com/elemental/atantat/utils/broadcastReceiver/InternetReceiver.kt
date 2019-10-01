package com.elemental.atantat.utils.broadcastReceiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.elemental.atantat.data.models.YesNo
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.services.MainService
import com.elemental.atantat.usecases.PostAttendanceUseCase
import com.elemental.atantat.utils.NetworkUtil
import com.elemental.atantat.utils.SharedPreference
import org.jetbrains.anko.doAsync
import kotlin.coroutines.CoroutineContext

class InternetReceiver(val context: Context): BroadcastReceiver() {


    private val yesno: MutableList<YesNo> = ArrayList()
    private lateinit var sharedPreference:SharedPreference
    private lateinit var  postAttendanceUseCase:PostAttendanceUseCase

    private lateinit var yesNo: YesNo

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        val networkUtil = NetworkUtil()
        var status: String? = networkUtil.getConnectivityStatusString(context!!)
        if (status!!.isEmpty()) {

        } else if (status == "Mobile data enabled" || status == "Wifi enabled") {
            val db: AtanTatDatabase = AtanTatDatabase.invoke(context)
            sharedPreference= SharedPreference(context)
            postAttendanceUseCase = PostAttendanceUseCase(context, sharedPreference)
            doAsync {
                val api = MainService.invoke(
                    ConnectivityInterceptorImpl(context),
                    sharedPreference.getValueString("token")!!
                )
                Log.d("api", api.toString())
                Log.d("token",sharedPreference.getValueString("token"))
                val yesNo = db.SubjectDao().getattendence()
                yesNo.forEach {
                    val yesno = YesNo(it.id, it.yes, it.no)
                    postAttendanceUseCase.go(yesno)
                }

            }
        }

    }
}