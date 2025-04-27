package com.example.showcase.features.mediadetails.ui.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.example.showcase.ui.PreviewTransitionAnimation
import com.example.showcase.ui.theme.ShowcaseTheme

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun MediaHeader(
    title: String,
    dateTime: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    with(sharedTransitionScope) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = title),
                        animatedVisibilityScope = animatedContentScope
                    )
            )

            Text(
                text = dateTime,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = dateTime),
                        animatedVisibilityScope = animatedContentScope
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun PreviewMediaListItem() {
    ShowcaseTheme {
        PreviewTransitionAnimation { sharedTransitionScope, animatedContentScope ->
            MediaHeader(
                title = LoremIpsum(8).values.joinToString(),
                dateTime = "11/11/1111",
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    }
}