package com.example.showcase.features.mediadetails.ui

import com.example.showcase.features.mainlist.domain.model.Media
import com.example.showcase.features.mediadetails.domain.GetMediaUseCase
import com.example.showcase.features.shared.data.model.MediaType
import java.time.LocalDateTime

class FakeGetMediaUseCase(private val shouldFail: Boolean = false) : GetMediaUseCase {
    override fun invoke(mediaId: Int): Result<Media> {
        return if (shouldFail) {
            Result.failure(Exception("Fake error"))
        } else {
            Result.success(dummyMedia)
        }
    }

    companion object {
        val dummyMedia = Media(
            id = 1,
            title = "title",
            urlBig = "",
            type = MediaType.PDF,
            url = "",
            dateTime = LocalDateTime.MIN
        )
    }
}