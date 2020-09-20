package ru.maxbach.aviasales.feature.plane

import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.network.model.City
import ru.maxbach.aviasales.network.model.toLatLng
import javax.inject.Inject

class PlaneViewModel @Inject constructor() : BaseViewModel<PlaneScreenState>(PlaneScreenState()) {

    private lateinit var cityFrom: City
    private lateinit var cityTo: City

    fun init(cityFrom: City, cityTo: City) {
        this.cityFrom = cityFrom
        this.cityTo = cityTo

        updateState { PlaneScreenState(cityFrom.location.toLatLng(), cityTo.location.toLatLng()) }
    }

}
