package com.example.showcase.features.mainlist.domain.model

import com.example.showcase.features.mainlist.data.model.MediaData
import com.example.showcase.features.mainlist.data.model.MediaDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Media(
    val id: Int,
    val url: String,
    val urlBig: String,
    val type: String,
    val date: LocalDateTime,
    val title: String
)

fun MediaData.toDomain() = Media(
    id = id,
    url = url,
    urlBig = urlBig,
    type = type,
    date = date.toDateTime(),
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