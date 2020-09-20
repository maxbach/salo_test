package ru.maxbach.aviasales.feature.plane

import com.google.android.gms.maps.model.LatLng

data class PlaneScreenState(
        val cityFromLocation: LatLng = LatLng(0.0, 0.0),
        val cityToLocation: LatLng = LatLng(0.0, 0.0),
        val pointsOfCurve: List<LatLng> = listOf(cityFromLocation, cityToLocation),
        val plane: PlanePosition = PlanePosition(cityFromLocation, 90f)
)
