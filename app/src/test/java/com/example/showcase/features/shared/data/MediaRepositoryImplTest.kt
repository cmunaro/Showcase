package com.example.showcase.features.shared.data

import androidx.compose.ui.graphics.ImageBitmap
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MediaRepositoryImplTest {
    val testDispatcher = StandardTestDispatcher()

    @Test
    fun `when getImageOf is triggered and the image is in storage then just return it`() = runTest {
        val api = mockk<VegansLabAPI>()
        val mediaStorage = mockk<MediaStorage>()
        val tempFileManager = mockk<TempFileManager>()
        val pdfToImageService = mockk<PdfToImageService>()
        val repository = MediaRepositoryImpl(
            dispatcher = testDispatcher,
            api = api,
            mediaStorage = mediaStorage,
            tempFileManager = tempFileManager,
            pdfToImageService = pdfToImageService
        )

        every { mediaStorage.getImageOrNull(1) } answers { mockedImage }

        repository.getImageOf(mediaId = 1) shouldBe Result.success(mockedImage)
    }

    companion object {
        private val mockedImage = mockk<ImageBitmap>()
    }
}