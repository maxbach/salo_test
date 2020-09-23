package ru.maxbach.aviasales.feature.search

import ru.maxbach.aviasales.network.model.City

class Suggestions(
    val cities: List<City>,
    val isFromRecents: Boolean
)
