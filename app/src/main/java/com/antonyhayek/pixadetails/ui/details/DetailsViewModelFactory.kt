package com.antonyhayek.pixadetails.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.antonyhayek.pixadetails.data.local.prefsstore.PrefsStore
import com.antonyhayek.pixadetails.data.repositories.HomeRepository
import com.antonyhayek.pixadetails.data.repositories.LoginRepository

class DetailsViewModelFactory(private val repository: LoginRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository) as T
    }
}