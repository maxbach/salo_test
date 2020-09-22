package ru.maxbach.aviasales.domain

import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.data.SearchHistoryRepository
import ru.maxbach.aviasales.data.SuggestionsRepository
import ru.maxbach.aviasales.network.model.City
import javax.inject.Inject

class GetSuggestionsUseCase @Inject constructor(
        private val suggestionsRepository: SuggestionsRepository,
        private val searchHistoryRepository: SearchHistoryRepository
) {

    //TODO: add logic for choosing language
    operator fun invoke(input: String): Single<List<City>> = if (input.isNotEmpty()) {
        suggestionsRepository.getSuggestions(input, "ru")
    } else {
        searchHistoryRepository.getSearchHistory()
    }

}
