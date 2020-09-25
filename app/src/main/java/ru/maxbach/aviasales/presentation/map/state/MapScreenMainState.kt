package ru.maxbach.aviasales.presentation.map.state

data class MapScreenMainState(
    val cityFrom: CityMapMarker = CityMapMarker(),
    val cityTo: CityMapMarker = CityMapMarker()
)
