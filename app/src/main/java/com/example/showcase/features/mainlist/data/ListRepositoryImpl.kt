package com.example.showcase.features.mainlist.data

import com.example.showcase.features.mainlist.data.model.MediaData
import com.example.showcase.features.mainlist.domain.ListRepository
import com.example.showcase.features.mainlist.domain.model.Media
import com.example.showcase.features.mainlist.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListRepositoryImpl(
    private val api: VegansLabAPI
): ListRepository {
    override suspend fun getList(): Result<List<Media>> = runCatching {
        withContext(Dispatchers.IO) {
            api.getItems().content
                .map(MediaData::toDomain)
        }
    }
}