package ru.maxbach.aviasales.domain

import ru.maxbach.aviasales.datasource.network.model.City
import ru.maxbach.aviasales.domain.models.Suggestions
import ru.maxbach.aviasales.feature.search.adapter.SearchItem
import ru.maxbach.aviasales.utils.addToBeginIfNotNull
import javax.inject.Inject

class ConvertSuggestionsToUiItemsUseCase @Inject constructor() {

    operator fun invoke(suggestions: Suggestions): List<SearchItem> = suggestions
        .cities
        .map(this::mapToUiItem)
        .addToBeginIfNotNull(
            SearchItem.SearchHistoryHeader
                .takeIf { suggestions.isFromRecents && suggestions.cities.isNotEmpty() }
        )

    private fun mapToUiItem(city: City) = SearchItem.SuggestionItem(
        city.id,
        city.name
    )

}
