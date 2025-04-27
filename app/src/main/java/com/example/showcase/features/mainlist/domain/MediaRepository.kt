package com.example.showcase.features.mainlist.domain

import com.example.showcase.features.mainlist.domain.model.Media

interface MediaRepository {
    suspend fun getList(): Result<List<Media>>
    fun getMedia(mediaId: Int): Media?

}