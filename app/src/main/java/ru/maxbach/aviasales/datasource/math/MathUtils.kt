package ru.maxbach.aviasales.datasource.math

import com.google.maps.android.geometry.Point
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

fun isFlightsCrosses180meridian(start: Point, end: Point) = abs(start.x - end.x) > 180

fun Point.shiftByX(dx: Double) = Point(x + dx, y)

fun Point.normalizeByX() = Point(x % 360.0, y)

fun Point.rotateAround(center: Point, radians: Double) = Point(
    center.x + (this.x - center.x) * cos(radians) - (this.y - center.y) * sin(radians),
    center.y + (this.x - center.x) * sin(radians) + (this.y - center.y) * cos(radians)
)

fun pointBetween(p1: Point, p2: Point, t: Double): Point = Point(
    p1.x * (1 - t) + p2.x * t,
    p1.y * (1 - t) + p2.y * t
)
