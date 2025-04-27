package com.example.showcase.features.mediadetails.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data class MediaDetailsPageRoute(val mediaId: Int)

@Composable
fun MediaDetailsPage(
    mediaId: Int,
    viewmodel: MediaDetailsViewModel = koinViewModel(parameters = { parametersOf(mediaId) })
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()

    Text("$state")
}