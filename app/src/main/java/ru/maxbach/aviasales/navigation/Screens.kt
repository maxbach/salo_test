package ru.maxbach.aviasales.navigation

import ru.maxbach.aviasales.feature.planes.PlanesFragment
import ru.maxbach.aviasales.feature.search.SearchFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class Search : SupportAppScreen() {
        override fun getFragment() = SearchFragment()
    }

    class Planes : SupportAppScreen() {
        override fun getFragment() = PlanesFragment()
    }

}
