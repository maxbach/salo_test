package ru.maxbach.aviasales.feature.main

import ru.maxbach.aviasales.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.datasource.network.model.City
import ru.maxbach.aviasales.domain.AddToSearchHistoryNewCityUseCase
import ru.maxbach.aviasales.domain.GetLastSearchUseCase
import ru.maxbach.aviasales.domain.RememberLastSearchUseCase
import ru.maxbach.aviasales.domain.models.RoutePoint
import ru.maxbach.aviasales.feature.search.SearchResult
import ru.maxbach.aviasales.navigation.ScreenResult
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val coordinator: MainScreenCoordinator,
    private val getLastSearchUseCase: GetLastSearchUseCase,
    private val rememberLastSearchUseCase: RememberLastSearchUseCase,
    private val addToSearchHistoryNewCityUseCase: AddToSearchHistoryNewCityUseCase,
    private val screenResult: ScreenResult<SearchResult>
) : BaseViewModel<MainScreenState>(MainScreenState()) {

    private lateinit var cityFromChosen: City
    private lateinit var cityToChosen: City

    init {
        observeSearchScreenResult()
        getLastSearch()
    }

    fun onCityFromClicked() {
        coordinator.openSearch(routePoint = RoutePoint.FROM)
    }

    fun onCityToClicked() {
        coordinator.openSearch(routePoint = RoutePoint.TO)
    }

    fun onButtonClicked() {
        rememberLastSearch()
        coordinator.openPlanes(cityFromChosen, cityToChosen)
    }

    private fun getLastSearch() {
        getLastSearchUseCase()
            .untilDestroy(onSuccess = {
                updateScreenState(
                    cityFrom = it.cityFrom,
                    cityTo = it.cityTo
                )
            })
    }

    private fun observeSearchScreenResult() {
        screenResult.observe { result ->
            addToHistoryNewCity(result.city)
            when (result.routePoint) {
                RoutePoint.FROM -> updateScreenState(cityFrom = result.city)
                RoutePoint.TO -> updateScreenState(cityTo = result.city)
            }
        }
    }

    private fun addToHistoryNewCity(chosenCity: City) {
        addToSearchHistoryNewCityUseCase
            .invoke(chosenCity)
            .untilDestroy()
    }

    private fun updateScreenState(cityFrom: City? = null, cityTo: City? = null) {
        if (cityFrom != null) cityFromChosen = cityFrom
        if (cityTo != null) cityToChosen = cityTo

        updateState {
            MainScreenState(
                cityFrom = cityFromChosen.fullName,
                cityTo = cityToChosen.fullName
            )
        }
    }

    private fun rememberLastSearch() {
        rememberLastSearchUseCase(cityFromChosen, cityToChosen)
            .untilDestroy()
    }

}
