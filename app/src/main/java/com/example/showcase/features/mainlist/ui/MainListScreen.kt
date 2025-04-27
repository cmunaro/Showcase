package com.example.showcase.features.mainlist.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.showcase.base.Async
import com.example.showcase.base.getOrElse
import com.example.showcase.features.mainlist.ui.components.MediaListItem
import com.example.showcase.features.mainlist.ui.components.SwipeToDeleteBox
import com.example.showcase.ui.PreviewTransitionAnimation
import com.example.showcase.ui.theme.ShowcaseTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object MainListRoute

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun MainListPage(
    viewModel: MainListViewModel = koinViewModel(),
    snackBarHostState: SnackbarHostState,
    onNavigateToItemDetails: (id: Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize()

        viewModel.eventsChannel.collectLatest {
            val snackBarResult = snackBarHostState.showSnackbar(
                message = "Fetch failed",
                actionLabel = "Retry",
                withDismissAction = true
            )
            viewModel.onFetchFailedResult(snackBarResult)
        }
    }

    MainListScreen(
        state = state,
        onRefresh = viewModel::onRefresh,
        onItemDeletion = viewModel::onItemDeletion,
        onItemClick = onNavigateToItemDetails,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
fun MainListScreen(
    state: MainListState,
    onRefresh: () -> Unit,
    onItemDeletion: (id: Int) -> Unit,
    onItemClick: (id: Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    PullToRefreshBox(
        isRefreshing = state.items is Async.Loading || state.items == Async.Uninitialized,
        onRefresh = onRefresh,
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = state.items.getOrElse(emptyList()), key = MediaPreview::id) { mediaPreview ->
                SwipeToDeleteBox(
                    modifier = Modifier
                        .animateItem()
                        .clickable { onItemClick(mediaPreview.id) },
                    onDelete = { onItemDeletion(mediaPreview.id) }
                ) {
                    MediaListItem(mediaPreview, sharedTransitionScope, animatedContentScope)
                }
            }

            if (state.items is Async.Success && state.items.value.isEmpty()) {
                item {
                    Text(
                        text = "No items",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun PreviewMainList() {
    ShowcaseTheme {
        PreviewTransitionAnimation { sharedTransitionScope, animatedContentScope ->
            MainListScreen(
                state = MainListState(
                    items = Async.Loading(
                        listOf(
                            MediaPreview(
                                id = 1,
                                title = "Title 1",
                                dateTime = "11/11/1111"
                            ),
                            MediaPreview(
                                id = 2,
                                title = "Title 2",
                                dateTime = "11/11/1111"
                            ),
                            MediaPreview(
                                id = 3,
                                title = "Title 3",
                                dateTime = "11/11/1111"
                            )
                        )
                    )
                ),
                onRefresh = {},
                onItemDeletion = {},
                onItemClick = {},
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    }
}
