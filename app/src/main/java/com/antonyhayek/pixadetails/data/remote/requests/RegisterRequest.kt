package com.antonyhayek.pixadetails.data.remote.requests

data class RegisterRequest(
    val email: String,
    val password: String,
    val age: Int
)