package ru.maxbach.aviasales.datasource.math

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.geometry.Point
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.maxbach.aviasales.di.math.DotsInCurveCount
import javax.inject.Inject

class BezierCurveCalculationDataSource @Inject constructor(
    @DotsInCurveCount private val dotsCount: Int
) {

    fun get(from: LatLng, to: LatLng): Single<List<LatLng>> = Single
        .fromCallable { return@fromCallable calculateCurve(from, to) }
        .subscribeOn(Schedulers.computation())

    private fun calculateCurve(from: LatLng, to: LatLng): List<LatLng> {
        val startPoint = from.toPoint()
        val endPoint = to.toPoint()

        val (control2, control3, control4) = calculateControlPoints(startPoint, endPoint)

        return calculatePoints(
            dotsCount,
            ControlPoints(startPoint, control2, control3, control4, endPoint)
        )
    }

    private fun calculatePoints(
        dotsCount: Int,
        controlPoints: ControlPoints
    ) = (0..dotsCount)
        .map {
            val t = it.toDouble() / dotsCount
            controlPoints.calculateBezierCurve(t)
        }
        .map { it.toLatLng() }


    //TODO: change square to more pretty figure
    private fun calculateControlPoints(start: Point, end: Point): Triple<Point, Point, Point> {
        val control2 = Point(
            (start.x + end.x) / 2,
            (start.y + end.y) / 2
        )
        val control1 = Point(
            control2.x - start.y + control2.y,
            control2.y + start.x - control2.x
        )
        val control3 = Point(
            control2.x - end.y + control2.y,
            control2.y + end.x - control2.x
        )

        return Triple(control1, control2, control3)
    }

}
