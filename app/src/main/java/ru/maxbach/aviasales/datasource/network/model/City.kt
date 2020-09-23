package ru.maxbach.aviasales.datasource.network.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class City(
    val id: Long,
    @Json(name = "fullname")
    val fullName: String,
    val location: Location,
    @Json(name = "city")
    val name: String
) : Parcelable
