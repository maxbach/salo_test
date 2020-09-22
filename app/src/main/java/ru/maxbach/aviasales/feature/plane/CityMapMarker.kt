package ru.maxbach.aviasales.feature.plane

import com.google.android.gms.maps.model.LatLng

data class CityMapMarker(
        val location: LatLng = LatLng(0.0, 0.0),
        val shortName: String = ""
)
