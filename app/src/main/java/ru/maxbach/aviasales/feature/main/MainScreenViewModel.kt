package ru.maxbach.aviasales.feature.main

import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.navigation.ScreenResult
import ru.maxbach.aviasales.network.model.City
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
        private val coordinator: MainScreenCoordinator,
        private val screenResult: ScreenResult<City>
) : BaseViewModel<MainScreenState>(MainScreenState()) {

    private var cityFromChosen: City? = null
    private var cityToChosen: City? = null

    private var cityFromSearchScreenOpened: Boolean? = false

    init {
        screenResult.observe {
            if (cityFromSearchScreenOpened == true) {
                cityFromChosen = it
            } else if (cityFromSearchScreenOpened == false) {
                cityToChosen = it
            }
            cityFromSearchScreenOpened = null

            updateScreenState()
        }
    }

    fun onCityFromClicked() {
        cityFromSearchScreenOpened = true
        coordinator.openSearch()
    }

    fun onCityToClicked() {
        cityFromSearchScreenOpened = false
        coordinator.openSearch()
    }

    fun onButtonClicked() {
        //TODO: add handling null cases
        coordinator.openPlanes(cityFromChosen!!, cityToChosen!!)
    }

    private fun updateScreenState() {
        updateState {
            MainScreenState(
                    cityFrom = cityFromChosen?.fullName,
                    cityTo = cityToChosen?.fullName,
                    buttonEnabled = cityFromChosen != null && cityToChosen != null
            )
        }
    }

}
