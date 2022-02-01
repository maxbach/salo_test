package ru.maxbach.aviasales.domain

import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.data.repo.SearchHistoryRepository
import ru.maxbach.aviasales.data.repo.SuggestionsRepository
import ru.maxbach.aviasales.domain.models.SearchHistory
import ru.maxbach.aviasales.domain.models.Suggestions
import javax.inject.Inject

class GetSuggestionsUseCase @Inject constructor(
  private val suggestionsRepository: SuggestionsRepository,
  private val searchHistoryRepository: SearchHistoryRepository
) {

    operator fun invoke(input: String): Single<Suggestions> = if (input.isNotEmpty()) {
        suggestionsRepository
            .getSuggestions(input)
            .map { Suggestions(it, isFromRecents = false) }
    } else {
        searchHistoryRepository.getSearchHistory()
            .map(SearchHistory::citiesList)
            .map { Suggestions(it, isFromRecents = true) }
    }

}
