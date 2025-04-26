package com.example.showcase.features.mainlist.ui

import androidx.lifecycle.viewModelScope
import com.example.showcase.base.StateViewModel
import com.example.showcase.features.mainlist.domain.GetListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainListViewModel(
    private val getListUseCase: GetListUseCase
): StateViewModel<MainListState>(
    initialState = MainListState()
) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val list = getListUseCase()
            println(list)
        }
    }
}