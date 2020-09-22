package ru.maxbach.aviasales.domain

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.maxbach.aviasales.feature.plane.PlanePosition
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovePlaneUseCase @Inject constructor() {

    operator fun invoke(curve: List<LatLng>): Observable<PlanePosition> = Observable
            .intervalRange(0, 1001, 0, 1000 / 25, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .zipWith(Observable.fromIterable(
                    curve.mapIndexed { index, latLng -> index to latLng }
            ), { _, (index, latLng) ->

                val firstPointForAngle = if (latLng == curve.last()) curve[index - 1] else latLng
                val secondPointForAngle = if (latLng == curve.last()) latLng else curve[index + 1]

                val angle = SphericalUtil.computeHeading(firstPointForAngle, secondPointForAngle).toFloat()

                PlanePosition(
                        location = latLng,
                        angle = angle - 90f
                )
            })

}
