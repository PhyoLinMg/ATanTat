package com.elemental.atantat.network

import android.content.Context
import android.net.ConnectivityManager
import com.elemental.atantat.network.ConnectivityInterceptor
import com.elemental.atantat.network.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(context: Context): ConnectivityInterceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }
    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager
        val netWorkInfo = connectivityManager.activeNetworkInfo
        return netWorkInfo != null && netWorkInfo.isConnected
    }
}