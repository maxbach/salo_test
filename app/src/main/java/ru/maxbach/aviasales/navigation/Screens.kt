package ru.maxbach.aviasales.navigation

import ru.maxbach.aviasales.presentation.main.MainFragment
import ru.maxbach.aviasales.presentation.map.MapFragment
import ru.maxbach.aviasales.presentation.map.MapNavArgs
import ru.maxbach.aviasales.presentation.search.SearchFragment
import ru.maxbach.aviasales.presentation.search.SearchScreenNavArgs
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
