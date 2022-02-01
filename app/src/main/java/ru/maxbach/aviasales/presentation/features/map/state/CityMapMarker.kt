package ru.maxbach.aviasales.presentation.features.map.state

import com.google.android.gms.maps.model.LatLng
import ru.maxbach.aviasales.data.datasource.network.model.City
import ru.maxbach.aviasales.data.datasource.network.model.toLatLng

data class CityMapMarker(
        val location: LatLng = LatLng(0.0, 0.0),
        val shortName: String = ""
)

fun City.mapToMarker() = CityMapMarker(
        location = this.location.toLatLng(),
        shortName = this.name.take(3)
)
