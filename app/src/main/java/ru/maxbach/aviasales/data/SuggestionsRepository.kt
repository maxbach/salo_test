package ru.maxbach.aviasales.data

import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.datasource.network.SuggestionsNetworkDataSource
import ru.maxbach.aviasales.datasource.network.model.AutocompleteResponse
import ru.maxbach.aviasales.datasource.network.model.City
import javax.inject.Inject

class SuggestionsRepository @Inject constructor(
    private val networkDataSource: SuggestionsNetworkDataSource
) {

    fun getSuggestions(input: String): Single<List<City>> = networkDataSource
        .search(input)
        .map(AutocompleteResponse::cities)

}
