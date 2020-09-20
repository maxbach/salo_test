package ru.maxbach.aviasales.feature.search

import ru.maxbach.aviasales.network.model.City

data class SearchScreenState(
        val suggestions: List<City> = emptyList()
)
