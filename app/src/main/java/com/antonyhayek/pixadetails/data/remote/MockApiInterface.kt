package com.antonyhayek.pixadetails.data.remote

import com.antonyhayek.pixadetails.data.remote.requests.LoginRequest
import com.antonyhayek.pixadetails.data.remote.requests.RegisterRequest
import com.antonyhayek.pixadetails.data.remote.responses.BaseResponse
import com.antonyhayek.pixadetails.data.remote.responses.LoginResponse
import com.antonyhayek.pixadetails.data.remote.responses.RegisterResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MockApiInterface {

    @POST(ApiEndPoints.LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest) : Response<BaseResponse<LoginResponse>>

    @POST(ApiEndPoints.REGISTER)
    suspend fun register(@Body registerRequest: RegisterRequest) : Response<BaseResponse<RegisterResponse>>


    companion object{

        operator fun invoke(
            mockInterceptor: MockInterceptor
        ) : MockApiInterface{

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(mockInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("http://www.mocky.io/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MockApiInterface::class.java)
        }
    }

}


