package com.antonyhayek.pixadetails.ui.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.antonyhayek.pixadetails.R
import com.antonyhayek.pixadetails.data.repositories.HomeRepository
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    fun pixabayImagesResponseLiveData() = liveData {
        val response = homeRepository.getPixabayImages()
        emit(response)
    }


}