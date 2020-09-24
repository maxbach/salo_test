package ru.maxbach.aviasales.feature.map.utils

import android.content.Context
import android.content.res.Configuration
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.feature.map.state.CityMapMarker
import ru.maxbach.aviasales.feature.map.state.PlanePosition

private const val CAMERA_WIDTH_EDGE_PADDING_PERCENT = .1f
private const val CAMERA_HEIGHT_EDGE_PADDING_PERCENT = .2f

fun GoogleMap.addCityMarker(context: Context, cityMapMarker: CityMapMarker) {
    addMarker(
        MarkerOptions()
            .position(cityMapMarker.location)
            .icon(context.createCityMarker(cityMapMarker.shortName))
            .alpha(0.9f)
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

fun GoogleMap.focusMapOnRoute(context: Context, cityFromLocation: LatLng, cityToLocation: LatLng) {
    LatLngBounds.builder()
        .include(cityFromLocation)
        .include(cityToLocation)
        .build()
        .let { bounds ->
            val width = context.resources.displayMetrics.widthPixels
            val height = context.resources.displayMetrics.heightPixels
            val padding = context.calculateCameraPadding(width, height)
            moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
        }
}

fun GoogleMap.drawPlanePath(context: Context, planePath: List<LatLng>) {
    addPolyline(
        PolylineOptions()
            .add(*planePath.toTypedArray())
            .pattern(listOf(Dot(), Gap(10f)))
            .color(context.getColor(R.color.colorPrimaryDark))
    )
}

private fun Context.calculateCameraPadding(width: Int, height: Int): Int {
    val padding = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        width * CAMERA_WIDTH_EDGE_PADDING_PERCENT
    } else {
        height * CAMERA_HEIGHT_EDGE_PADDING_PERCENT
    }

    return padding.toInt()
}
