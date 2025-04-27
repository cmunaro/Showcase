package com.example.showcase.features.mainlist.ui

sealed interface MainListEvent {
    data object FetchError : MainListEvent
}