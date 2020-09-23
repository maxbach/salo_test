package ru.maxbach.aviasales.datasource.math

import com.google.maps.android.geometry.Point
import kotlin.math.pow

fun ControlPoints.calculateBezierCurve(t: Double): Point = Point(
    bezierFun(getXCoordinates(), t),
    bezierFun(getYCoordinates(), t)
)

private fun bezierFun(controlPoints: ControlPointCoordinates, t: Double) =
    controlPoints.position1 * (1 - t).pow(4) +
        4 * controlPoints.position2 * (1 - t).pow(3) * t +
        6 * controlPoints.position3 * (1 - t).pow(2) * t.pow(2) +
        4 * controlPoints.position4 * (1 - t) * t.pow(3) +
        controlPoints.position5 * t.pow(4)
