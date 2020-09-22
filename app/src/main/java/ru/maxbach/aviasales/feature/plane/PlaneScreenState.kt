package ru.maxbach.aviasales.feature.plane

import com.google.android.gms.maps.model.LatLng

data class PlaneScreenState(
        val cityFrom: CityMapMarker = CityMapMarker(),
        val cityTo: CityMapMarker = CityMapMarker(),
        val pointsOfCurve: List<LatLng> = listOf(cityFrom.location, cityTo.location),
        val plane: PlanePosition = PlanePosition(cityFrom.location, 90f)
)
