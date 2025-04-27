package com.example.showcase.features.mainlist.domain.model

import com.example.showcase.features.shared.data.model.MediaData
import com.example.showcase.features.shared.data.model.MediaDate
import com.example.showcase.features.shared.data.model.MediaType
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Media(
    val id: Int,
    val url: String,
    val urlBig: String,
    val type: MediaType,
    val dateTime: LocalDateTime,
    val title: String
)

fun MediaData.toDomain() = Media(
    id = id,
    url = url,
    urlBig = urlBig,
    type = type,
    dateTime = date.toDateTime(),
    title = titleCustom
)

fun MediaDate.toDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(
        "EEE, dd MMM yyyy HH:mm:ss XX",
        Locale.ENGLISH
    )
    return ZonedDateTime.parse(dateString, formatter)
        .toLocalDateTime()
}