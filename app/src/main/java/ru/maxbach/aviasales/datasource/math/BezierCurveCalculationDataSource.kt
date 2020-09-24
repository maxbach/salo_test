package ru.maxbach.aviasales.datasource.math

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.geometry.Point
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.maxbach.aviasales.di.math.DotsInCurveCount
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.sin

class BezierCurveCalculationDataSource @Inject constructor(
    @DotsInCurveCount private val dotsCount: Int
) {

    fun get(from: LatLng, to: LatLng): Single<List<LatLng>> = Single
        .fromCallable { return@fromCallable calculateCurve(from, to) }
        .subscribeOn(Schedulers.computation())

    private fun calculateCurve(from: LatLng, to: LatLng): List<LatLng> {
        val startPoint = from.toPoint()
        val endPoint = to.toPoint()

        return calculatePoints(
            dotsCount,
            calculateControlPoints(startPoint, endPoint)
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


    // rhombus. first diagonal size is distance between start and end. second diagonal size is 3/4 of first diagonal
    private fun calculateControlPoints(start: Point, end: Point): ControlPoints {
        val middle = pointBetween(start, end, 0.5)

        val control1 = pointBetween(start, middle, 0.25).rotateAround(
            middle,
            Math.PI / 2
        )

        val control3 = pointBetween(end, middle, 0.25).rotateAround(
            middle,
            Math.PI / 2
        )

        return ControlPoints(start, control1, control3, end)
    }

    private fun Point.rotateAround(center: Point, radians: Double) = Point(
        center.x + (this.x - center.x) * cos(radians) - (this.y - center.y) * sin(radians),
        center.y + (this.x - center.x) * sin(radians) + (this.y - center.y) * cos(radians)
    )

    private fun pointBetween(p1: Point, p2: Point, t: Double): Point = Point(
        p1.x * (1 - t) + p2.x * t,
        p1.y * (1 - t) + p2.y * t
    )

}
