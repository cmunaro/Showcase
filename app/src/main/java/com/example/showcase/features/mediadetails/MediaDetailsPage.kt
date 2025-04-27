package com.example.showcase.features.mediadetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Text("$mediaId")
}