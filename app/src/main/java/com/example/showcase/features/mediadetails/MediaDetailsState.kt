package com.example.showcase.features.mediadetails

import com.example.showcase.base.Async

data class MediaDetailsState(
    val mediaDetails: Async<Nothing> = Async.Uninitialized
)
