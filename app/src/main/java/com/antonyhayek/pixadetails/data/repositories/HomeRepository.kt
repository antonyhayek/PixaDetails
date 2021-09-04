package com.antonyhayek.pixadetails.data.repositories

import com.antonyhayek.pixadetails.data.base.BaseRepository
import com.antonyhayek.pixadetails.data.remote.ApiInterface

class HomeRepository(
    private val api: ApiInterface
)  : BaseRepository() {

    suspend fun getPixabayImages() =
        safeApiCall { api.getPixabayImages() }

}