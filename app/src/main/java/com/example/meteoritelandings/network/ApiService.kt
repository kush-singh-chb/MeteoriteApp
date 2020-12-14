package com.example.meteoritelandings.network

import com.example.meteoritelandings.Utils.Constants.BASE_URL
import com.example.meteoritelandings.models.Meteorite
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Kush Singh Chibber on 14-12-2020.
 */
interface ApiService {
    @GET("y77d-th95.json")
    fun retrieveLandedMeteorites(
        @Query("\$\$app_token") appToken: String,
        @Query("\$where") where: String,
        @Query("\$order") order: String
    )
            : Single<List<Meteorite>>

    companion object {

        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .baseUrl(BASE_URL)
                .client(createHttpClient())
                .build()

            return retrofit.create(ApiService::class.java)
        }

        private fun createGson(): Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

        private fun createHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }
    }

}