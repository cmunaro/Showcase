package com.example.showcase.features.mainlist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaResponse(
    val status: Boolean,
    val lang: String,
    val content: List<MediaData>
)

@Serializable
data class MediaData(
    @SerialName("mediaId") val id: Int,
    @SerialName("mediaUrl") val url: String,
    @SerialName("mediaUrlBig") val urlBig: String,
    @SerialName("mediaType") val type: String,
    @SerialName("mediaDate") val date: MediaDate,
    @SerialName("mediaTitleCustom") val titleCustom: String
)

@Serializable
data class MediaDate(
    val dateString: String,
    val year: String
)
