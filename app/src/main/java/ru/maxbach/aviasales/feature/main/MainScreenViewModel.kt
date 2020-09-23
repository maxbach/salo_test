package ru.maxbach.aviasales.feature.main

import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.datasource.LastSearch
import ru.maxbach.aviasales.datasource.LastSearchDataSource
import ru.maxbach.aviasales.navigation.ScreenResult
import ru.maxbach.aviasales.network.model.City
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val coordinator: MainScreenCoordinator,
    //TODO: create repo and use case
    private val lastSearchDataSource: LastSearchDataSource,
    screenResult: ScreenResult<City>
) : BaseViewModel<MainScreenState>(MainScreenState()) {

    private lateinit var cityFromChosen: City
    private lateinit var cityToChosen: City

    // TODO: move to screen args and screen result
    private var cityFromSearchScreenOpened: Boolean? = false

    init {
        //TODO: move to methods
        screenResult.observe {
            if (cityFromSearchScreenOpened == true) {
                cityFromChosen = it
            } else if (cityFromSearchScreenOpened == false) {
                cityToChosen = it
            }
            cityFromSearchScreenOpened = null

            updateScreenState()
        }

        lastSearchDataSource
            .getLastSearchValue()
            .untilDestroy(onSuccess = {
                cityFromChosen = it.cityFrom
                cityToChosen = it.cityTo
                updateScreenState()
            })
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
        lastSearchDataSource
            .writeLastSearchValue(LastSearch(cityFromChosen, cityToChosen))
            .untilDestroy()
        coordinator.openPlanes(cityFromChosen, cityToChosen)
    }

    private fun updateScreenState() {
        updateState {
            MainScreenState(
                cityFrom = cityFromChosen.fullName,
                cityTo = cityToChosen.fullName
            )
        }
    }

}
