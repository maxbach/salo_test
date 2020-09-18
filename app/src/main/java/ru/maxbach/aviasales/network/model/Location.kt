package ru.maxbach.aviasales.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
        @Json(name = "lat")
        val latitude: Double,

        @Json(name = "lon")
        val longitude: Double
)
