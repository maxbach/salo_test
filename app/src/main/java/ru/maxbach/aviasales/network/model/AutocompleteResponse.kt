package ru.maxbach.aviasales.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AutocompleteResponse(
        val cities: List<City> = emptyList()
)
