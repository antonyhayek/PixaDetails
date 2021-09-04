package com.antonyhayek.pixadetails.data.remote

import com.antonyhayek.pixadetails.data.remote.responses.PixabayResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {


    @GET("?key=23213144-40e4806fb725ffdd769da4530&category=computer") /*&image_type=photo*/
    suspend fun getPixabayImages(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ) : PixabayResponse


    companion object{

        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ApiInterface{

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://pixabay.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }

}