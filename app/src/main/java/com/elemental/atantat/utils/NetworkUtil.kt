package com.elemental.atantat.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService



class NetworkUtil {
    fun getConnectivityStatusString(context: Context): String? {
        var status: String? = null
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled"
                return status
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled"
                return status
            }
        } else {
            status = "No internet is available"
            return status
        }
        return status
    }
}