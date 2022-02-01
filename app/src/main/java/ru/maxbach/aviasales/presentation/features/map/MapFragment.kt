package ru.maxbach.aviasales.presentation.features.map

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.presentation.base.fragment.BaseFragment
import ru.maxbach.aviasales.presentation.features.map.state.MapScreenMainState
import ru.maxbach.aviasales.presentation.features.map.utils.addCityMarker
import ru.maxbach.aviasales.presentation.features.map.utils.addPlaneMarker
import ru.maxbach.aviasales.presentation.features.map.utils.drawPlanePath
import ru.maxbach.aviasales.presentation.features.map.utils.focusMapOnRoute
import ru.maxbach.aviasales.presentation.features.map.utils.movePlane
import ru.maxbach.aviasales.presentation.features.map.utils.setup

class MapFragment : BaseFragment<MapNavArgs, MapScreenViewModel>(R.layout.fragment_planes) {

    companion object {

        private const val MAP_STATE_KEY_EXTRA = "MAP_STATE"

        fun create(navArgs: MapNavArgs) = MapFragment().apply {
            putNavArgs(navArgs)
        }
    }

    private val viewModel by viewModel<MapScreenViewModel>()

    private var planeMarker: Marker? = null
    private var isCameraPositionRestored: Boolean = false

    private lateinit var googleMap: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMapAsync {
            googleMap = this

            savedInstanceState?.getParcelable<CameraPosition>(MAP_STATE_KEY_EXTRA)?.let { savedPosition ->
                isCameraPositionRestored = true
                moveCamera(CameraUpdateFactory.newCameraPosition(savedPosition))
            }

            viewModel.state.observe(viewLifecycleOwner) { state ->
                setupFirst(state)
            }

            viewModel.planePosition.observe(viewLifecycleOwner) { planePosition ->
                planeMarker?.movePlane(planePosition) ?: run {
                    planeMarker = addPlaneMarker(planePosition)
                }
            }

            viewModel.planePath.observe(viewLifecycleOwner) { planePath ->
                drawPlanePath(requireContext(), planePath)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(MAP_STATE_KEY_EXTRA, googleMap.cameraPosition)
    }

    private fun GoogleMap.setupFirst(mainState: MapScreenMainState) {
        setup()

        addCityMarker(requireContext(), mainState.cityFrom)
        addCityMarker(requireContext(), mainState.cityTo)

        if (!isCameraPositionRestored) {
            focusMapOnRoute(requireContext(), mainState.cityFrom.location, mainState.cityTo.location)
        }
    }

    private fun getMapAsync(action: GoogleMap.() -> Unit) {
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync { it.action() }
    }

}
