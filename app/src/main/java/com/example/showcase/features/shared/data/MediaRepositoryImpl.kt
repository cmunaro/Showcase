package com.example.showcase.features.shared.data

import androidx.compose.ui.graphics.ImageBitmap
import com.example.showcase.base.runCatchingNonCancellation
import com.example.showcase.features.mainlist.domain.MediaRepository
import com.example.showcase.features.mainlist.domain.model.Media
import com.example.showcase.features.mainlist.domain.model.toDomain
import com.example.showcase.features.shared.data.model.MediaData
import com.example.showcase.features.shared.data.model.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepositoryImpl(
    private val api: VegansLabAPI,
    private val mediaStorage: MediaStorage,
    private val tempFileManager: TempFileManager,
    private val pdfToImageService: PdfToImageService
) : MediaRepository {
    override suspend fun getList(): Result<List<Media>> = runCatchingNonCancellation {
        withContext(Dispatchers.IO) {
            api.getItems().content
                .map(MediaData::toDomain)
                .also { mediaStorage.save(it) }
        }
    }

    override fun getMedia(mediaId: Int) =
        mediaStorage.getMediaOrNull(mediaId)

    override suspend fun getImageOf(mediaId: Int): Result<ImageBitmap> =
        runCatchingNonCancellation {
            mediaStorage.getImageOrNull(mediaId) ?: withContext(Dispatchers.IO) {
                val media = getMedia(mediaId)!!
                if (media.type != MediaType.PDF) {
                    throw IllegalArgumentException("Media type is not supported")
                }
                val bytes = api.downloadPdf(url = media.url)
                val tempFile = tempFileManager.saveToTempFile(bytes = bytes)
                pdfToImageService.convert(pdfFile = tempFile).also {
                    tempFileManager.dispose(file = tempFile)
                    mediaStorage.save(mediaId = mediaId, mediaImage = it)
                }
            }
        }
}