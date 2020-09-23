package ru.maxbach.aviasales.feature.map

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.base.fragment.BaseFragment
import ru.maxbach.aviasales.feature.map.state.MapScreenMainState
import ru.maxbach.aviasales.feature.map.utils.addCityMarker
import ru.maxbach.aviasales.feature.map.utils.addPlaneMarker
import ru.maxbach.aviasales.feature.map.utils.drawPlanePath
import ru.maxbach.aviasales.feature.map.utils.focusMapOnRoute
import ru.maxbach.aviasales.feature.map.utils.movePlane
import ru.maxbach.aviasales.feature.map.utils.setup

class MapFragment(
    navArgs: MapNavArgs
) : BaseFragment<MapNavArgs, MapScreenViewModel>(R.layout.fragment_planes, navArgs) {

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

        focusMapOnRoute(mainState.cityFrom.location, mainState.cityTo.location)
    }

    private fun getMapAsync(action: GoogleMap.() -> Unit) {
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync {
            it.action()
        }
    }

}
