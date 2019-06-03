package com.elemental.atantat.network

import com.elemental.atantat.data.user_panel.LoginResponse
import com.elemental.atantat.data.user_panel.SignUpResponse
import com.elemental.atantat.utils.Const.LOCAL_API_END
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST


interface MainService {


    companion object {
       operator fun invoke(
           connectivityInterceptor: ConnectivityInterceptor
       ) : MainService {

           val requestInterceptor = Interceptor { chain ->
               val request = chain.request()
                   .newBuilder()
                   .addHeader("Authorization","")
                   .build()

               return@Interceptor chain.proceed(request)
           }

           val okHttpClient = OkHttpClient.Builder()
//               .addInterceptor(requestInterceptor)
               .addInterceptor(connectivityInterceptor)
               .build()

           return Retrofit.Builder()
               .baseUrl(LOCAL_API_END)
               .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(CoroutineCallAdapterFactory())
               .client(okHttpClient)
               .build()
               .create(MainService::class.java)
       }
    }
}