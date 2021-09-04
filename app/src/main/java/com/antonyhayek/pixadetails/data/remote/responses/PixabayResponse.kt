package com.antonyhayek.pixadetails.data.remote.responses

data class PixabayResponse(
    val total: Long,
    val totalHits: Long,
    val hits: List<ImageResponse>
)