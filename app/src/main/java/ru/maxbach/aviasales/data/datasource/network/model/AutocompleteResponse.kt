package ru.maxbach.aviasales.data.datasource.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AutocompleteResponse(
        val cities: List<City> = emptyList()
)
