package ru.maxbach.aviasales.feature.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.google.android.gms.maps.model.LatLng
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.datasource.network.model.toLatLng
import ru.maxbach.aviasales.di.viewmodel.ViewModelAssistedFactory
import ru.maxbach.aviasales.domain.GetBezierCurveUseCase
import ru.maxbach.aviasales.domain.MovePlaneUseCase
import ru.maxbach.aviasales.feature.map.state.MapScreenMainState
import ru.maxbach.aviasales.feature.map.state.PlanePosition
import ru.maxbach.aviasales.feature.map.state.mapToMarker
import ru.maxbach.aviasales.utils.toImmutable

class MapScreenViewModel @AssistedInject constructor(
    private val getPointsOfCurveUseCase: GetBezierCurveUseCase,
    private val movePlaneUseCase: MovePlaneUseCase,
    @Assisted arg0: SavedStateHandle
) : BaseViewModel<MapScreenMainState, MapNavArgs>(MapScreenMainState(), arg0) {

    private val _planePath = MutableLiveData<List<LatLng>>()
    val planePath = _planePath.toImmutable()

    private val _planePosition = MutableLiveData<PlanePosition>()
    val planePosition = _planePosition.toImmutable()

    init {
        updateState {
            MapScreenMainState(
                cityFrom = navArgs.cityFrom.mapToMarker(),
                cityTo = navArgs.cityTo.mapToMarker()
            )
        }

        val fromLatLng = navArgs.cityFrom.location.toLatLng()
        val toLatLng = navArgs.cityTo.location.toLatLng()

        calculatePlanePath(fromLatLng, toLatLng)
    }

    private fun calculatePlanePath(fromLatLng: LatLng, toLatLng: LatLng) {
        getPointsOfCurveUseCase(fromLatLng, toLatLng)
            .observeOn(AndroidSchedulers.mainThread())
            .untilDestroy(onSuccess = { planePath ->
                _planePath.value = planePath
                startPlaneFlight(fromLatLng, toLatLng)
            })
    }

    private fun startPlaneFlight(from: LatLng, to: LatLng) {
        movePlaneUseCase(from, to)
            .observeOn(AndroidSchedulers.mainThread())
            .untilDestroy(_planePosition::setValue)
    }

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<MapScreenViewModel>

}
