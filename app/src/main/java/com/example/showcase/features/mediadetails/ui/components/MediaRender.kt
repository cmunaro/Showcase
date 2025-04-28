package com.example.showcase.features.mediadetails.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.showcase.R
import com.example.showcase.base.Async

@Composable
fun MediaRender(render: Async<ImageBitmap>, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (render) {
            is Async.Loading, Async.Uninitialized -> CircularProgressIndicator()
            is Async.Failure -> {
                Button(onClick = onClick) {
                    Text(text = stringResource(R.string.open_media_online_button))
                }
            }

            is Async.Success -> {
                if (LocalInspectionMode.current) {
                    Text(
                        text = "PREVIEW",
                        modifier = Modifier
                            .size(400.dp)
                            .background(color = Color.LightGray),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Image(
                        bitmap = render.value,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .clickable(onClick = onClick)
                    )
                }
            }
        }
    }
}