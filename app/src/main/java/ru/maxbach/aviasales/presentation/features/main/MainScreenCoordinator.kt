package ru.maxbach.aviasales.presentation.features.main

import ru.maxbach.aviasales.data.datasource.network.model.City
import ru.maxbach.aviasales.domain.models.RoutePoint
import ru.maxbach.aviasales.presentation.navigation.Screens
import ru.maxbach.aviasales.presentation.features.map.MapNavArgs
import ru.maxbach.aviasales.presentation.features.search.SearchScreenNavArgs
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainScreenCoordinator @Inject constructor(
    private val router: Router
) {

    fun openSearch(routePoint: RoutePoint) {
        router.navigateTo(Screens.Search(SearchScreenNavArgs(routePoint)))
    }

    fun openPlanes(cityFrom: City, cityTo: City) {
        router.navigateTo(Screens.Map(MapNavArgs(cityFrom, cityTo)))
    }

}
