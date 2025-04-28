package com.example.showcase.features.mediadetails.ui

import com.example.showcase.MainDispatcherRule
import com.example.showcase.base.Async
import com.example.showcase.ui.toLocalDateTimeString
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MediaDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when viewModel starts, should load media details and image preview`() = runTest {
        val getMediaUSeCase = spyk(FakeGetMediaUseCase())
        val loadMediaImageUseCase = spyk(FakeLoadMediaImageUseCase())
        val viewModel = MediaDetailsViewModel(
            mediaId = FakeGetMediaUseCase.dummyMedia.id,
            getMediaUseCase = getMediaUSeCase,
            loadMediaImageUseCase = loadMediaImageUseCase
        )

        advanceUntilIdle()

        coVerify(exactly = 1) { getMediaUSeCase(FakeGetMediaUseCase.dummyMedia.id) }
        coVerify(exactly = 1) { loadMediaImageUseCase(FakeGetMediaUseCase.dummyMedia.id) }
        viewModel.state.value shouldBe MediaDetailsState(
            mediaDetails = Async.Success(
                MediaDetails(
                    id = FakeGetMediaUseCase.dummyMedia.id,
                    title = FakeGetMediaUseCase.dummyMedia.title,
                    url = FakeGetMediaUseCase.dummyMedia.url,
                    dateTime = FakeGetMediaUseCase.dummyMedia.dateTime.toLocalDateTimeString()
                )
            ),
            mediaImage = Async.Success(FakeLoadMediaImageUseCase.mockedImage)
        )
    }

    @Test
    fun `when viewModel starts and fails to load, should set fail state`() = runTest {
        val getMediaUSeCase = spyk(FakeGetMediaUseCase(shouldFail = true))
        val loadMediaImageUseCase = spyk(FakeLoadMediaImageUseCase(shouldFail = true))
        val viewModel = MediaDetailsViewModel(
            mediaId = FakeGetMediaUseCase.dummyMedia.id,
            getMediaUseCase = getMediaUSeCase,
            loadMediaImageUseCase = loadMediaImageUseCase
        )

        advanceUntilIdle()

        coVerify(exactly = 1) { getMediaUSeCase(FakeGetMediaUseCase.dummyMedia.id) }
        coVerify(exactly = 1) { loadMediaImageUseCase(FakeGetMediaUseCase.dummyMedia.id) }
        viewModel.state.value shouldBe MediaDetailsState(
            mediaDetails = Async.Failure(MediaNotFoundException(FakeGetMediaUseCase.dummyMedia.id)),
            mediaImage = Async.Failure(FakeLoadMediaImageUseCase.dummyException)
        )
    }
}