package com.example.showcase.features.mainlist.ui

import androidx.compose.runtime.Immutable
import com.example.showcase.base.Async
import com.example.showcase.features.mainlist.domain.model.Media
import com.example.showcase.ui.toLocalDateTimeString

@Immutable
data class MainListState(
    val items: Async<List<MediaPreview>> = Async.Uninitialized
)

@Immutable
data class MediaPreview(
    val id: Int,
    val title: String,
    val dateTime: String
)

fun Media.toPreview() = MediaPreview(
    id = id,
    title = title,
    dateTime = dateTime.toLocalDateTimeString()
)
