package ru.maxbach.aviasales.presentation.main

import androidx.lifecycle.SavedStateHandle
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import ru.maxbach.aviasales.datasource.network.model.City
import ru.maxbach.aviasales.di.viewmodel.ViewModelAssistedFactory
import ru.maxbach.aviasales.domain.AddToSearchHistoryNewCityUseCase
import ru.maxbach.aviasales.domain.GetLastSearchUseCase
import ru.maxbach.aviasales.domain.RememberLastSearchUseCase
import ru.maxbach.aviasales.domain.models.RoutePoint
import ru.maxbach.aviasales.navigation.ScreenResult
import ru.maxbach.aviasales.presentation.base.fragment.EmptyState
import ru.maxbach.aviasales.presentation.base.livedata.EmptySingleLiveEvent
import ru.maxbach.aviasales.presentation.base.viewmodel.BaseViewModel
import ru.maxbach.aviasales.presentation.search.SearchResult

class MainScreenViewModel @AssistedInject constructor(
    private val coordinator: MainScreenCoordinator,
    private val getLastSearchUseCase: GetLastSearchUseCase,
    private val rememberLastSearchUseCase: RememberLastSearchUseCase,
    private val addToSearchHistoryNewCityUseCase: AddToSearchHistoryNewCityUseCase,
    private val screenResult: ScreenResult<SearchResult>,
    @Assisted arg0: SavedStateHandle
) : BaseViewModel<MainScreenState, EmptyState>(MainScreenState(), arg0) {

    val openTaxiEvent = EmptySingleLiveEvent()

    private lateinit var chosenCityFrom: City
    private lateinit var chosenCityTo: City

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
        if (chosenCityFrom.id == chosenCityTo.id) {
            openTaxiEvent.call()
        } else {
            coordinator.openPlanes(chosenCityFrom, chosenCityTo)
        }
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
        if (cityFrom != null) chosenCityFrom = cityFrom
        if (cityTo != null) chosenCityTo = cityTo

        updateState {
            MainScreenState(
                cityFrom = chosenCityFrom.fullName,
                cityTo = chosenCityTo.fullName
            )
        }
    }

    private fun rememberLastSearch() {
        rememberLastSearchUseCase(chosenCityFrom, chosenCityTo)
            .untilDestroy()
    }

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<MainScreenViewModel>

}
