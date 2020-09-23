package ru.maxbach.aviasales.feature.main

import ru.maxbach.aviasales.datasource.network.model.City
import ru.maxbach.aviasales.domain.models.RoutePoint
import ru.maxbach.aviasales.feature.map.MapNavArgs
import ru.maxbach.aviasales.feature.search.SearchScreenNavArgs
import ru.maxbach.aviasales.navigation.Screens
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
