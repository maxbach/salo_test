package ru.maxbach.aviasales.domain.models

import ru.maxbach.aviasales.data.datasource.network.model.City

class Suggestions(
  val cities: List<City>,
  val isFromRecents: Boolean
)
