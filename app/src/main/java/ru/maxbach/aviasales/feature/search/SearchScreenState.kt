package ru.maxbach.aviasales.feature.search

data class SearchScreenState(
        val suggestions: List<SearchItem> = emptyList()
)
