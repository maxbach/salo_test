package ru.maxbach.aviasales.presentation.search

import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SearchCoordinator @Inject constructor(
        private val router: Router
) {

    fun close() {
        router.exit()
    }

}
