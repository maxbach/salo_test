package ru.maxbach.aviasales.domain

import com.google.android.gms.maps.model.LatLng
import ru.maxbach.aviasales.data.repo.BezierCurveRepository
import javax.inject.Inject

class GetBezierCurveUseCase @Inject constructor(
    private val bezierCurveRepository: BezierCurveRepository
) {

    operator fun invoke(from: LatLng, to: LatLng) = bezierCurveRepository
        .get(from, to)
}
