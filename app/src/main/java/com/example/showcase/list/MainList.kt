package com.example.showcase.list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.serialization.Serializable

@Serializable
data object MainListRoute

@Composable
fun MainList() {
    Text("Hello World")
}

@Preview
@Composable
private fun PreviewMainList() {
    MainList()
}
