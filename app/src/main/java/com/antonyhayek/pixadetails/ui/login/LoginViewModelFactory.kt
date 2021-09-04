package com.antonyhayek.pixadetails.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.antonyhayek.pixadetails.data.repositories.HomeRepository
import com.antonyhayek.pixadetails.data.repositories.LoginRepository

class LoginViewModelFactory(private val repository: LoginRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}