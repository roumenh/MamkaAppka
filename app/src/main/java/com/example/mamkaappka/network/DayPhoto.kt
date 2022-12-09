package com.example.mamkaappka.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DayPhoto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "date")
    val date: String,
    @Json(name = "file")
    val file: String,
    @Json(name = "text")
    val text: String
)