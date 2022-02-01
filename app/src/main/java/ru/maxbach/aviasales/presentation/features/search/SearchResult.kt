package ru.maxbach.aviasales.presentation.features.search

import ru.maxbach.aviasales.data.datasource.network.model.City
import ru.maxbach.aviasales.domain.models.RoutePoint

class SearchResult(
  val city: City,
  val routePoint: RoutePoint
)
