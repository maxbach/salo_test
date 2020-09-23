package ru.maxbach.aviasales.feature.map.utils

import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.feature.map.state.CityMapMarker
import ru.maxbach.aviasales.feature.map.state.PlanePosition

fun GoogleMap.addCityMarker(context: Context, cityMapMarker: CityMapMarker) {
    addMarker(
        MarkerOptions()
            .position(cityMapMarker.location)
            .icon(context.createCityMarker(cityMapMarker.shortName))
            .alpha(0.7f)
            .anchor(0.5f, 0.5f)
    )
}

fun GoogleMap.addPlaneMarker(planePosition: PlanePosition): Marker = addMarker(
    MarkerOptions()
        .anchor(0.5f, 0.5f)
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_plane))
        .position(planePosition.location)
        .rotation(planePosition.angle)
)

fun Marker.movePlane(planePosition: PlanePosition) {
    position = planePosition.location
    rotation = planePosition.angle
}

fun GoogleMap.setup() {
    uiSettings.isMapToolbarEnabled = false
    uiSettings.isRotateGesturesEnabled = false
    isBuildingsEnabled = false
    setMaxZoomPreference(9f)
}

fun GoogleMap.focusMapOnRoute(cityFromLocation: LatLng, cityToLocation: LatLng) {
    moveCamera(
        CameraUpdateFactory.newLatLngBounds(
            LatLngBounds.builder()
                .include(cityFromLocation)
                .include(cityToLocation)
                .build(),
            20
        )
    )

}

fun GoogleMap.drawPlanePath(planePath: List<LatLng>) {
    addPolyline(
        PolylineOptions()
            .add(*planePath.toTypedArray())
            .pattern(listOf(Dash(10f), Gap(20f)))
    )
}
