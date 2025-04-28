package com.example.showcase.features.mainlist.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.showcase.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeToDeleteBox(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val swipeOffset = remember { Animatable(0f) }
    val maxDragPx = with(LocalDensity.current) { (-64).dp.toPx() }
    var canDrag by remember { mutableStateOf(true) }
    val draggableState = rememberDraggableState { delta ->
        if (canDrag) {
            val newOffset = (swipeOffset.value + delta).coerceIn(maxDragPx, 0f)
            scope.launch {
                swipeOffset.snapTo(newOffset)
            }
        }
    }

    LaunchedEffect(swipeOffset.value) {
        if (swipeOffset.value <= maxDragPx) {
            if (canDrag) {
                canDrag = false
                onDelete()
            }
            swipeOffset.animateTo(maxDragPx)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Red)
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete_icon_description),
            tint = Color.White,
            modifier = Modifier
                .width(64.dp)
                .align(Alignment.CenterEnd)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(swipeOffset.value.roundToInt(), 0) }
                .draggable(
                    enabled = canDrag,
                    state = draggableState,
                    orientation = Orientation.Horizontal,
                    onDragStopped = {
                        if (swipeOffset.value > maxDragPx) {
                            scope.launch {
                                swipeOffset.animateTo(0f)
                            }
                        }
                    }
                )
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun SwipeToDeleteBoxPreview() {
    SwipeToDeleteBox(onDelete = {}, content = {
        Box(modifier = Modifier
            .width(600.dp)
            .height(64.dp)
            .background(Color.White))
    }, modifier = Modifier)
}
