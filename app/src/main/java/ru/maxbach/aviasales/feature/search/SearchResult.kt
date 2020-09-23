package ru.maxbach.aviasales.feature.search

import ru.maxbach.aviasales.datasource.network.model.City
import ru.maxbach.aviasales.domain.models.RoutePoint

class SearchResult(
    val city: City,
    val routePoint: RoutePoint
)
