package ru.maxbach.aviasales.network.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Location(
        @Json(name = "lat")
        val latitude: Double,

        @Json(name = "lon")
        val longitude: Double
) : Parcelable

fun Location.toLatLng() = LatLng(latitude, longitude)
