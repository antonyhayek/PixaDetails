package com.antonyhayek.pixadetails.data.remote.responses

data class BaseResponse<T>(
    val status: String,
    val message: String,
    val data: T
)


