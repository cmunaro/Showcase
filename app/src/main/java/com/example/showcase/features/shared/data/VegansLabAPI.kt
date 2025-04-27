package com.example.showcase.features.shared.data

import com.example.showcase.features.shared.data.model.MediaResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Url

interface VegansLabAPI {
    @GET("test.json")
    suspend fun getItems(): MediaResponse

    @GET
    suspend fun downloadPdf(@Url url: String): ByteArray
}