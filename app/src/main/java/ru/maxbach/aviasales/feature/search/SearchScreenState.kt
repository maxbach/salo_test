package ru.maxbach.aviasales.feature.search

data class SearchScreenState(
        val suggestionsFrom: List<String> = emptyList(),
        val suggestionsTo: List<String> = emptyList(),
        val buttonEnabled: Boolean = false
)
