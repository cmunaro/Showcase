package com.example.showcase.ui

import java.time.LocalDateTime

fun LocalDateTime.toLocalDateTimeString(): String = "$dayOfMonth/$monthValue/$year"