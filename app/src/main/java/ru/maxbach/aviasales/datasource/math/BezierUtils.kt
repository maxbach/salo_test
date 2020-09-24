package ru.maxbach.aviasales.datasource.math

import com.google.maps.android.geometry.Point
import kotlin.math.pow

fun ControlPoints.calculateBezierCurve(t: Double, shiftX: Double = 0.0): Point = Point(
    bezierFun(getXCoordinates(), t) - shiftX,
    bezierFun(getYCoordinates(), t)
)

private fun bezierFun(controlPoints: ControlPointCoordinates, t: Double) =
    controlPoints.position1 * (1 - t).pow(3) +
        3 * controlPoints.position2 * (1 - t).pow(2) * t +
        3 * controlPoints.position3 * (1 - t) * t.pow(2) +
        controlPoints.position4 * t.pow(3)
