package ru.maxbach.aviasales.feature.plane

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import ru.maxbach.aviasales.network.model.City
import ru.maxbach.aviasales.utils.viewModels

class PlaneFragment : SupportMapFragment() {

    companion object {
        private const val CITY_FROM_KEY = "CITY_FROM"
        private const val CITY_TO_KEY = "CITY_TO"

        fun create(cityFrom: City, cityTo: City): PlaneFragment = PlaneFragment().apply {
            arguments = bundleOf(
                    CITY_FROM_KEY to cityFrom,
                    CITY_TO_KEY to cityTo
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init(
                arguments?.getParcelable(CITY_FROM_KEY)!!,
                arguments?.getParcelable(CITY_TO_KEY)!!
        )
    }

    private val viewModel by viewModels<PlaneViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, { state ->
            getMapAsync {
                it.addMarker(MarkerOptions().position(state.cityFromLocation))
                it.addMarker(MarkerOptions().position(state.cityToLocation))

                it.setLatLngBoundsForCameraTarget(LatLngBounds.builder()
                        .include(state.cityFromLocation)
                        .include(state.cityToLocation)
                        .build()
                )

                it.addPolyline(PolylineOptions()
                        .add(state.cityFromLocation, state.cityToLocation)
                        .geodesic(true)
                        .pattern(listOf(Dash(20f), Gap(20f)))
                )
            }
        })
    }

}
