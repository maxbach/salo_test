package ru.maxbach.aviasales.feature.map

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import ru.maxbach.aviasales.base.viewmodel.viewModels
import ru.maxbach.aviasales.feature.map.state.MapScreenMainState
import ru.maxbach.aviasales.feature.map.utils.addCityMarker
import ru.maxbach.aviasales.feature.map.utils.addPlaneMarker
import ru.maxbach.aviasales.feature.map.utils.drawPlanePath
import ru.maxbach.aviasales.feature.map.utils.focusMapOnRoute
import ru.maxbach.aviasales.feature.map.utils.movePlane
import ru.maxbach.aviasales.feature.map.utils.setup

class MapFragment : SupportMapFragment() {

    companion object {
        private const val NAV_ARGS_KEY = "NAV_ARGS"

        fun create(navArgs: MapNavArgs): MapFragment = MapFragment().apply {
            arguments = bundleOf(NAV_ARGS_KEY to navArgs)
        }
    }

    private var planeMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO add assited inject
        viewModel.init(
            arguments?.getParcelable(NAV_ARGS_KEY)
                ?: throw IllegalStateException("Nav args not found. Use create() method to create fragment")
        )
    }

    private val viewModel by viewModels<MapScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            getMapAsync { it.setupFirst(state) }
        }

        viewModel.planePath.observe(viewLifecycleOwner) { planePath ->
            getMapAsync {
                it.drawPlanePath(requireContext(), planePath)
            }
        }

        getMapAsync { googleMap ->
            viewModel.planePosition.observe(viewLifecycleOwner) { planePosition ->
                planeMarker?.movePlane(planePosition) ?: run {
                    planeMarker = googleMap.addPlaneMarker(planePosition)
                }
            }
        }
    }

    private fun GoogleMap.setupFirst(mainState: MapScreenMainState) {
        setup()

        addCityMarker(requireContext(), mainState.cityFrom)
        addCityMarker(requireContext(), mainState.cityTo)

        focusMapOnRoute(mainState.cityFrom.location, mainState.cityTo.location)
    }

}
