package ru.maxbach.aviasales.feature.search.adapter

sealed class SearchItem {
    object SearchHistoryHeader : SearchItem()

    data class SuggestionItem(
            val id: Long,
            val cityName: String
    ) : SearchItem()
}
