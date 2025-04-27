package com.example.showcase.features.mainlist.domain

import com.example.showcase.features.mainlist.domain.model.Media

interface ListRepository {
    suspend fun getList(): Result<List<Media>>

}