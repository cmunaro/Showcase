package com.example.showcase.features.mainlist.ui

import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.viewModelScope
import com.example.showcase.base.Async
import com.example.showcase.base.EventLauncher
import com.example.showcase.base.EventLauncherImpl
import com.example.showcase.base.StateViewModel
import com.example.showcase.base.getOrElse
import com.example.showcase.base.getOrNull
import com.example.showcase.features.mainlist.domain.GetListUseCase
import com.example.showcase.features.mainlist.domain.model.Media
import io.ktor.util.collections.ConcurrentSet
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MainListViewModel(
    private val getListUseCase: GetListUseCase
) : StateViewModel<MainListState>(initialState = MainListState()),
    EventLauncher<MainListEvent> by EventLauncherImpl() {
    private val deletionJobs = ConcurrentSet<Job>()

    private suspend fun fetchItems(fromStart: Boolean = false) {
        updateState { copy(items = Async.Loading(items.getOrNull())) }

        val medias = getListUseCase()
            .onFailure { sendEvent(MainListEvent.FetchError) }
            .getOrDefault(emptyList())
            .map(Media::toPreview)
        updateState {
            val newItemsList = if (fromStart) medias else items.getOrElse(emptyList()) + medias
            copy(items = Async.Success(newItemsList))
        }
    }

    fun onFetchFailedResult(result: SnackbarResult) {
        if (result == SnackbarResult.ActionPerformed) {
            viewModelScope.launch {
                fetchItems()
            }
        }
    }

    fun onRefresh() {
        deletionJobs.forEach { it.cancel() }
        viewModelScope.launch {
            fetchItems(fromStart = true)
        }
    }

    fun onItemDeletion(id: Int) {
        val job = viewModelScope.launch {
            delay(0.5.seconds)
            updateState {
                copy(items = Async.Success(items.getOrElse(emptyList()).filterNot { it.id == id }))
            }
        }
        deletionJobs.add(job)
        job.invokeOnCompletion { deletionJobs.remove(job) }
    }
}