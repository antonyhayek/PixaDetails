package com.antonyhayek.pixadetails.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.antonyhayek.pixadetails.data.base.BaseRepository
import com.antonyhayek.pixadetails.data.remote.ApiInterface
import com.antonyhayek.pixadetails.data.remote.responses.ImageResponse
import com.antonyhayek.pixadetails.ui.home.HomeViewModel
import com.antonyhayek.pixadetails.ui.home.ImageDataSource
import kotlinx.coroutines.flow.Flow

class HomeRepository(
    private val api: ApiInterface
)  : BaseRepository() {

   /* suspend fun getPixabayImages() =
        safeApiCall { api.getPixabayImages() }*/


    fun getPixabayImages(
    ): Flow<PagingData<ImageResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                ImageDataSource(api)
            }
        ).flow
    }

    /**
     * The same thing but with Livedata
     */
    fun getPixabayImagesLiveData(

    ): LiveData<PagingData<ImageResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                ImageDataSource(api)
            }
        ).liveData

    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

}