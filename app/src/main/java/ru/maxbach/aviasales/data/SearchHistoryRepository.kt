package ru.maxbach.aviasales.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.maxbach.aviasales.datasource.in_memory.SearchHistoryInMemoryDataSource
import ru.maxbach.aviasales.datasource.network.model.City
import ru.maxbach.aviasales.domain.models.SearchHistory
import javax.inject.Inject

class SearchHistoryRepository @Inject constructor(
    private val dataSource: SearchHistoryInMemoryDataSource
) {

    fun getSearchHistory(): Single<SearchHistory> = dataSource
        .getSearchHistory()

    fun addCityToSearchHistory(city: City): Completable = dataSource
        .addNewCityToSearchHistory(city)

}
