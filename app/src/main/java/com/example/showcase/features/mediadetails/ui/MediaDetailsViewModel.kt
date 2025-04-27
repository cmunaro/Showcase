package com.example.showcase.features.mediadetails.ui

import com.example.showcase.base.Async
import com.example.showcase.base.StateViewModel
import com.example.showcase.features.mediadetails.domain.GetMediaUseCase

class MediaDetailsViewModel(
    mediaId: Int,
    getMediaUseCase: GetMediaUseCase
) : StateViewModel<MediaDetailsState>(MediaDetailsState()) {

    init {
        val media = getMediaUseCase(mediaId)?.toMediaDetails()
        updateState {
            copy(
                mediaDetails = if (media == null) Async.Failure(MediaNotFoundException(mediaId))
                else Async.Success(media)
            )
        }
    }
}