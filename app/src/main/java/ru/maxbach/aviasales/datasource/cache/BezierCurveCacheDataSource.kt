package ru.maxbach.aviasales.datasource.cache

import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BezierCurveCacheDataSource @Inject constructor() {

    private val curveCache = BehaviorSubject.create<List<LatLng>>()

    fun get(from: LatLng, to: LatLng): List<LatLng>? = curveCache.value?.takeIf {
        it.first() == from && it.last() == to
    }

    fun put(newCurve: List<LatLng>) = curveCache.onNext(newCurve)
}
