package com.antonyhayek.pixadetails.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.antonyhayek.pixadetails.data.local.prefsstore.PrefsStore
import com.antonyhayek.pixadetails.data.local.prefsstore.UserPreferences
import com.antonyhayek.pixadetails.data.remote.requests.LoginRequest
import com.antonyhayek.pixadetails.data.repositories.LoginRepository

class DetailsViewModel (private val loginRepository: LoginRepository) : ViewModel() {

    suspend fun updateIsLoggedIn(isLoggedIn: Boolean) {
        loginRepository.updateIsLoggedIn(isLoggedIn)
    }

}
