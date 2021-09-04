package com.antonyhayek.pixadetails.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.antonyhayek.pixadetails.data.repositories.HomeRepository
import com.antonyhayek.pixadetails.data.repositories.LoginRepository
import com.antonyhayek.pixadetails.data.repositories.RegisterRepository

class RegisterViewModelFactory(private val repository: RegisterRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(repository) as T
    }
}