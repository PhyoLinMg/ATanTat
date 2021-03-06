package com.elemental.atantat.network.services

import com.elemental.atantat.data.models.Periods
import com.elemental.atantat.data.models.Profile
import com.elemental.atantat.data.models.Subjects
import com.elemental.atantat.data.models.YesNo
import com.elemental.atantat.data.responses.AttendanceResponse
import com.elemental.atantat.data.responses.ProfileResponse
import com.elemental.atantat.network.ConnectivityInterceptor
import com.elemental.atantat.utils.Const
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface MainService{

    @GET("periods")
    fun getPeriodsAsync():Deferred<Response<Periods>>

    @GET("subjects")
    fun getSubjectsAsync():Deferred<Response<Subjects>>

    @POST("attendances")
    fun postattendancesAsync(@Body yesNo: YesNo): Deferred<Response<AttendanceResponse>>

    @GET("user")
    fun getUserAsync():Deferred<Response<Profile>>

    companion object {
       operator fun invoke(
           connectivityInterceptor: ConnectivityInterceptor, token:String
       ) : MainService {


           val requestInterceptor = Interceptor { chain ->
               val request = chain.request()
                   .newBuilder()
                   .addHeader("Authorization","Bearer "+token)
                   .build()

               return@Interceptor chain.proceed(request)
           }

           val okHttpClient = OkHttpClient.Builder()
              .addInterceptor(requestInterceptor)
               .addInterceptor(connectivityInterceptor)
               .build()


           return Retrofit.Builder()
               .baseUrl(Const.ONLINE_API_END)
               .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(CoroutineCallAdapterFactory())
               .client(okHttpClient)
               .build()
               .create(MainService::class.java)
       }
    }
}