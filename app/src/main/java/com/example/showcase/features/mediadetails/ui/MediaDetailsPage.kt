package com.example.showcase.features.mediadetails.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.showcase.base.Async
import com.example.showcase.features.mediadetails.ui.components.MediaHeader
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data class MediaDetailsPageRoute(val mediaId: Int)

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun MediaDetailsPage(
    mediaId: Int,
    viewmodel: MediaDetailsViewModel = koinViewModel(parameters = { parametersOf(mediaId) }),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()

    when (val mediaDetails = state.mediaDetails) {
        is Async.Failure -> TODO()
        is Async.Loading, Async.Uninitialized -> {}
        is Async.Success -> {
            Column {
                MediaHeader(
                    title = mediaDetails.value.title,
                    dateTime = mediaDetails.value.dateTime,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )
                MediaRender(
                    render = state.mediaImage
                )
            }
        }
    }
}

@Composable
fun MediaRender(render: Async<ImageBitmap>) {
    when (render) {
        is Async.Failure -> {
            Button(onClick = {}) {
                Text(text = "Open online")
            }
        }

        is Async.Loading, Async.Uninitialized -> CircularProgressIndicator()
        is Async.Success -> {
            Image(
                bitmap = render.value,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
