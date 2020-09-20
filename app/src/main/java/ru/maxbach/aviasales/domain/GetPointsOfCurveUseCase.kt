package ru.maxbach.aviasales.domain

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.geometry.Point
import com.google.maps.android.projection.SphericalMercatorProjection
import javax.inject.Inject
import kotlin.math.pow

//TODO: move to data source
class GetPointsOfCurveUseCase @Inject constructor() {

    companion object {
        // TODO: move to di
        private const val DOTS_COUNT = 1000
    }

    // TODO: move to di
    private val mercatorProjection by lazy { SphericalMercatorProjection(360.0) }

    operator fun invoke(from: LatLng, to: LatLng): List<LatLng> {
        val startPoint = from.toPoint()
        val endPoint = to.toPoint()

        val (control1, control2, control3) = calculateControlPoints(startPoint, endPoint)

        return calculatePoints(DOTS_COUNT, startPoint, control1, control2, control3, endPoint)
    }

    private fun calculatePoints(
            dotsCount: Int,
            control1: Point,
            control2: Point,
            control3: Point,
            control4: Point,
            control5: Point
    ) = (0..dotsCount)
            .map {
                val t = it.toDouble() / dotsCount
                Point(
                        bezierFun(
                                control1.x,
                                control2.x,
                                control3.x,
                                control4.x,
                                control5.x,
                                t
                        ),
                        bezierFun(
                                control1.y,
                                control2.y,
                                control3.y,
                                control4.y,
                                control5.y,
                                t
                        )
                )
            }
            .map { it.toLatLng() }

    private fun LatLng.toPoint(): Point = mercatorProjection.toPoint(this)

    private fun Point.toLatLng(): LatLng = mercatorProjection.toLatLng(this)

    private fun bezierFun(point1: Double, point2: Double, point3: Double, point4: Double, point5: Double, t: Double) =
            point1 * (1 - t).pow(4) +
                    4 * point2 * (1 - t).pow(3) * t +
                    6 * point3 * (1 - t).pow(2) * t.pow(2) +
                    4 * point4 * (1 - t) * t.pow(3) +
                    point5 * t.pow(4)


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
