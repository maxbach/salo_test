package ru.maxbach.aviasales.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.maxbach.aviasales.network.model.AutocompleteResponse

interface CitySearchApi {

    @GET("autocomplete")
    fun search(@Query("term") query: String, @Query("lang") lang: String): Single<AutocompleteResponse>

}
