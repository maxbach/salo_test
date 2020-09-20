package ru.maxbach.aviasales.feature.plane

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import ru.maxbach.aviasales.R
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

    private var planeMarker: Marker? = null

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
                if (planeMarker != null) {
                    planeMarker!!.apply {
                        position = state.plane.location
                        rotation = state.plane.angle
                    }
                } else {
                    it.addMarker(MarkerOptions().position(state.cityFromLocation))
                    it.addMarker(MarkerOptions().position(state.cityToLocation))

                    it.setLatLngBoundsForCameraTarget(LatLngBounds.builder()
                            .include(state.cityFromLocation)
                            .include(state.cityToLocation)
                            .build()
                    )

                    it.addPolyline(PolylineOptions()
                            .add(*state.pointsOfCurve.toTypedArray())
                            .pattern(listOf(Dash(10f), Gap(20f)))
                    )

                    planeMarker = it.addMarker(MarkerOptions()
                            .position(state.plane.location)
                            .rotation(state.plane.angle)
                            .anchor(0.5f, 0.5f)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_plane))
                    )
                }


            }
        })
    }

}
