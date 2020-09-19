package ru.maxbach.aviasales.feature.search

import ru.maxbach.aviasales.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SearchCoordinator @Inject constructor(
        private val router: Router
) {

    fun openPlanes() {
        router.navigateTo(Screens.Planes())
    }

}
