package ru.maxbach.aviasales.data.repo

import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.data.datasource.network.SuggestionsNetworkDataSource
import ru.maxbach.aviasales.data.datasource.network.model.AutocompleteResponse
import ru.maxbach.aviasales.data.datasource.network.model.City
import javax.inject.Inject

class SuggestionsRepository @Inject constructor(
    private val networkDataSource: SuggestionsNetworkDataSource
) {

    fun getSuggestions(input: String): Single<List<City>> = networkDataSource
        .search(input)
        .map(AutocompleteResponse::cities)

}
