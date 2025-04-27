package com.example.showcase.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.rememberTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedContentLambdaTargetStateParameter")
fun PreviewTransitionAnimation(
    composable: @Composable (
        sharedTransitionScope: SharedTransitionScope,
        animatedContentScope: AnimatedContentScope
    ) -> Unit
) {
    SharedTransitionLayout {
        val transitionState = remember { SeekableTransitionState(null) }
        val transition: Transition<Nothing?> = rememberTransition(transitionState, label = "fake")
        transition.AnimatedContent {
            composable(this@SharedTransitionLayout, this)
        }
    }
}