package com.example.showcase.features.mainlist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.showcase.base.Async
import com.example.showcase.base.getOrElse
import com.example.showcase.features.mainlist.ui.components.MediaListItem
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object MainListRoute

@Composable
fun MainListPage(viewModel: MainListViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchItems()
    }

    MainListScreen(state)
}

@Composable
fun MainListScreen(state: MainListState) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = state.items.getOrElse(emptyList()), key = MediaPreview::id) { mediaPreview ->
            MediaListItem(mediaPreview)
        }

        if (state.items is Async.Loading || state.items == Async.Uninitialized) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMainList() {
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
        )
    )
}
