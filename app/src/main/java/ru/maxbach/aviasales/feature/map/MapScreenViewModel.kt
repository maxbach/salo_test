package ru.maxbach.aviasales.feature.map

import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.datasource.network.model.toLatLng
import ru.maxbach.aviasales.domain.GetPointsOfCurveUseCase
import ru.maxbach.aviasales.domain.MovePlaneUseCase
import ru.maxbach.aviasales.feature.map.state.MapScreenState
import ru.maxbach.aviasales.feature.map.state.mapToMarker
import javax.inject.Inject

class MapScreenViewModel @Inject constructor(
    private val getPointsOfCurveUseCase: GetPointsOfCurveUseCase,
    private val movePlaneUseCase: MovePlaneUseCase
) : BaseViewModel<MapScreenState>(MapScreenState()) {

    fun init(navArgs: MapNavArgs) {
        val cityFrom = navArgs.cityFrom
        val cityTo = navArgs.cityTo

        val planePath = getPointsOfCurveUseCase.invoke(
            from = cityFrom.location.toLatLng(),
            to = cityTo.location.toLatLng()
        )

        updateState {
            MapScreenState(
                cityFrom = cityFrom.mapToMarker(),
                cityTo = cityTo.mapToMarker(),
                planePath = planePath
            )
        }

        startPlaneFlight(planePath)

    }

    private fun startPlaneFlight(planePath: List<LatLng>) {
        movePlaneUseCase
            .invoke(planePath)
            .observeOn(AndroidSchedulers.mainThread())
            .untilDestroy(onNext = {
                updateState { currentState ->
                    currentState.copy(plane = it)
                }
            })
    }

}
