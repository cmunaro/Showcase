package com.example.showcase.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class StateViewModel<T>(
    private val initialState: T
): ViewModel() {
    private val _state = MutableStateFlow<T>(initialState)
    val state: StateFlow<T> = _state

    fun updateState(alteration: T.() -> T) {
        _state.update(alteration)
    }
}