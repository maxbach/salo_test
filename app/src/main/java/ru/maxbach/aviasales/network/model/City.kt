package ru.maxbach.aviasales.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
        val id: Long,
        @Json(name = "fullname") val fullName: String,
        val location: Location,
        @Json(name = "iata") val aliases: List<String> = emptyList(),
        @Json(name = "city") val name: String
)
