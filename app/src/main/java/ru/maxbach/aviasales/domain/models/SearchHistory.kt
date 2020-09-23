package ru.maxbach.aviasales.domain.models

import com.squareup.moshi.JsonClass
import ru.maxbach.aviasales.datasource.network.model.City
import ru.maxbach.aviasales.utils.addToBegin

@JsonClass(generateAdapter = true)
data class SearchHistory(
    val citiesList: List<City> = emptyList()
)

fun SearchHistory.addNewCityAndRemoveRepeats(newCity: City) = SearchHistory(
    citiesList = (this.citiesList - newCity).addToBegin(newCity)
)
