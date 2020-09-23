package ru.maxbach.aviasales.datasource.math

import com.google.maps.android.geometry.Point

class ControlPoints(
    val control1: Point,
    val control2: Point,
    val control3: Point,
    val control4: Point,
    val control5: Point
) {

    fun getXCoordinates() = ControlPointCoordinates(
        control1.x,
        control2.x,
        control3.x,
        control4.x,
        control5.x
    )

    fun getYCoordinates() = ControlPointCoordinates(
        control1.y,
        control2.y,
        control3.y,
        control4.y,
        control5.y
    )

}
