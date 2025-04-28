package com.example.showcase.features.mediadetails.domain

import com.example.showcase.features.mainlist.domain.MediaRepository
import com.example.showcase.features.mainlist.domain.model.Media

interface GetMediaUseCase {
    operator fun invoke(mediaId: Int): Result<Media>
}

class GetMediaUseCaseImpl(private val mediaRepository: MediaRepository) : GetMediaUseCase {
    override fun invoke(mediaId: Int) =
        mediaRepository.getMedia(mediaId)
}
