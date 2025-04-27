package com.example.showcase.features.mainlist.domain

import androidx.compose.ui.graphics.ImageBitmap
import com.example.showcase.features.mainlist.domain.model.Media

interface MediaRepository {
    suspend fun getList(): Result<List<Media>>
    fun getMedia(mediaId: Int): Media?
    suspend fun getImageOf(mediaId: Int): Result<ImageBitmap>

}