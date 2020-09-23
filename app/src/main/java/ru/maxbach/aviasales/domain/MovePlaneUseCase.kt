package ru.maxbach.aviasales.domain

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.maxbach.aviasales.feature.map.state.PlanePosition
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovePlaneUseCase @Inject constructor() {

    companion object {
        private const val ONE_FRAME_DELAY = 16L
    }

    operator fun invoke(planePath: List<LatLng>): Observable<PlanePosition> = Observable
        .fromIterable(planePath.mapToPlanePosition())
        .zipWith(Observable.interval(ONE_FRAME_DELAY, TimeUnit.MILLISECONDS)) { plane, _ -> plane }
        .subscribeOn(Schedulers.computation())

    private fun List<LatLng>.mapToPlanePosition() = this
        .mapIndexed { index, latLng ->
            val planePath = this

            val firstPointForAngle = if (latLng == planePath.last()) {
                planePath[index - 1]
            } else {
                latLng
            }

            val secondPointForAngle = if (latLng == planePath.last()) {
                latLng
            } else {
                planePath[index + 1]
            }

            val angle = SphericalUtil
                .computeHeading(firstPointForAngle, secondPointForAngle)
                .toFloat()

            PlanePosition(
                location = latLng,
                angle = angle - 90f
            )
        }

}
