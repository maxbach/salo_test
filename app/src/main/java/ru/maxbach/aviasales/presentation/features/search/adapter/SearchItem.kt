package ru.maxbach.aviasales.presentation.features.search.adapter

sealed class SearchItem {
    object SearchHistoryHeader : SearchItem()

    data class SuggestionItem(
            val id: Long,
            val cityName: String
    ) : SearchItem()
}
