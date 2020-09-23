package ru.maxbach.aviasales.feature.map.state

data class MapScreenMainState(
    val cityFrom: CityMapMarker = CityMapMarker(),
    val cityTo: CityMapMarker = CityMapMarker()
)
