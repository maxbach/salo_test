package ru.maxbach.aviasales.datasource.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.maxbach.aviasales.datasource.network.model.AutocompleteResponse

interface SuggestionsNetworkDataSource {

    @GET("autocomplete")
    fun search(
        @Query("term") query: String,
        @Query("lang") lang: String = "ru"
    ): Single<AutocompleteResponse>

}
