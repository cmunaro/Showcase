package com.example.showcase.features.shared.data

import androidx.compose.ui.graphics.ImageBitmap
import com.example.showcase.features.mainlist.domain.model.Media

interface MediaStorage {
    fun save(medias: List<Media>)
    fun save(mediaId: Int, mediaImage: ImageBitmap)
    fun getMediaOrNull(mediaId: Int): Media?
    fun getImageOrNull(mediaId: Int): ImageBitmap?
}

class InMemoryMediaStorage : MediaStorage {
    private val medias = mutableListOf<Media>()
    private val mediaImages = mutableMapOf<Int, ImageBitmap>()

    override fun save(medias: List<Media>) {
        this.medias.clear()
        this.medias.addAll(medias)
    }

    override fun save(mediaId: Int, mediaImage: ImageBitmap) {
        mediaImages[mediaId] = mediaImage
    }

    override fun getMediaOrNull(mediaId: Int): Media? =
        medias.firstOrNull { it.id == mediaId }

    override fun getImageOrNull(mediaId: Int): ImageBitmap? =
        mediaImages[mediaId]
}
