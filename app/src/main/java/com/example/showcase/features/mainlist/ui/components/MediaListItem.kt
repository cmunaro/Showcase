package com.example.showcase.features.mainlist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.example.showcase.features.mainlist.ui.MediaPreview
import com.example.showcase.ui.theme.ShowcaseTheme

@Composable
fun MediaListItem(mediaPreview: MediaPreview) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .height(64.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = mediaPreview.title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = mediaPreview.dateTime,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMediaListItem() {
    ShowcaseTheme {
        MediaListItem(
            mediaPreview = MediaPreview(
                id = 1,
                title = LoremIpsum(100).values.joinToString(),
                dateTime = "11/11/1111"
            )
        )
    }
}