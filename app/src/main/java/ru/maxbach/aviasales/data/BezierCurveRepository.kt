package ru.maxbach.aviasales.data

import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.datasource.cache.BezierCurveCacheDataSource
import ru.maxbach.aviasales.datasource.math.BezierCurveCalculationDataSource
import javax.inject.Inject

class BezierCurveRepository @Inject constructor(
    private val bezierCurveCalculationDataSource: BezierCurveCalculationDataSource,
    private val bezierCurveCacheDataSource: BezierCurveCacheDataSource
) {

    fun get(from: LatLng, to: LatLng): Single<List<LatLng>> = bezierCurveCacheDataSource
        .get(from, to)
        ?.let { Single.just(it) }
        ?: getNewCurve(from, to)

    private fun getNewCurve(from: LatLng, to: LatLng) = bezierCurveCalculationDataSource
        .get(from, to)
        .doOnSuccess(bezierCurveCacheDataSource::put)

}
