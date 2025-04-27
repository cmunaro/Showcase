package com.example.showcase.features.shared.data

import android.content.Context
import android.graphics.pdf.PdfRenderer
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.createBitmap
import androidx.core.net.toUri
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resumeWithException

interface PdfToImageService {
    suspend fun convert(pdfFile: File): ImageBitmap
}

class PdfToImageServiceImpl(private val context: Context) : PdfToImageService {
    override suspend fun convert(pdfFile: File): ImageBitmap =
        suspendCancellableCoroutine { continuation ->
            runCatching {
                context.contentResolver.openFileDescriptor(pdfFile.toUri(), "r")
                    ?.use { descriptor ->
                        PdfRenderer(descriptor).use { pdfRenderer ->
                            pdfRenderer.openPage(0).use { page ->
                                val bitmap = createBitmap(page.width, page.height)
                                page.render(
                                    bitmap,
                                    null,
                                    null,
                                    PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                                )
                                bitmap.asImageBitmap()
                            }
                        }
                    } ?: throw NullPointerException("Descriptor is null")
            }.onFailure(continuation::resumeWithException)
                .onSuccess { it: ImageBitmap -> continuation.resume(value = it) { _, _, _ -> } }
        }
}