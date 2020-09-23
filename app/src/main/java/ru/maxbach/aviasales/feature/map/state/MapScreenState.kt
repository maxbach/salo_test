package ru.maxbach.aviasales.feature.map.state

import com.google.android.gms.maps.model.LatLng

data class MapScreenState(
    val cityFrom: CityMapMarker = CityMapMarker(),
    val cityTo: CityMapMarker = CityMapMarker(),
    val planePath: List<LatLng> = listOf(cityFrom.location, cityTo.location),
    val plane: PlanePosition = PlanePosition(cityFrom.location, 90f)
)
