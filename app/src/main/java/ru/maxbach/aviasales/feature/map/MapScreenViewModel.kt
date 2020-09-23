package ru.maxbach.aviasales.feature.map

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.datasource.network.model.City
import ru.maxbach.aviasales.datasource.network.model.toLatLng
import ru.maxbach.aviasales.domain.GetBezierCurveUseCase
import ru.maxbach.aviasales.domain.MovePlaneUseCase
import ru.maxbach.aviasales.feature.map.state.MapScreenMainState
import ru.maxbach.aviasales.feature.map.state.PlanePosition
import ru.maxbach.aviasales.feature.map.state.mapToMarker
import ru.maxbach.aviasales.utils.toImmutable
import javax.inject.Inject

class MapScreenViewModel @Inject constructor(
    private val getPointsOfCurveUseCase: GetBezierCurveUseCase,
    private val movePlaneUseCase: MovePlaneUseCase
) : BaseViewModel<MapScreenMainState>(MapScreenMainState()) {

    private val _planePath = MutableLiveData<List<LatLng>>()
    val planePath = _planePath.toImmutable()

    private val _planePosition = MutableLiveData<PlanePosition>()
    val planePosition = _planePosition.toImmutable()

    private lateinit var cityFrom: City
    private lateinit var cityTo: City

    fun init(navArgs: MapNavArgs) {
        cityFrom = navArgs.cityFrom
        cityTo = navArgs.cityTo

        updateState {
            MapScreenMainState(
                cityFrom = cityFrom.mapToMarker(),
                cityTo = cityTo.mapToMarker()
            )
        }

        getPointsOfCurveUseCase(cityFrom.location.toLatLng(), cityTo.location.toLatLng())
            .observeOn(AndroidSchedulers.mainThread())
            .untilDestroy(onSuccess = { planePath ->
                _planePath.value = planePath
                startPlaneFlight(cityFrom.location.toLatLng(), cityTo.location.toLatLng())
            })

    }

    private fun startPlaneFlight(from: LatLng, to: LatLng) {
        movePlaneUseCase(from, to)
            .observeOn(AndroidSchedulers.mainThread())
            .untilDestroy(onNext = {
                _planePosition.value = it
            })
    }

}
