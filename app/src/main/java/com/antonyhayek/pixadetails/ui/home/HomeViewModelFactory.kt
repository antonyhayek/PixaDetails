package com.antonyhayek.pixadetails.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.antonyhayek.pixadetails.data.repositories.HomeRepository

class HomeViewModelFactory(private val repository: HomeRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}