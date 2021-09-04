package com.antonyhayek.pixadetails.ui.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.antonyhayek.pixadetails.R
import com.antonyhayek.pixadetails.data.remote.responses.ImageResponse
import com.antonyhayek.pixadetails.data.repositories.HomeRepository
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

   /* fun pixabayImagesResponseLiveData() = liveData {
        val response = homeRepository.getPixabayImages()
        emit(response)
    }*/

    /* val pixabayImages = Pager(PagingConfig(pageSize = 20)) {
         ImagePaginationDataSource(api)
     }.flow.cachedIn(viewModelScope)*/

    private var currentResult: Flow<PagingData<ImageResponse>>? = null

    @ExperimentalPagingApi
     fun getPixabayImages(): Flow<PagingData<ImageResponse>> {
        val newResult: Flow<PagingData<ImageResponse>> =
            homeRepository.getPixabayImages().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }


    fun getPixabayImagesLiveData(): LiveData<PagingData<ImageResponse>> {
        return  homeRepository.getPixabayImagesLiveData().cachedIn(viewModelScope)
    }

}