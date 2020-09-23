package ru.maxbach.aviasales.feature.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.maxbach.aviasales.domain.models.RoutePoint

@Parcelize
class SearchScreenNavArgs(
    val routePoint: RoutePoint
) : Parcelable
