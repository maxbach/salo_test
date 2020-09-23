package ru.maxbach.aviasales.feature.search

import android.os.Parcelable
import androidx.annotation.IdRes
import kotlinx.android.parcel.Parcelize
import ru.maxbach.aviasales.R

@Parcelize
class SearchScreenNavArgs(
    val routePoint: RoutePoint
) : Parcelable

//TODO: move to separate file
enum class RoutePoint(@IdRes val inputHintId: Int) {
    FROM(R.string.global_input_from_hint),
    TO(R.string.global_input_to_hint)
}
