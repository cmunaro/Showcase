package com.example.showcase.features.mediadetails.ui

import androidx.compose.ui.graphics.ImageBitmap
import com.example.showcase.features.mediadetails.domain.LoadMediaImageUseCase
import io.mockk.mockk

class FakeLoadMediaImageUseCase(private val shouldFail: Boolean = false) : LoadMediaImageUseCase {
    override suspend fun invoke(mediaId: Int): Result<ImageBitmap> {
        return if (shouldFail) {
            Result.failure(dummyException)
        } else {
            Result.success(mockedImage)
        }
    }

    companion object {
        val mockedImage = mockk<ImageBitmap>()
        val dummyException = Exception("Failed to load image")
    }
}