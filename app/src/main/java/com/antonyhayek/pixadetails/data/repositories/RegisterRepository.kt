package com.antonyhayek.pixadetails.data.repositories

import com.antonyhayek.pixadetails.data.base.BaseRepository
import com.antonyhayek.pixadetails.data.remote.MockApiInterface
import com.antonyhayek.pixadetails.data.remote.requests.RegisterRequest

class RegisterRepository(
    private val api: MockApiInterface
)  : BaseRepository() {

    suspend fun register(registerRequest: RegisterRequest) =
        safeApiCall { api.register(registerRequest) }

}