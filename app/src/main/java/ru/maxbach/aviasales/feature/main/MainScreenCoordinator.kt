package ru.maxbach.aviasales.feature.main

import ru.maxbach.aviasales.navigation.Screens
import ru.maxbach.aviasales.network.model.City
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainScreenCoordinator @Inject constructor(
        private val router: Router
) {

    fun openSearch() {
        router.navigateTo(Screens.Search())
    }

    fun openPlanes(cityFrom: City, cityTo: City) {
        router.navigateTo(Screens.Planes(cityFrom, cityTo))
    }

}
