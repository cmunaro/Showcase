package com.example.showcase.features.mainlist.data

import com.example.showcase.features.mainlist.domain.model.Media

interface MediaStorage {
    fun save(medias: List<Media>)
    fun getOrNull(mediaId: Int): Media?
}

class InMemoryMediaStorage : MediaStorage {
    private val medias = mutableListOf<Media>()

    override fun save(medias: List<Media>) {
        this.medias.clear()
        this.medias.addAll(medias)
    }

    override fun getOrNull(mediaId: Int): Media? =
        medias.firstOrNull { it.id == mediaId }
}
