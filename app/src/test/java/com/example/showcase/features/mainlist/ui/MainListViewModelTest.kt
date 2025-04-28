package com.example.showcase.features.mainlist.ui

import androidx.compose.material3.SnackbarResult
import app.cash.turbine.test
import com.example.showcase.MainDispatcherRule
import com.example.showcase.base.Async
import com.example.showcase.base.getOrThrow
import com.example.showcase.features.mainlist.domain.model.Media
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when viewModel starts, should fetch data and update the state`() = runTest {
        val getListUseCase = spyk(FakeGetListUseCase())
        val viewModel = MainListViewModel(getListUseCase)

        viewModel.state.test {
            awaitItem() shouldBe MainListState(items = Async.Uninitialized)
            advanceUntilIdle()
            awaitItem() shouldBe MainListState(items = Async.Loading(null))
            awaitItem().items.shouldBeInstanceOf<Async.Success<List<Media>>>()
        }

        coVerify(exactly = 1) { getListUseCase() }
    }

    @Test
    fun `when viewModel starts and fails to fetch data, should send error event`() = runTest {
        val getListUseCase = spyk(FakeGetListUseCase(shouldFails = true))
        val viewModel = MainListViewModel(getListUseCase)

        viewModel.eventsChannel.test {
            advanceUntilIdle()
            awaitItem() shouldBe MainListEvent.FetchError
        }

        coVerify(exactly = 1) { getListUseCase() }
    }

    @Test
    fun `when onFetchFailedSnackBarResult is triggered, should fetch data again if the result is ActionPerformed`() =
        runTest {
            val getListUseCase = spyk(FakeGetListUseCase())
            val viewModel = MainListViewModel(getListUseCase)

            advanceUntilIdle()
            coVerify(exactly = 1) { getListUseCase() }

            viewModel.onFetchFailedSnackBarResult(SnackbarResult.ActionPerformed)
            advanceUntilIdle()
            coVerify(exactly = 2) { getListUseCase() }

            viewModel.onFetchFailedSnackBarResult(SnackbarResult.Dismissed)
            advanceUntilIdle()
            coVerify(exactly = 2) { getListUseCase() }
        }

    @Test
    fun `when onFetchFailedSnackBarResult is triggered, should not fetch data again if the result is Dismissed`() =
        runTest {
            val getListUseCase = spyk(FakeGetListUseCase())
            val viewModel = MainListViewModel(getListUseCase)

            advanceUntilIdle()
            coVerify(exactly = 1) { getListUseCase() }

            viewModel.onFetchFailedSnackBarResult(SnackbarResult.Dismissed)
            advanceUntilIdle()
            coVerify(exactly = 1) { getListUseCase() }
        }

    @Test
    fun `when refresh is triggered, should fetch data from start`() = runTest {
        val getListUseCase = spyk(FakeGetListUseCase())
        val viewModel = MainListViewModel(getListUseCase)

        advanceUntilIdle()
        coVerify(exactly = 1) { getListUseCase() }
        viewModel.state.value.items.getOrThrow().size shouldBe 1

        viewModel.onRefresh()
        advanceUntilIdle()
        coVerify(exactly = 2) { getListUseCase() }
        viewModel.state.value.items.getOrThrow().size shouldBe 1
    }

    @Test
    fun `when refresh is triggered, should cancel delete jobs`() = runTest {
        val getListUseCase = spyk(FakeGetListUseCase())
        val viewModel = MainListViewModel(getListUseCase)

        advanceUntilIdle()
        coVerify(exactly = 1) { getListUseCase() }
        viewModel.state.value.items.getOrThrow().size shouldBe 1

        viewModel.onItemDeletion(FakeGetListUseCase.dummyMedia.id)
        viewModel.onRefresh()
        advanceUntilIdle()
        coVerify(exactly = 2) { getListUseCase() }
        viewModel.state.value.items.getOrThrow().size shouldBe 1
    }

    @Test
    fun `when deletion is triggered, should delete item from list`() = runTest {
        val getListUseCase = spyk(FakeGetListUseCase())
        val viewModel = MainListViewModel(getListUseCase)

        advanceUntilIdle()
        coVerify(exactly = 1) { getListUseCase() }
        viewModel.state.value.items.getOrThrow().size shouldBe 1

        viewModel.onItemDeletion(FakeGetListUseCase.dummyMedia.id)
        advanceUntilIdle()
        viewModel.state.value.items.getOrThrow().size shouldBe 0
    }
}