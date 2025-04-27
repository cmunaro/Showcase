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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainListViewModel(
    private val getListUseCase: GetListUseCase
): StateViewModel<MainListState>(initialState = MainListState()),
    EventLauncher<MainListEvent> by EventLauncherImpl()
{

    private fun fetchItems(fromStart: Boolean = false) {
        updateState { copy(items = Async.Loading(items.getOrNull())) }

        viewModelScope.launch(Dispatchers.IO) {
            val medias = getListUseCase()
                .onFailure { sendEvent(MainListEvent.FetchError) }
                .getOrDefault(emptyList())
                .map(Media::toPreview)
            updateState {
                val newItemsList = if(fromStart) medias else items.getOrElse(emptyList()) + medias
                copy(items = Async.Success(newItemsList))
            }
        }
    }

    fun onFetchFailedResult(result: SnackbarResult) {
        if (result == SnackbarResult.ActionPerformed) {
            fetchItems()
        }
    }

    fun onRefresh() = fetchItems(fromStart = true)
    fun onDelete(id: Int) {

    }
}