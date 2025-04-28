package com.example.showcase.features.mediadetails.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.showcase.base.Async
import com.example.showcase.base.getOrNull
import com.example.showcase.features.mediadetails.ui.components.MediaHeader
import com.example.showcase.features.mediadetails.ui.components.MediaRender
import com.example.showcase.ui.PreviewTransitionAnimation
import com.example.showcase.ui.theme.ShowcaseTheme
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
    onGoBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val uriHandler = LocalUriHandler.current
    val state by viewmodel.state.collectAsStateWithLifecycle()

    MediaDetailsScreen(
        state = state,
        onGoBack = onGoBack,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        onOpenMedia = { state.mediaDetails.getOrNull()?.url?.let(uriHandler::openUri) }
    )
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
private fun MediaDetailsScreen(
    state: MediaDetailsState,
    onGoBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onOpenMedia: () -> Unit,
) {
    when (val mediaDetails = state.mediaDetails) {
        is Async.Failure -> onGoBack()
        is Async.Loading, Async.Uninitialized -> {}
        is Async.Success -> {
            Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
                MediaHeader(
                    title = mediaDetails.value.title,
                    dateTime = mediaDetails.value.dateTime,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )
                MediaRender(
                    render = state.mediaImage,
                    onClick = onOpenMedia
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
private fun PreviewMediaDetailsScreen() {
    ShowcaseTheme {
        PreviewTransitionAnimation { sharedTransitionScope, animatedContentScope ->
            MediaDetailsScreen(
                state = MediaDetailsState(
                    mediaDetails = Async.Success(
                        MediaDetails(id = 1, url = "", dateTime = "11/11/1111", title = "Title")
                    ),
                    mediaImage = Async.Success(value = ImageBitmap(1, 1))
                ),
                onGoBack = {},
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
                onOpenMedia = {}
            )
        }
    }
}
