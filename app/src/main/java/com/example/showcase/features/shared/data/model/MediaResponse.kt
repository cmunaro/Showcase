package com.example.showcase.features.shared.data.model

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
    @SerialName("mediaType") val type: MediaType,
    @SerialName("mediaDate") val date: MediaDate,
    @SerialName("mediaTitleCustom") val titleCustom: String
)

@Serializable
data class MediaDate(
    val dateString: String,
    val year: String
)

@Serializable
enum class MediaType {
    @SerialName("pdf")
    PDF
}
