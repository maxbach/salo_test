package ru.maxbach.aviasales.presentation.search

import androidx.annotation.StringRes
import ru.maxbach.aviasales.R
import ru.maxbach.aviasales.presentation.search.adapter.SearchItem

data class SearchScreenState(
        val suggestions: List<SearchItem> = emptyList(),
        @StringRes val inputHintId: Int = R.string.search_input_hint_default
)
