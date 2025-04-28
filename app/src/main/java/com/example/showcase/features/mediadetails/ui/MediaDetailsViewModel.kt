package com.example.showcase.features.mediadetails.ui

import androidx.lifecycle.viewModelScope
import com.example.showcase.base.Async
import com.example.showcase.base.StateViewModel
import com.example.showcase.features.mainlist.domain.model.Media
import com.example.showcase.features.mediadetails.domain.GetMediaUseCase
import com.example.showcase.features.mediadetails.domain.LoadMediaImageUseCase
import kotlinx.coroutines.launch

class MediaDetailsViewModel(
    mediaId: Int,
    private val getMediaUseCase: GetMediaUseCase,
    private val loadMediaImageUseCase: LoadMediaImageUseCase
) : StateViewModel<MediaDetailsState>(MediaDetailsState()) {

    init {
        loadMediaDescription(mediaId)
        loadMediaImage(mediaId)
    }

    private fun loadMediaDescription(mediaId: Int) {
        val media = getMediaUseCase(mediaId).map(Media::toMediaDetails).getOrNull()
        updateState {
            copy(
                mediaDetails = if (media == null) Async.Failure(MediaNotFoundException(mediaId))
                else Async.Success(media)
            )
        }
    }

    private fun loadMediaImage(mediaId: Int) {
        viewModelScope.launch {
            loadMediaImageUseCase(mediaId)
                .onSuccess { updateState { copy(mediaImage = Async.Success(it)) } }
                .onFailure { updateState { copy(mediaImage = Async.Failure(it)) } }
        }
    }
}