package ru.maxbach.aviasales.data.datasource.math

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.geometry.Point
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.maxbach.aviasales.di.math.DotsInCurveCount
import javax.inject.Inject
import kotlin.math.max

class BezierCurveCalculationDataSource @Inject constructor(
    @DotsInCurveCount private val dotsCount: Int
) {

    fun get(from: LatLng, to: LatLng): Single<List<LatLng>> = Single
        .fromCallable { return@fromCallable calculateCurve(from, to) }
        .subscribeOn(Schedulers.computation())

    private fun calculateCurve(from: LatLng, to: LatLng): List<LatLng> {
        val startPoint = from.toPoint()
        val endPoint = to.toPoint()

        return if (isFlightsCrosses180meridian(startPoint, endPoint)) {
            val shiftX = 360 - max(startPoint.x, endPoint.x)

            val shiftedStartPoint = startPoint
                .shiftByX(shiftX)
                .normalizeByX()

            val shiftedEndPoint = endPoint
                .shiftByX(shiftX)
                .normalizeByX()

            calculatePoints(
                dotsCount,
                calculateControlPoints(shiftedStartPoint, shiftedEndPoint),
                shiftX
            )
        } else {
            calculatePoints(
                dotsCount,
                calculateControlPoints(startPoint, endPoint)
            )
        }

    }

    private fun calculatePoints(
      dotsCount: Int,
      controlPoints: ControlPoints,
      shiftX: Double = 0.0
    ) = (0..dotsCount)
        .map {
            val t = it.toDouble() / dotsCount
            controlPoints.calculateBezierCurve(t, shiftX)
        }
        .map { it.toLatLng() }


    // rhombus.
    // first diagonal size is distance between start and end.
    // second diagonal size is 3/4 of first diagonal
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

}
