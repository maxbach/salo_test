package ru.maxbach.aviasales.presentation.navigation

import ru.maxbach.aviasales.presentation.features.main.MainFragment
import ru.maxbach.aviasales.presentation.features.map.MapFragment
import ru.maxbach.aviasales.presentation.features.map.MapNavArgs
import ru.maxbach.aviasales.presentation.features.search.SearchFragment
import ru.maxbach.aviasales.presentation.features.search.SearchScreenNavArgs
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class Main : SupportAppScreen() {
        override fun getFragment() = MainFragment.create()
    }

    class Search(private val navArgs: SearchScreenNavArgs) : SupportAppScreen() {
        override fun getFragment() = SearchFragment.create(navArgs)
    }

    class Map(private val navArgs: MapNavArgs) : SupportAppScreen() {
        override fun getFragment() = MapFragment.create(navArgs)
    }

}
