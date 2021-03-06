package com.elemental.atantat.network.services

import com.elemental.atantat.data.responses.LoginResponse
import com.elemental.atantat.data.models.LoginUser
import com.elemental.atantat.data.responses.SignUpResponse
import com.elemental.atantat.data.models.SignUpUser
import com.elemental.atantat.data.models.YesNo
import com.elemental.atantat.data.responses.AttendanceResponse
import com.elemental.atantat.network.ConnectivityInterceptor
import com.elemental.atantat.utils.Const
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface UserLoginSignUpInterface {


    @POST("login")
    fun login(@Body loginUser: LoginUser): Deferred<Response<LoginResponse>>

    @POST("signup")
    fun signup(@Body signUpUser: SignUpUser): Deferred<Response<SignUpResponse>>


    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ) : UserLoginSignUpInterface {

            val requestInterceptor = Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("X-Requested-With","XMLHttpRequest")
                    .addHeader("Content-Type","application/json")
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
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(UserLoginSignUpInterface::class.java)
        }
    }
}