package ru.maxbach.aviasales.presentation.features.map

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.maxbach.aviasales.data.datasource.network.model.City

@Parcelize
class MapNavArgs(
  val cityFrom: City,
  val cityTo: City
) : Parcelable
