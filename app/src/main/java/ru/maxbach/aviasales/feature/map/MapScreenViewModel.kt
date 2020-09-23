package ru.maxbach.aviasales.feature.map

import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.datasource.network.model.toLatLng
import ru.maxbach.aviasales.domain.GetBezierCurveUseCase
import ru.maxbach.aviasales.domain.MovePlaneUseCase
import ru.maxbach.aviasales.feature.map.state.MapScreenState
import ru.maxbach.aviasales.feature.map.state.mapToMarker
import javax.inject.Inject

class MapScreenViewModel @Inject constructor(
    private val getPointsOfCurveUseCase: GetBezierCurveUseCase,
    private val movePlaneUseCase: MovePlaneUseCase
) : BaseViewModel<MapScreenState>(MapScreenState()) {

    fun init(navArgs: MapNavArgs) {
        val cityFrom = navArgs.cityFrom
        val cityTo = navArgs.cityTo

        val cityFromLocation = cityFrom.location.toLatLng()
        val cityToLocation = cityTo.location.toLatLng()

        getPointsOfCurveUseCase(cityFromLocation, cityToLocation)
            .observeOn(AndroidSchedulers.mainThread())
            .untilDestroy(onSuccess = { planePath ->
                updateState {
                    MapScreenState(
                        cityFrom = cityFrom.mapToMarker(),
                        cityTo = cityTo.mapToMarker(),
                        planePath = planePath
                    )
                }

                startPlaneFlight(cityFromLocation, cityToLocation)
            })

    }

    private fun startPlaneFlight(from: LatLng, to: LatLng) {
        movePlaneUseCase(from, to)
            .observeOn(AndroidSchedulers.mainThread())
            .untilDestroy(onNext = {
                updateState { currentState ->
                    currentState.copy(plane = it)
                }
            })
    }

}
