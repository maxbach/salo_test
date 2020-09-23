package ru.maxbach.aviasales.domain

import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.data.SearchHistoryRepository
import ru.maxbach.aviasales.data.SuggestionsRepository
import ru.maxbach.aviasales.datasource.SearchHistory
import ru.maxbach.aviasales.feature.search.Suggestions
import javax.inject.Inject

class GetSuggestionsUseCase @Inject constructor(
    private val suggestionsRepository: SuggestionsRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) {

    //TODO: add logic for choosing language
    operator fun invoke(input: String): Single<Suggestions> = if (input.isNotEmpty()) {
        suggestionsRepository
            .getSuggestions(input, "ru")
            .map { Suggestions(it, isFromRecents = false) }
    } else {
        searchHistoryRepository.getSearchHistory()
            .map(SearchHistory::citiesList)
            .map { Suggestions(it, isFromRecents = true) }
    }

}
