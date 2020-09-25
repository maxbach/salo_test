package ru.maxbach.aviasales.presentation.map

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.presentation.base.fragment.BaseFragment
import ru.maxbach.aviasales.presentation.map.state.MapScreenMainState
import ru.maxbach.aviasales.presentation.map.utils.*

class MapFragment : BaseFragment<MapNavArgs, MapScreenViewModel>(R.layout.fragment_planes) {

    companion object {
        fun create(navArgs: MapNavArgs) = MapFragment().apply {
            putNavArgs(navArgs)
        }
    }

    private var planeMarker: Marker? = null

    private val viewModel by viewModel<MapScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            getMapAsync { setupFirst(state) }
        }

        viewModel.planePath.observe(viewLifecycleOwner) { planePath ->
            getMapAsync { drawPlanePath(requireContext(), planePath) }
        }

        getMapAsync {
            viewModel.planePosition.observe(viewLifecycleOwner) { planePosition ->
                planeMarker?.movePlane(planePosition) ?: run {
                    planeMarker = addPlaneMarker(planePosition)
                }
            }
        }
    }

    private fun GoogleMap.setupFirst(mainState: MapScreenMainState) {
        setup()

        addCityMarker(requireContext(), mainState.cityFrom)
        addCityMarker(requireContext(), mainState.cityTo)

        focusMapOnRoute(requireContext(), mainState.cityFrom.location, mainState.cityTo.location)
    }

    private fun getMapAsync(action: GoogleMap.() -> Unit) {
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync {
            it.action()
        }
    }

}
