package com.antonyhayek.pixadetails.data.repositories

import com.antonyhayek.pixadetails.data.base.BaseRepository
import com.antonyhayek.pixadetails.data.remote.MockApiInterface
import com.antonyhayek.pixadetails.data.remote.requests.LoginRequest

class LoginRepository(
    private val api: MockApiInterface
)  : BaseRepository() {

    suspend fun login(loginRequest: LoginRequest) =
        safeApiCall { api.login(loginRequest) }

}