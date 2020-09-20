package ru.maxbach.aviasales.feature.plane

import com.google.android.gms.maps.model.LatLng

class PlaneScreenState(
        val cityFromLocation: LatLng = LatLng(0.0, 0.0),
        val cityToLocation: LatLng = LatLng(0.0, 0.0)
)
