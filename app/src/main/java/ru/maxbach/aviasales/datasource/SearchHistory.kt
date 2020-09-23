package ru.maxbach.aviasales.datasource

import com.squareup.moshi.JsonClass
import ru.maxbach.aviasales.network.model.City

@JsonClass(generateAdapter = true)
data class SearchHistory(
    val citiesList: List<City> = emptyList()
)

fun SearchHistory.addNewCityAndRemoveRepeats(newCity: City) = SearchHistory(
    citiesList = listOf(newCity) + (this.citiesList - newCity)
)
