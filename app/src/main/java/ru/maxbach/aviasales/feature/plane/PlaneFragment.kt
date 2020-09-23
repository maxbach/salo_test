package ru.maxbach.aviasales.feature.plane

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.base.viewmodel.viewModels
import ru.maxbach.aviasales.network.model.City
import ru.maxbach.aviasales.utils.toBitmap

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
                    it.addMarker(MarkerOptions()
                            .position(state.cityFrom.location)
                            .icon(createCityMarker(state.cityFrom.shortName))
                            .alpha(0.7f)
                            .anchor(0.5f, 0.5f)
                    )
                    it.addMarker(MarkerOptions()
                            .position(state.cityTo.location)
                            .icon(createCityMarker(state.cityTo.shortName))
                            .alpha(0.8f)
                            .anchor(0.5f, 0.5f)
                    )

                    it.moveCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                    LatLngBounds.builder()
                                            .include(state.cityFrom.location)
                                            .include(state.cityTo.location)
                                            .build(),
                                    20
                            )
                    )

                    it.addPolyline(PolylineOptions()
                            .add(*state.pointsOfCurve.toTypedArray())
                            .pattern(listOf(Dash(10f), Gap(20f)))
                    )

                    it.uiSettings.isMapToolbarEnabled = false
                    it.uiSettings.isRotateGesturesEnabled = false
                    it.isBuildingsEnabled = false
                    it.setMaxZoomPreference(9f)

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

    @SuppressLint("InflateParams")
    private fun createCityMarker(cityShortName: String): BitmapDescriptor = (LayoutInflater
        .from(requireContext())
        .inflate(R.layout.map_city_marker, null) as TextView)
        .apply { text = cityShortName }
        .toBitmap()
        .let(BitmapDescriptorFactory::fromBitmap)

}
