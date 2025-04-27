package com.example.showcase.features.mainlist.data

import com.example.showcase.features.mainlist.data.model.MediaData
import com.example.showcase.features.mainlist.domain.MediaRepository
import com.example.showcase.features.mainlist.domain.model.Media
import com.example.showcase.features.mainlist.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepositoryImpl(
    private val api: VegansLabAPI,
    private val mediaStorage: MediaStorage
) : MediaRepository {
    override suspend fun getList(): Result<List<Media>> = runCatching {
        withContext(Dispatchers.IO) {
            api.getItems().content
                .map(MediaData::toDomain)
                .also { mediaStorage.save(it) }
        }
    }

    override fun getMedia(mediaId: Int) =
        mediaStorage.getOrNull(mediaId)
}