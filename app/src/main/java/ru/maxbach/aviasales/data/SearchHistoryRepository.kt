package ru.maxbach.aviasales.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.datasource.SearchHistoryDataSource
import ru.maxbach.aviasales.network.model.City
import javax.inject.Inject

class SearchHistoryRepository @Inject constructor(
        private val dataSource: SearchHistoryDataSource
) {

    fun getSearchHistory(): Single<List<City>> = dataSource
            .getSearchHistory()

    fun addCityToSearchHistory(city: City): Completable = dataSource
            .addToSearchHistory(city)

}
