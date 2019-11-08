package com.ugc.onlineshoping.module

import android.util.Base64
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val AUTH = "Basic "+ Base64.encodeToString("Aher:123456".toByteArray(), Base64.NO_WRAP)

    private const val  SHOE_URL:String ="http://192.168.1.213/online_shopping/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{chain ->
            val original  = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method, original.body)

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance : ApiInterface by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(SHOE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(ApiInterface::class.java)
    }

}
