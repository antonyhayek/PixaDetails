package com.antonyhayek.pixadetails.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.antonyhayek.pixadetails.data.remote.requests.LoginRequest
import com.antonyhayek.pixadetails.data.remote.requests.RegisterRequest
import com.antonyhayek.pixadetails.data.repositories.LoginRepository
import com.antonyhayek.pixadetails.data.repositories.RegisterRepository

class RegisterViewModel (private val registerRepository: RegisterRepository) : ViewModel() {

    fun registerResponseLiveData(registerRequest: RegisterRequest) = liveData {
        val response = registerRepository.register(registerRequest)
        emit(response)
    }
}
