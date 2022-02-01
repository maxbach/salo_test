package ru.maxbach.aviasales.data.datasource.math

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.geometry.Point
import com.google.maps.android.projection.SphericalMercatorProjection


private val mercatorProjection by lazy { SphericalMercatorProjection(360.0) }

fun LatLng.toPoint(): Point = mercatorProjection.toPoint(this)

fun Point.toLatLng(): LatLng = mercatorProjection.toLatLng(this)
