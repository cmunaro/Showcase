package com.example.showcase.features.mainlist.data

import com.example.showcase.features.mainlist.data.model.MediaResponse
import de.jensklingenberg.ktorfit.http.GET

interface VegansLabAPI {
    @GET("test.json")
    suspend fun getItems(): MediaResponse
}