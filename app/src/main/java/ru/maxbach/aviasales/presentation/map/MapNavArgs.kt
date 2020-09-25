package ru.maxbach.aviasales.presentation.map

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.maxbach.aviasales.datasource.network.model.City

@Parcelize
class MapNavArgs(
    val cityFrom: City,
    val cityTo: City
) : Parcelable
