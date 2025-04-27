package com.example.showcase.features.mediadetails.domain

import androidx.compose.ui.graphics.ImageBitmap
import com.example.showcase.features.mainlist.domain.MediaRepository

interface LoadMediaImageUseCase {
    suspend operator fun invoke(mediaId: Int): Result<ImageBitmap>
}

class LoadMediaImageUseCaseImpl(private val mediaRepository: MediaRepository) :
    LoadMediaImageUseCase {
    override suspend operator fun invoke(mediaId: Int): Result<ImageBitmap> =
        mediaRepository.getImageOf(mediaId)
}
