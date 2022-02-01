package ru.maxbach.aviasales.presentation.features.map.state

data class MapScreenMainState(
  val cityFrom: CityMapMarker = CityMapMarker(),
  val cityTo: CityMapMarker = CityMapMarker()
)
