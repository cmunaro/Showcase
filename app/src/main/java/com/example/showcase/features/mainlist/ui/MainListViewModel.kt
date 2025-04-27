package com.example.showcase.features.mainlist.ui

import androidx.lifecycle.viewModelScope
import com.example.showcase.base.Async
import com.example.showcase.base.StateViewModel
import com.example.showcase.base.getOrElse
import com.example.showcase.base.getOrNull
import com.example.showcase.features.mainlist.domain.GetListUseCase
import com.example.showcase.features.mainlist.domain.model.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainListViewModel(
    private val getListUseCase: GetListUseCase
): StateViewModel<MainListState>(
    initialState = MainListState()
) {
    fun fetchItems() {
        updateState { copy(items = Async.Loading(items.getOrNull())) }

        viewModelScope.launch(Dispatchers.IO) {
            val medias = getListUseCase().map(Media::toPreview)
            updateState {
                val newItemsList = items.getOrElse(emptyList()) + medias
                copy(items = Async.Success(newItemsList))
            }
        }
    }
}