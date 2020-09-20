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
            .intervalRange(0, 1000, 0, 1000 / 25, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .zipWith(Observable.fromIterable(
                    curve.mapIndexed { index, latLng -> index to latLng }
            ), { _, (index, latLng) ->
                PlanePosition(
                        location = latLng,
                        angle = SphericalUtil.computeHeading(
                                latLng,
                                curve.getOrElse(index + 1) { curve.last() }
                        ).toFloat() - 90f
                )
            })

}
