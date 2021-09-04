package com.antonyhayek.pixadetails.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.antonyhayek.pixadetails.data.remote.ApiInterface
import com.antonyhayek.pixadetails.data.remote.responses.ImageResponse


class ImagePaginationDataSource(
    private val api: ApiInterface
) : PagingSource<Int, ImageResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResponse> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = api.getPixabayImages(nextPageNumber, 20)
            LoadResult.Page(
                data = response.hits,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.totalHits) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ImageResponse>): Int? {
        return null
    }


}