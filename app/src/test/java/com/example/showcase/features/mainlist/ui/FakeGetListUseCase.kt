package com.example.showcase.features.mainlist.ui

import com.example.showcase.features.mainlist.domain.GetListUseCase
import com.example.showcase.features.mainlist.domain.model.Media
import com.example.showcase.features.shared.data.model.MediaType
import java.time.LocalDateTime

class FakeGetListUseCase(private val shouldFails: Boolean = false) : GetListUseCase {
    override suspend fun invoke(): Result<List<Media>> = runCatching {
        if (shouldFails) {
            throw Exception()
        } else {
            listOf(dummyMedia)
        }
    }

    companion object {
        val dummyMedia = Media(
            id = 1,
            url = "",
            urlBig = "",
            title = "",
            dateTime = LocalDateTime.MIN,
            type = MediaType.PDF
        )
    }
}