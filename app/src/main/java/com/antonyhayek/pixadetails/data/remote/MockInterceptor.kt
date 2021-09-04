package com.antonyhayek.pixadetails.data.remote

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {



    override fun intercept(chain: Interceptor.Chain): Response {

            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.endsWith(ApiEndPoints.LOGIN) -> getLoginSuccessJson
                uri.endsWith(ApiEndPoints.REGISTER) -> getRegisterSuccessJson
                else -> ""
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
    }
}

const val getLoginSuccessJson = """
{
  "status": "success",
  "data": {
    "password": "abc1233",
    "email": "abc@gmail.com"
  },
  "message": ""
}"""


const val getRegisterSuccessJson = """
{
  "status": "success",
  "data": {
    "password": "abc1233",
    "email": "abc@gmail.com",
    "age": 22
  },
  "message": ""
}"""