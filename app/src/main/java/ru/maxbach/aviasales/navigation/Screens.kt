package ru.maxbach.aviasales.navigation

import ru.maxbach.aviasales.feature.main.MainFragment
import ru.maxbach.aviasales.feature.plane.PlaneFragment
import ru.maxbach.aviasales.feature.search.SearchFragment
import ru.maxbach.aviasales.network.model.City
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class Main : SupportAppScreen() {
        override fun getFragment() = MainFragment()
    }

    class Search : SupportAppScreen() {
        override fun getFragment() = SearchFragment()
    }

    class Planes(
            private val cityFrom: City,
            private val cityTo: City
    ) : SupportAppScreen() {
        override fun getFragment() = PlaneFragment.create(cityFrom, cityTo)
    }

}
