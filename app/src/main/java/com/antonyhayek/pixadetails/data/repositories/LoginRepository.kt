package com.antonyhayek.pixadetails.data.repositories

import androidx.lifecycle.asLiveData
import com.antonyhayek.pixadetails.data.base.BaseRepository
import com.antonyhayek.pixadetails.data.local.prefsstore.PrefsStore
import com.antonyhayek.pixadetails.data.remote.MockApiInterface
import com.antonyhayek.pixadetails.data.remote.requests.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginRepository(
    private val api: MockApiInterface,
    private val prefStore: PrefsStore
)  : BaseRepository() {

    suspend fun login(loginRequest: LoginRequest) =
        safeApiCall { api.login(loginRequest) }

    suspend fun updateIsLoggedIn(isLoggedIn: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            prefStore.updateIsLoggedIn(isLoggedIn)
        }
    }

    val isLoggedIn = prefStore.userPreferencesFlow.asLiveData()

}