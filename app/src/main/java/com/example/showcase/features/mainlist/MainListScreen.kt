package com.example.showcase.features.mainlist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object MainListRoute

@Composable
fun MainListPage(viewModel: MainListViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainListScreen(state)
}

@Composable
fun MainListScreen(state: MainListState) {
    Text("Hello World")
}

@Preview
@Composable
private fun PreviewMainList() {
    MainListScreen(
        state = MainListState()
    )
}
