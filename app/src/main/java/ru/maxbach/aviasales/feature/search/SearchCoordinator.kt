package ru.maxbach.aviasales.feature.search

import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SearchCoordinator @Inject constructor(
        private val router: Router
) {

    fun close() {
        router.exit()
    }

}
