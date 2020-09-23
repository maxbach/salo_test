package ru.maxbach.aviasales.feature.map

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import ru.maxbach.aviasales.base.viewmodel.viewModels
import ru.maxbach.aviasales.feature.map.state.MapScreenState
import ru.maxbach.aviasales.feature.map.utils.*

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

        viewModel.state.observe(viewLifecycleOwner, { state ->
            getMapAsync { googleMap ->
                val currentPlaneMarker = planeMarker

                if (currentPlaneMarker != null) {
                    currentPlaneMarker.movePlane(state.plane)
                } else {
                    googleMap.setupFirst(state)
                }
            }
        })
    }

    private fun GoogleMap.setupFirst(state: MapScreenState) {
        setup()

        addCityMarker(requireContext(), state.cityFrom)
        addCityMarker(requireContext(), state.cityTo)
        planeMarker = addPlaneMarker(state.plane)

        focusMapOnRoute(state.cityFrom.location, state.cityTo.location)
        drawPlanePath(state.planePath)
    }

}
