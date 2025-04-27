package com.example.showcase.features.mediadetails.ui

import androidx.compose.ui.graphics.ImageBitmap
import com.example.showcase.base.Async
import com.example.showcase.features.mainlist.domain.model.Media
import com.example.showcase.ui.toLocalDateTimeString

data class MediaDetailsState(
    val mediaDetails: Async<MediaDetails> = Async.Uninitialized,
    val mediaImage: Async<ImageBitmap> = Async.Uninitialized
)

data class MediaDetails(
    val id: Int,
    val url: String,
    val dateTime: String,
    val title: String
)

fun Media.toMediaDetails() = MediaDetails(
    id = id,
    url = url,
    dateTime = dateTime.toLocalDateTimeString(),
    title = title
)

data class MediaNotFoundException(val mediaId: Int) :
    Exception("No media with id $mediaId was found")
