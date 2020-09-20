package ru.maxbach.aviasales.data

import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.network.SuggestionsApi
import ru.maxbach.aviasales.network.model.AutocompleteResponse
import ru.maxbach.aviasales.network.model.City
import javax.inject.Inject

class SuggestionsRepository @Inject constructor(
        private val api: SuggestionsApi
) {

    fun getSuggestions(input: String, lang: String): Single<List<City>> = api
            .search(input, lang)
            .map(AutocompleteResponse::cities)

}
