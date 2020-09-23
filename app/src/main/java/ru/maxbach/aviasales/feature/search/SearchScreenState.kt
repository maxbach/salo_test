package ru.maxbach.aviasales.feature.search

import androidx.annotation.StringRes
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.feature.search.adapter.SearchItem

data class SearchScreenState(
        val suggestions: List<SearchItem> = emptyList(),
        @StringRes val inputHintId: Int = R.string.search_input_hint_default
)
