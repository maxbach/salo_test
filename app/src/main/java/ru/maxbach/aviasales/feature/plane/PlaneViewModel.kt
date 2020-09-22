package ru.maxbach.aviasales.feature.plane

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.domain.GetPointsOfCurveUseCase
import ru.maxbach.aviasales.domain.MovePlaneUseCase
import ru.maxbach.aviasales.network.model.City
import ru.maxbach.aviasales.network.model.toLatLng
import javax.inject.Inject

class PlaneViewModel @Inject constructor(
        private val getPointsOfCurveUseCase: GetPointsOfCurveUseCase,
        private val movePlaneUseCase: MovePlaneUseCase
) : BaseViewModel<PlaneScreenState>(PlaneScreenState()) {

    private lateinit var cityFrom: City
    private lateinit var cityTo: City

    fun init(cityFrom: City, cityTo: City) {
        this.cityFrom = cityFrom
        this.cityTo = cityTo

        val cityFromLatLng = cityFrom.location.toLatLng()
        val cityToLatLng = cityTo.location.toLatLng()

        val curve = getPointsOfCurveUseCase.invoke(cityFromLatLng, cityToLatLng)
        updateState {
            PlaneScreenState(
                    CityMapMarker(cityFromLatLng, cityFrom.name.take(3)),
                    CityMapMarker(cityToLatLng, cityTo.name.take(3)),
                    curve
            )
        }

        movePlaneUseCase
                .invoke(curve)
                .observeOn(AndroidSchedulers.mainThread())
                .untilDestroy(onNext = {
                    updateState { currentState ->
                        currentState.copy(plane = it)
                    }
                })

    }

}
