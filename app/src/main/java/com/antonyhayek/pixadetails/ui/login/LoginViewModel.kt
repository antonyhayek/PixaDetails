package com.antonyhayek.pixadetails.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.antonyhayek.pixadetails.data.local.prefsstore.PrefsStore
import com.antonyhayek.pixadetails.data.local.prefsstore.UserPreferences
import com.antonyhayek.pixadetails.data.remote.requests.LoginRequest
import com.antonyhayek.pixadetails.data.repositories.LoginRepository

class LoginViewModel (private val loginRepository: LoginRepository) : ViewModel() {

    fun loginResponseLiveData(loginRequest: LoginRequest) = liveData {
        val response = loginRepository.login(loginRequest)
        emit(response)
    }

    suspend fun updateIsLoggedIn(isLoggedIn: Boolean) {
        loginRepository.updateIsLoggedIn(isLoggedIn)
    }

    val isLoggedIn : LiveData<UserPreferences> = loginRepository.isLoggedIn
}
