package ru.maxbach.aviasales.datasource

import com.squareup.moshi.JsonClass
import ru.maxbach.aviasales.network.model.City

@JsonClass(generateAdapter = true)
data class LastSearch(
        val cityFrom: City,
        val cityTo: City
)
